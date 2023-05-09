package com.nsdl.captcha.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/*
 * AUTH HEADERS FILTER This filter is going to act as a CORS filter. It is
 * assigned before JwtAuthenticationFilter in the filter chain.
*/

public class CorsFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("Cors filter Called");
		System.out.println(request.getHeader("Access-Control-Request-Headers"));
		String origin = request.getHeader("Origin");
		if (origin != null && !origin.isEmpty()) {
			response.setHeader("Access-Control-Allow-Origin", origin);
		}
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
		response.setHeader("Access-Control-Allow-Headers",
				"Date, Content-Type, Accept, X-Requested-With, Authorization, From, X-Auth-Token, Request-Id");
		response.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
			filterChain.doFilter(request, response);
		}
	}
}