package com.xerpass.logsafe.exception;

import java.util.ArrayList;
import java.util.List;

public class ApiError {
	
	private List<ApiErrorMessage> messages;
	
	public ApiError(){
		this.messages = new ArrayList<ApiErrorMessage>();
	}
	
	public List<ApiErrorMessage> getMessages(){
		return messages;
	}
	
	public void addMessage(String title, String message){
		
		ApiErrorMessage messageError = new ApiErrorMessage(title, message);
		
		this.messages.add(messageError);
	}
	
	@Override
	public String toString() {
		return this.messages.toString();
	}
}
