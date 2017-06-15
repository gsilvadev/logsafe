package com.xerpass.logsafe.exception;

public class ApiErrorMessage {

	private String title;
	private String message;

	public ApiErrorMessage(){}
	
	public ApiErrorMessage(String title, String message) {
		this.title = title;
		this.message = message;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Título: " + this.title + " - Mensagem: " + this.message;
	}
}
