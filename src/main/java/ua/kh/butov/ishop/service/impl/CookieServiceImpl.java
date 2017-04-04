package ua.kh.butov.ishop.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.framework.annotation.Component;
import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.model.ShoppingCartItem;
import ua.kh.butov.ishop.service.CookieService;

@Component
public class CookieServiceImpl implements CookieService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CookieServiceImpl.class);

	@Override
	public String createShoppingCartCookie(Collection<ShoppingCartItem> items) {
		StringBuilder res = new StringBuilder();
		for (ShoppingCartItem item : items) {
			res.append(item.getProduct().getId()).append("-").append(item.getCount()).append("|");
		}
		if (res.length() > 0) {
			res.deleteCharAt(res.length() - 1);
		}
		return res.toString();
	}

	@Override
	public List<ProductForm> parseShoppingCartCookie(String cookieValue) {
		List<ProductForm> products = new ArrayList<ProductForm>();
		String[] items = cookieValue.split("\\|");
		for (String item : items) {
			try {
				String data[] = item.split("-");
				int idProduct = Integer.parseInt(data[0]);
				int count = Integer.parseInt(data[1]);
				products.add(new ProductForm(idProduct, count));
			} catch (RuntimeException e) {
				LOGGER.error("Can't parse cookie value: item=" + item + ", cookieValue=" + cookieValue, e);
			}
		}
		return products;
	}
}
