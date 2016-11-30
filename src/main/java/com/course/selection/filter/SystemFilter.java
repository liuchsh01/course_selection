package com.course.selection.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.course.selection.entity.User;

public class SystemFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException("OncePerRequestFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpSession session = httpRequest.getSession(true);
		String uri =httpRequest.getRequestURI(); 
		if (!uri.contains("/static/") && !uri.contains("/system/")) {
			Object object = session.getAttribute("user");
			User user = object == null ? null : (User) object;
			if (user == null) {
				servletRequest.getRequestDispatcher("/system/jumpLogin.do").forward(httpRequest, httpResponse);
			}else{
				filterChain.doFilter(servletRequest, servletResponse);
			}
		}else{
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
