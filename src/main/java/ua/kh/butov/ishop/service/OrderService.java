package ua.kh.butov.ishop.service;

import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.model.ShoppingCart;

public interface OrderService {

	void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);
}
