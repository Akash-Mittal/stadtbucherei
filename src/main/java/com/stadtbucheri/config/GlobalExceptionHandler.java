package com.stadtbucheri.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.stadtbucheri.dto.ErrorResponse;
import com.stadtbucheri.exception.BusinessException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value());
		return new ResponseEntity<>(errorResponse, ex.getStatus());
	}
}
