package com.paycart.order.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		List<String> validationMessages = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"Validation failed", request.getRequestURI());
		apiError.setValidationErrors(validationMessages);

		return ResponseEntity.badRequest().body(apiError);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());
		return ResponseEntity.badRequest().body(apiError);
	}

	/**
	 * Handles errors coming from calling Wallet Service via RestTemplate (4xx).
	 * Example: Insufficient balance -> Wallet returns 400 -> RestTemplate throws
	 * HttpClientErrorException
	 */
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ApiError> handleHttpClientError(HttpClientErrorException ex, HttpServletRequest request) {
		ApiError apiError = new ApiError(ex.getStatusCode().value(), ex.getStatusText(),
				"Downstream service error: " + ex.getResponseBodyAsString(), request.getRequestURI());
		return ResponseEntity.status(ex.getStatusCode()).body(apiError);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleRuntime(RuntimeException ex, HttpServletRequest request) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unexpected error occurred",
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
	}
}
