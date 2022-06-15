package com.tushaar.miniassignment.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BasicAuthInterceptor extends HandlerInterceptorAdapter {

	private static final String AUTH_TOKEN = "token";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("x-auth-token");
		if (token != null && token.equals(AUTH_TOKEN)) {
			return true;
		} else {
			response.sendError(401, "Unauthorized");
			return false;
		}
	}
}
