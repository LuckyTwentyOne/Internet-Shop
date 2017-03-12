package ua.kh.butov.ishop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.util.RoutingUtils;

@WebFilter("/*")
public class ErrorHandlerFilter extends AbstractFilter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp,
	FilterChain chain) throws IOException, ServletException {
	try {
	 chain.doFilter(req, resp);
	} catch (Throwable th) {
	 String requestUrl = req.getRequestURI();
	 //LOGGER.error("Request " + requestUrl + " failed: " + th.getMessage(), th);
	 RoutingUtils.forwardToPage("error.jsp", req, resp);
	}
	}

	@Override
	public void destroy() {
	}
}
