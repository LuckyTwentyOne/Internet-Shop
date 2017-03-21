package ua.kh.butov.ishop.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.ishop.form.ProductForm;
import ua.kh.butov.ishop.form.SearchForm;
import ua.kh.butov.ishop.service.OrderService;
import ua.kh.butov.ishop.service.ProductService;
import ua.kh.butov.ishop.service.SocialService;
import ua.kh.butov.ishop.service.impl.ServiceManager;

public abstract class AbstractController extends HttpServlet {
	private static final long serialVersionUID = 8233947516885878614L;
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private ServiceManager serviceManager;
	
	@Override
	public final void init() throws ServletException {
		serviceManager = ServiceManager.getInstance(getServletContext());
		initServlet();
	}

	protected void initServlet() throws ServletException {
	}

	@Override
	public final void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public final ProductService getProductService(){
		return serviceManager.getProductService();
	}
	
	public final OrderService getOrderService(){
		return serviceManager.getOrderService();
	}
	
	public SocialService getSocialService() {
		return serviceManager.getSocialService();
	}
	
	public final int getPageCount(int totalCount, int itemsPerPage) {
		int res = totalCount / itemsPerPage;
		if(res * itemsPerPage != totalCount) {
			res++;
		}
		return res;
	}
	
	public final int getPage(HttpServletRequest request) {
		try {
			return Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	public final SearchForm createSearchForm(HttpServletRequest request) {
		return new SearchForm(
				request.getParameter("query"), 
				request.getParameterValues("category"), 
				request.getParameterValues("producer")
				);
	}
	
	public final ProductForm createProductForm(HttpServletRequest request) {
		return new ProductForm(
				Integer.valueOf(request.getParameter("idProduct")),
				Integer.valueOf(request.getParameter("count"))
				);
	}

}
