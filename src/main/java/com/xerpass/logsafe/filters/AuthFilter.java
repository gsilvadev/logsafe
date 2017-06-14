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
import com.xerpass.logsafe.Utils.JwtUtil;

public class AuthFilter extends GenericFilterBean  {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String token = JwtUtil.getTokenFromHeaderRequest(request);
		
		try{
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secreto")).withIssuer("auth0").build();
			verifier.verify(token);
		} catch(JWTVerificationException ex){
			((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
//			throw new ApiErrorException("Não autorizado", "Token inexistente ou inválido");
		}
		
		chain.doFilter(request, response);
	}

}
