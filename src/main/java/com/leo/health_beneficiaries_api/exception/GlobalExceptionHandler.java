package com.leo.health_beneficiaries_api.exception;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
		return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
		return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new LinkedHashMap<>();

		exception.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("message", "Erro de validacao");
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("timestamp", OffsetDateTime.now());
		body.put("errors", errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		log.error("Exception: ", exception);
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
	}

	private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
		ErrorResponse body = ErrorResponse.builder()
				.message(message)
				.status(status.value())
				.timestamp(OffsetDateTime.now())
				.build();

		return ResponseEntity.status(status).body(body);
	}
}
