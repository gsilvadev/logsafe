package com.xerpass.logsafe.utils;

import static org.springframework.util.StringUtils.isEmpty;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xerpass.logsafe.exception.ApiErrorException;

public class JwtUtil {

	public static String getTokenFromHeaderRequest(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;

		String header = req.getHeader("Authorization");

		if (isEmpty(header) || !header.startsWith("Bearer ")) {
			throw new ApiErrorException("Não autorizado", "Token inexistente ou inválido");
		}

		String token = header.substring(7);

		return token;
	}
	
	public static String getSubjectFromToken(String token) {
		 DecodedJWT decode = JWT.decode(token);
		 
		 return decode.getSubject();
	}
	
	public static String getLoginFromRequest(ServletRequest request){
		String token = getTokenFromHeaderRequest(request);
		return getSubjectFromToken(token);
	}
}
