package ua.kh.butov.ishop.servlets.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.servlets.AbstractController;
import ua.kh.butov.ishop.util.RoutingUtils;

@WebServlet("/products/*")
public class ProductsByCategoryController extends AbstractController {
	private static final long serialVersionUID = 1015660808630879774L;
	private static final int SUBSTRING_INDEX = "/iShop/products".length();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
		RoutingUtils.forwardToPage("products.jsp", req, resp);
	}
}
