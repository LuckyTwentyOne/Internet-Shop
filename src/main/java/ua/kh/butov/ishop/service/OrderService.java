package ua.kh.butov.ishop.service;

import java.util.List;

import ua.kh.butov.ishop.entity.Order;
import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.model.CurrentAccount;
import ua.kh.butov.ishop.model.ShoppingCart;
import ua.kh.butov.ishop.model.SocialAccount;

public interface OrderService {

	void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);

	void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart);

	String serializeShoppingCart(ShoppingCart shoppingCart);

	ShoppingCart deserializeShoppingCart(String string);

	CurrentAccount authentificate(SocialAccount socialAccount);

	long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount);

	Order findOrderById(long id, CurrentAccount currentAccount);

	List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit);

	int countMyOrders(CurrentAccount currentAccount);
}
