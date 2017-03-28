package ua.kh.butov.ishop.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.ishop.entity.Account;
import ua.kh.butov.ishop.entity.Order;
import ua.kh.butov.ishop.entity.OrderItem;
import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.exception.AccessDeniedException;
import ua.kh.butov.ishop.exception.InternalServerErrorException;
import ua.kh.butov.ishop.exception.ResourceNotFoundException;
import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.framework.handler.DefaultListResultSetHandler;
import ua.kh.butov.ishop.framework.handler.DefaultUniqueResultSetHandler;
import ua.kh.butov.ishop.framework.handler.IntResultSetHandler;
import ua.kh.butov.ishop.framework.handler.ResultSetHandler;
import ua.kh.butov.ishop.model.CurrentAccount;
import ua.kh.butov.ishop.model.ShoppingCart;
import ua.kh.butov.ishop.model.ShoppingCartItem;
import ua.kh.butov.ishop.model.SocialAccount;
import ua.kh.butov.ishop.service.OrderService;
import ua.kh.butov.ishop.service.jdbc.JDBCUtils;

class OrderServiceImpl implements OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	private final ResultSetHandler<Product> productResultSetHandler = new DefaultUniqueResultSetHandler<>(Product.class);
	private final ResultSetHandler<Account> accountResultSetHandler = new DefaultUniqueResultSetHandler<>(Account.class);
	private final ResultSetHandler<Order> orderResultSetHandler = new DefaultUniqueResultSetHandler<>(Order.class);
	private final ResultSetHandler<List<OrderItem>> orderItemListResultSetHandler = new DefaultListResultSetHandler<>(OrderItem.class);
	private final ResultSetHandler<List<Order>> ordersResultSetHandler = new DefaultListResultSetHandler<>(Order.class);
	private final ResultSetHandler<Integer> countResultSetHandler = new IntResultSetHandler();
	private final DataSource dataSource;
	private final String rootDir;

	private String smtpHost;
	private String smtpPort;
	private String smtpUsername;
	private String smtpPassword;
	private String host;
	private String fromAddress;

	public OrderServiceImpl(DataSource dataSource, ServiceManager serviceManager) {
		this.dataSource = dataSource;
		this.rootDir = serviceManager.getApplicationProperty("app.avatar.root.dir");
		this.smtpHost = serviceManager.getApplicationProperty("email.smtp.server");
		this.smtpPort = serviceManager.getApplicationProperty("email.smtp.port");
		this.smtpUsername = serviceManager.getApplicationProperty("email.smtp.username");
		this.smtpPassword = serviceManager.getApplicationProperty("email.smtp.password");
		this.host = serviceManager.getApplicationProperty("app.host");
		this.fromAddress = serviceManager.getApplicationProperty("email.smtp.fromAddress");
	}

	@Override
	public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
		try (Connection connection = dataSource.getConnection()) {
			Product product = JDBCUtils.select(connection,
					"select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
							+ "where c.id=p.id_category and pr.id=p.id_producer and p.id=?",
					productResultSetHandler, productForm.getIdProduct());
			if (product == null) {
				throw new InternalServerErrorException("Product not found by id= " + productForm.getIdProduct());
			}
			shoppingCart.addProduct(product, productForm.getCount());
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart) {
		shoppingCart.removeProduct(form.getIdProduct(), form.getCount());
	}

	@Override
	public String serializeShoppingCart(ShoppingCart shoppingCart) {
		StringBuilder res = new StringBuilder();
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			res.append(item.getProduct().getId()).append("-").append(item.getCount()).append("|");
		}
		if (res.length() > 0) {
			res.deleteCharAt(res.length() - 1);
		}
		return res.toString();
	}

	@Override
	public ShoppingCart deserializeShoppingCart(String string) {
		ShoppingCart shoppingCart = new ShoppingCart();
		String[] items = string.split("\\|");
		for (String item : items) {
			try {
				String data[] = item.split("-");
				int idProduct = Integer.parseInt(data[0]);
				int count = Integer.parseInt(data[1]);
				addProductToShoppingCart(new ProductForm(idProduct, count), shoppingCart);
			} catch (RuntimeException e) {
				LOGGER.error("Can't add product to ShoppingCart during deserialization: item=" + item, e);
			}
		}
		return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
	}

	@Override
	public CurrentAccount authentificate(SocialAccount socialAccount) {
		try (Connection c = dataSource.getConnection()) {
			Account account = JDBCUtils.select(c, "select * from account where email=?", accountResultSetHandler,
					socialAccount.getEmail());
			if (account == null) {
				String uniqFileName = UUID.randomUUID().toString() + ".jpg";
				Path filePathToSave = Paths.get(rootDir + "/" + uniqFileName);
				downloadAvatar(socialAccount.getAvatarUrl(), filePathToSave);
				account = JDBCUtils.insert(c, "insert into account values (nextval('account_seq'),?,?,?)",
						accountResultSetHandler, socialAccount.getName(), socialAccount.getEmail(),
						"/iShop/media/avatar/" + uniqFileName);
				c.commit();
			}
			return account;
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new InternalServerErrorException("Can't process avatar link", e);
		}
	}

	protected void downloadAvatar(String avatarUrl, Path filePathToSave) throws IOException {
		try (InputStream in = new URL(avatarUrl).openStream()) {
			Files.copy(in, filePathToSave);
		}
	}

	@Override
	public long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount) {
		if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
			throw new InternalServerErrorException("shoppingCart is null or empty");
		}
		try (Connection c = dataSource.getConnection()) {
			Order order = JDBCUtils.insert(c, "insert into \"order\" values(nextval('order_seq'),?,?)",
					orderResultSetHandler, currentAccount.getId(), new Timestamp(System.currentTimeMillis()));
			JDBCUtils.insertBatch(c, "insert into order_item values(nextval('order_item_seq'),?,?,?)",
					toOrderItemParameterList(order.getId(), shoppingCart.getItems()));
			c.commit();
			sendEmail(currentAccount.getEmail(), order);
			return order.getId();
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		}
	}

	private void sendEmail(String emailAddress, Order order) {
		try {
			SimpleEmail email = new SimpleEmail();
			email.setCharset("utf-8");
			email.setHostName(smtpHost);
			email.setSSLOnConnect(true);
			email.setSslSmtpPort(smtpPort);
			email.setFrom(fromAddress);
			email.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
			email.setSubject("New order");
			email.setMsg(host + "/order?id=" + order.getId());
			email.addTo(emailAddress);
			email.send();
		} catch (Exception e) {
			LOGGER.error("Error during send email: " + e.getMessage(), e);
		}
	}

	private List<Object[]> toOrderItemParameterList(long idOrder, Collection<ShoppingCartItem> items) {
		List<Object[]> parametersList = new ArrayList<>();
		for (ShoppingCartItem item : items) {
			parametersList.add(new Object[] { idOrder, item.getProduct().getId(), item.getCount() });
		}
		return parametersList;
	}

	@Override
	public Order findOrderById(long id, CurrentAccount currentAccount) {
		try (Connection c = dataSource.getConnection()) {
			Order order = JDBCUtils.select(c, "select * from \"order\" where id=?", orderResultSetHandler, id);
			if (order == null) {
				throw new ResourceNotFoundException("Order not found by id: " + id);
			}
			if (!order.getIdAccount().equals(currentAccount.getId())) {
				throw new AccessDeniedException(
						"Account with id=" + currentAccount.getId() + " is not owner for order with id=" + id);
			}
			List<OrderItem> list = JDBCUtils.select(c,
					"select o.id, o.id_order, o.id_product, o.count, p.id as pid, p.name, p.description, p.price, p.image_link, c.name as category, pr.name as producer"
					+ " from order_item o, product p, category c, producer pr where pr.id=p.id_producer and c.id=p.id_category and o.id_product=p.id and o.id_order=?",
					orderItemListResultSetHandler, id);
			order.setItems(list);
			return order;
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit) {
		try (Connection c = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			return JDBCUtils.select(c,
					"select * from \"order\" where id_account=? order by created desc limit ? offset ?",
					ordersResultSetHandler, currentAccount.getId(), limit, offset);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		}
	}

	@Override
	public int countMyOrders(CurrentAccount currentAccount) {
		try (Connection c = dataSource.getConnection()) {
			return JDBCUtils.select(c, "select count(*) from \"order\" where id_account=?", countResultSetHandler,
					currentAccount.getId());
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		}
	}
}
