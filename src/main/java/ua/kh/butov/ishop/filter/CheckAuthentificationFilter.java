package ua.kh.butov.ishop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kh.butov.ishop.constant.Constants;
import ua.kh.butov.ishop.util.RoutingUtils;
import ua.kh.butov.ishop.util.SessionUtils;
import ua.kh.butov.ishop.util.UrlUtils;
import ua.kh.butov.ishop.util.WebUtils;

/**
 * Filter determines which URLs are forbiden for unregistered user.
 * 
 * @author V. Butov
 *
 */
@WebFilter(filterName = "CheckAuthentificationFilter")
public class CheckAuthentificationFilter extends AbstractFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		if (SessionUtils.isCurrentAccountCreated(req)) {
			chain.doFilter(req, resp);
		} else {
			String requestUrl = WebUtils.getCurrentRequestUrl(req);
			if (UrlUtils.isAjaxUrl(requestUrl)) {
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				resp.getWriter().println("401");
			} else {
				req.getSession().setAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN, requestUrl);
				RoutingUtils.redirect("/iShop/sign-in", req, resp);
			}
		}
	}
}