package ua.kh.butov.ishop.servlets.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.servlets.AbstractController;
import ua.kh.butov.ishop.util.RoutingUtils;
import ua.kh.butov.ishop.util.SessionUtils;

@WebServlet("/shopping-cart")
public class ShowShoppingCartController extends AbstractController {
	private static final long serialVersionUID = 3437644051617589419L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionUtils.isCurrentShoppingCartCreated(req)) {
			RoutingUtils.forwardToPage("shopping-cart.jsp", req, resp);
		} else {
			RoutingUtils.redirect("/iShop/products", req, resp);
		}
	}
}
