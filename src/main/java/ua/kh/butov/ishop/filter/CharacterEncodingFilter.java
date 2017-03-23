package ua.kh.butov.ishop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Set request charset encoding to specific encoding.
 *
 * @author V.Butov
 */
@WebFilter(filterName = "CharacterEncodingFilter")
public class CharacterEncodingFilter extends AbstractFilter {

	@Override
	
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		chain.doFilter(req, resp);

	}

}
