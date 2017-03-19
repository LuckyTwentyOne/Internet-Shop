package ua.kh.butov.ishop.servlets.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.constant.Constants;
import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.servlets.AbstractController;
import ua.kh.butov.ishop.util.RoutingUtils;

@WebServlet("/products/*")
public class ProductsByCategoryController extends AbstractController {
	private static final long serialVersionUID = 1015660808630879774L;
	private static final int SUBSTRING_INDEX = "/iShop/products".length();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
		List<Product> products = getProductService().listProductsByCategory(categoryUrl, 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
		req.setAttribute("products", products);
		req.setAttribute("selectedCategoryUrl", categoryUrl);
		int totalCount = getProductService().countProductsByCategory(categoryUrl);
		req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
		RoutingUtils.forwardToPage("products.jsp", req, resp);
	}
}
