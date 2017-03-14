package ua.kh.butov.ishop.servlets.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.servlets.AbstractController;
import ua.kh.butov.ishop.util.RoutingUtils;

@WebServlet("/ajax/html/more/products/*")
public class ProductsByCategoryMoreController extends AbstractController {
	private static final long serialVersionUID = -2651974520717714088L;
	private static final int SUBSTRING_INDEX = "/iShop/ajax/html/more/products".length();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
		RoutingUtils.forwardToFragment("product-list.jsp", req, resp);
	}
}
