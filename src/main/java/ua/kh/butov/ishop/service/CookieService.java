package ua.kh.butov.ishop.service;

import java.util.Collection;
import java.util.List;

import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.model.ShoppingCartItem;

public interface CookieService {
	String createShoppingCartCookie(Collection<ShoppingCartItem> items);

	List<ProductForm> parseShoppingCartCookie(String cookieValue);
}
