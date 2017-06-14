package com.xerpass.logsafe.exception;

public class ApiErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ApiError error;

	public ApiErrorException() {
		super();
		this.error = new ApiError();
	}

	public ApiErrorException(String title, String message) {
		super();
		this.error = new ApiError();
		this.error.addMessage(title, message);
	}

	public void addMessage(String title, String message) {
		this.error.addMessage(title, message);
	}
	
	@Override
	public String getMessage() {
		return error.toString();
	}

	public ApiError getApiError() {
		return this.error;
	}
}
