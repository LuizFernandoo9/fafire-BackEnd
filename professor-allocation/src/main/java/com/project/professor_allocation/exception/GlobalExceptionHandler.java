package com.project.professor_allocation.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException e) {
		return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleMessageNotReadable(HttpMessageNotReadableException e) {
		return buildResponse(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido ou malformado.");
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
		String message = String.format("O parâmetro '%s' possui um valor inválido: '%s'.", e.getName(), e.getValue());
		return buildResponse(HttpStatus.BAD_REQUEST, message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getFieldErrors().stream()
				.map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
				.findFirst()
				.orElse("Requisicao invalida.");
		return buildResponse(HttpStatus.BAD_REQUEST, message);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException e) {
		return buildResponse(HttpStatus.CONFLICT, "A operação viola uma restrição de integridade dos dados.");
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException e) {
		HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());
		return buildResponse(status, e.getReason());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception e) {
		log.error("Erro não tratado", e);
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.");
	}

	private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message) {
		ApiError apiError = new ApiError(status.value(), status.getReasonPhrase(), message);
		return new ResponseEntity<>(apiError, status);
	}
}
