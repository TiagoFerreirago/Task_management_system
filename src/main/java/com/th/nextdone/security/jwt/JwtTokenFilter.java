package com.th.nextdone.security.jwt;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProvider tokenProvider;
	
	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			var token = tokenProvider.resolveToken((HttpServletRequest)request);
			if(StringUtils.isNotBlank(token) && tokenProvider.validateToken(token)) {
				Authentication authentication = tokenProvider.getAuthentication(token);
				if(authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}

}
