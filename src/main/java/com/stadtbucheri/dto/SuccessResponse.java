package com.stadtbucheri.dto;

public class SuccessResponse implements Response {
	private String message;
	private int status;

	public SuccessResponse(String message, int status) {
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