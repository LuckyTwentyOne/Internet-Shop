package ua.kh.butov.ishop.servlets.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.model.ShoppingCart;
import ua.kh.butov.ishop.servlets.AbstractController;
import ua.kh.butov.ishop.util.RoutingUtils;
import ua.kh.butov.ishop.util.SessionUtils;

@WebServlet("/ajax/json/product/add")
public class AddProductController extends AbstractController {
	private static final long serialVersionUID = -5985074072198452723L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductForm productForm = createProductForm(req);
		ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
		getOrderService().addProductToShoppingCart(productForm, shoppingCart);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("totalCount", shoppingCart.getTotalCount());
		jsonObject.put("totalCost", shoppingCart.getTotalCost());
		RoutingUtils.sendJSON(jsonObject, req, resp);
	}
}
