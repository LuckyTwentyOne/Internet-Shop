package ua.kh.butov.ishop.service;

import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.model.ShoppingCart;

public interface OrderService {

	void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);

	void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart);

	String serializeShoppingCart(ShoppingCart shoppingCart);

	ShoppingCart deserializeShoppingCart(String string);

	//CurrentAccount authentificate(SocialAccount socialAccount);
}
