package ua.kh.butov.ishop.servlets.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.constant.Constants;
import ua.kh.butov.ishop.entity.Order;
import ua.kh.butov.ishop.servlets.AbstractController;
import ua.kh.butov.ishop.util.RoutingUtils;
import ua.kh.butov.ishop.util.SessionUtils;

@WebServlet("/ajax/html/more/orders")
public class MyOrdersMoreController extends AbstractController {
	private static final long serialVersionUID = -4385792519039493271L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Order> orders = getOrderService().listMyOrders(SessionUtils.getCurrentAccount(req), getPage(req),
				Constants.MAX_ORDERS_PER_HTML_PAGE);
		req.setAttribute("orders", orders);
		RoutingUtils.forwardToFragment("order-list.jsp", req, resp);
	}
}
