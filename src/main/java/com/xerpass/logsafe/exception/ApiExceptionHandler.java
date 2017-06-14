package com.xerpass.logsafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value=ApiErrorException.class)
	public ApiError handleException(ApiErrorException ex){
		
		return ex.getApiError();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value=RuntimeException.class)
	public ApiError handleException(RuntimeException ex){
		ex.printStackTrace();
		
		ApiError error = new ApiError();
		
		return error;
	}
}
