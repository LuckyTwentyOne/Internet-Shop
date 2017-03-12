package ua.kh.butov.ishop.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.ishop.service.ServiceManager;

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

}
