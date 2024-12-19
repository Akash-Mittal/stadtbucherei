package com.stadtbucheri.dto;

// ErrorResponse class implementing Response interface
public class ErrorResponse implements Response {
	private String message;
	private int status;

	public ErrorResponse(String message, int status) {
		this.message = message;
		this.status = status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public int getStatus() {
		return status;
	}
}