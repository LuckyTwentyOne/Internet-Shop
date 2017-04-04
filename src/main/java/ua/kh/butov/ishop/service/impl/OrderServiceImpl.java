package ua.kh.butov.ishop.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.framework.annotation.Autowired;
import ua.kh.butov.framework.annotation.Component;
import ua.kh.butov.framework.annotation.jdbc.Transactional;
import ua.kh.butov.framework.factory.TransactionSynchronization;
import ua.kh.butov.framework.factory.TransactionSynchronizationManager;
import ua.kh.butov.ishop.entity.Account;
import ua.kh.butov.ishop.entity.Order;
import ua.kh.butov.ishop.entity.OrderItem;
import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.exception.AccessDeniedException;
import ua.kh.butov.ishop.exception.InternalServerErrorException;
import ua.kh.butov.ishop.exception.ResourceNotFoundException;
import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.model.CurrentAccount;
import ua.kh.butov.ishop.model.ShoppingCart;
import ua.kh.butov.ishop.model.ShoppingCartItem;
import ua.kh.butov.ishop.model.SocialAccount;
import ua.kh.butov.ishop.repository.AccountRepository;
import ua.kh.butov.ishop.repository.OrderItemRepository;
import ua.kh.butov.ishop.repository.OrderRepository;
import ua.kh.butov.ishop.repository.ProductRepository;
import ua.kh.butov.ishop.service.AvatarService;
import ua.kh.butov.ishop.service.CookieService;
import ua.kh.butov.ishop.service.NotificationService;
import ua.kh.butov.ishop.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CookieService cookieService;
	@Autowired
	private AvatarService avatarService;
	@Autowired
	private NotificationService notificationService;

	@Override
	@Transactional
	public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
		Product product = productRepository.findById(productForm.getIdProduct());
		if (product == null) {
			throw new InternalServerErrorException("Product not found by id=" + productForm.getIdProduct());
		}
		shoppingCart.addProduct(product, productForm.getCount());
	}

	@Override
	public void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart) {
		shoppingCart.removeProduct(form.getIdProduct(), form.getCount());
	}

	@Override
	public String serializeShoppingCart(ShoppingCart shoppingCart) {
		return cookieService.createShoppingCartCookie(shoppingCart.getItems());
	}

	@Override
	@Transactional
	public ShoppingCart deserializeShoppingCart(String cookieValue) {
		ShoppingCart shoppingCart = new ShoppingCart();
		List<ProductForm> items = cookieService.parseShoppingCartCookie(cookieValue);
		for (ProductForm item : items) {
			try {
				addProductToShoppingCart(item, shoppingCart);
			} catch (RuntimeException e) {
				LOGGER.error("Can't add product to ShoppingCart during deserialization: item=" + item, e);
			}
		}
		return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
	}

	@Override
	@Transactional(readOnly = false)
	public CurrentAccount authentificate(SocialAccount socialAccount) {
		Account account = accountRepository.findByEmail(socialAccount.getEmail());
		if (account == null) {
			String avatarUrl = avatarService.processAvatarLink(socialAccount.getAvatarUrl());
			account = new Account(socialAccount.getName(), socialAccount.getEmail(), avatarUrl);
			accountRepository.create(account);
		}
		return account;
	}

	@Override
	@Transactional(readOnly = false)
	public long makeOrder(ShoppingCart shoppingCart, final CurrentAccount currentAccount) {
		validateShoppingCart(shoppingCart);
		final Order order = new Order(currentAccount.getId(), new Timestamp(System.currentTimeMillis()));
		orderRepository.create(order);
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			orderItemRepository.create(new OrderItem(order.getId(), item.getProduct(), item.getCount()));
		}
		TransactionSynchronizationManager.addSynchronization(new TransactionSynchronization() {
			@Override
			public void afterCommit() {
				notificationService.sendNewOrderCreatedNotification(currentAccount.getEmail(), order);
			}
		});
		return order.getId();
	}

	private void validateShoppingCart(ShoppingCart shoppingCart) {
		if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
			throw new InternalServerErrorException("shoppingCart is null or empty");
		}
	}

	@Override
	@Transactional
	public Order findOrderById(long id, CurrentAccount currentAccount) {
		Order order = orderRepository.findById(id);
		if (order == null) {
			throw new ResourceNotFoundException("Order not found by id: " + id);
		}
		if (!order.getIdAccount().equals(currentAccount.getId())) {
			throw new AccessDeniedException(
					"Account with id=" + currentAccount.getId() + " is not owner for order with id=" + id);
		}
		order.setItems(orderItemRepository.findByIdOrder(id));
		return order;
	}

	@Override
	@Transactional
	public List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit) {
		int offset = (page - 1) * limit;
		return orderRepository.listMyOrders(currentAccount.getId(), limit, offset);
	}

	@Override
	@Transactional
	public int countMyOrders(CurrentAccount currentAccount) {
		return orderRepository.countMyOrders(currentAccount.getId());
	}
}