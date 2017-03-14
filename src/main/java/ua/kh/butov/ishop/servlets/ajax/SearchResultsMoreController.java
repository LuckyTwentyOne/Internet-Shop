package ua.kh.butov.ishop.servlets.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.servlets.AbstractController;
import ua.kh.butov.ishop.util.RoutingUtils;

@WebServlet("/ajax/html/more/search")
public class SearchResultsMoreController extends AbstractController {
	private static final long serialVersionUID = -2651974520717714088L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoutingUtils.forwardToFragment("product-list.jsp", req, resp);
	}
}
