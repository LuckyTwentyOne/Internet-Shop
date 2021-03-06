package ua.kh.butov.ishop.servlets.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.constant.Constants;
import ua.kh.butov.ishop.entity.Order;
import ua.kh.butov.ishop.model.CurrentAccount;
import ua.kh.butov.ishop.servlets.AbstractController;
import ua.kh.butov.ishop.util.RoutingUtils;
import ua.kh.butov.ishop.util.SessionUtils;

@WebServlet("/my-orders")
public class MyOrdersController extends AbstractController {
	private static final long serialVersionUID = -1782066337808445826L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CurrentAccount currentAccount = SessionUtils.getCurrentAccount(req);
		List<Order> orders = getOrderService().listMyOrders(currentAccount, 1,
				Constants.MAX_ORDERS_PER_HTML_PAGE);
		req.setAttribute("orders", orders);
		int totalCount = getOrderService().countMyOrders(currentAccount);
		req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_ORDERS_PER_HTML_PAGE));
		RoutingUtils.forwardToPage("my-orders.jsp", req, resp);
	}
}
