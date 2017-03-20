package ua.kh.butov.ishop.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.exception.SqlApplicationException;
import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.model.ShoppingCart;
import ua.kh.butov.ishop.model.ShoppingCartItem;
import ua.kh.butov.ishop.service.OrderService;
import ua.kh.butov.ishop.service.jdbc.JDBCUtils;
import ua.kh.butov.ishop.service.jdbc.ResultSetHandler;
import ua.kh.butov.ishop.service.jdbc.ResultSetHandlerFactory;

class OrderServiceImpl implements OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	private static final ResultSetHandler<Product> productResultSetHandler = ResultSetHandlerFactory
			.getSingleResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);
	private final DataSource dataSource;

	public OrderServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
		try (Connection connection = dataSource.getConnection()) {
			Product product = JDBCUtils.select(connection,
					"select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
							+ "where c.id=p.id_category and pr.id=p.id_producer and p.id=?",
					productResultSetHandler, productForm.getIdProduct());
			if (product == null) {
				throw new SqlApplicationException("Product not found by id= " + productForm.getIdProduct());
			}
			shoppingCart.addProduct(product, productForm.getCount());
		} catch (SQLException e) {
			throw new SqlApplicationException("Can't execute sql query: " + e.getMessage(), e);
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
}
