package com.xerpass.logsafe.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xerpass.logsafe.exception.ApiErrorException;
import com.xerpass.logsafe.utils.JwtUtil;

public class AuthFilter extends GenericFilterBean  {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try{
			String token = JwtUtil.getTokenFromHeaderRequest(request);
			
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secreto")).withIssuer("auth0").build();
			verifier.verify(token);
			
		} catch(ApiErrorException | JWTVerificationException ex){
			((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
			return;
		}
		
		chain.doFilter(request, response);
	}

}
