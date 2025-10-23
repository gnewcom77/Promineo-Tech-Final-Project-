package pepper.boss.controller.error;

import pepper.boss.error.ResourceNotFoundException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerErrorHandler {
	private enum LogStatus {
		STACK_TRACE, MESSAGE_ONLY
	}

	@Data
	private static class ExceptionMessage {
		private String message;
		private String statusReason;
		private int statusCode;
		private String timestamp;
		private String uri;
		private Map<String, String> fields; // validation field errors
	}

	@ExceptionHandler({ResourceNotFoundException.class, NoSuchElementException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionMessage handleNotFound(Exception ex, WebRequest req) {
		return build(ex, HttpStatus.NOT_FOUND, req, LogStatus.MESSAGE_ONLY);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionMessage handleBadJson(HttpMessageNotReadableException ex, WebRequest req) {
		return build(ex, HttpStatus.BAD_REQUEST, req, LogStatus.MESSAGE_ONLY);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionMessage handleValidation(MethodArgumentNotValidException ex, WebRequest req) {
		ExceptionMessage msg = build(ex, HttpStatus.BAD_REQUEST, req, LogStatus.MESSAGE_ONLY);
		Map<String, String> fields = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(fe -> fields.put(fe.getField(), fe.getDefaultMessage()));
		msg.setFields(fields);
		return msg;
	}

	@ExceptionHandler({ DuplicateKeyException.class, DataIntegrityViolationException.class })
	@ResponseStatus(HttpStatus.CONFLICT)
	public ExceptionMessage handleConflict(Exception ex, WebRequest req) {
		return build(ex, HttpStatus.CONFLICT, req, LogStatus.MESSAGE_ONLY);
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ExceptionMessage handleMethodNotAllowed(UnsupportedOperationException ex, WebRequest req) {
		return build(ex, HttpStatus.METHOD_NOT_ALLOWED, req, LogStatus.MESSAGE_ONLY);
	}

	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionMessage handleIllegalState(IllegalStateException ex, WebRequest req) {
		return build(ex, HttpStatus.BAD_REQUEST, req, LogStatus.MESSAGE_ONLY);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionMessage handleAny(Exception ex, WebRequest req) {
		return build(ex, HttpStatus.INTERNAL_SERVER_ERROR, req, LogStatus.STACK_TRACE);
	}

	private ExceptionMessage build(Exception ex, HttpStatus status, WebRequest webRequest, LogStatus logStatus) {
		String uri = null;
		if (webRequest instanceof ServletWebRequest swr) {
			uri = swr.getRequest().getRequestURI();
		}

		if (logStatus == LogStatus.MESSAGE_ONLY) {
			log.error("Exception: {}", ex.toString());
		} else {
			log.error("Exception: ", ex);
		}

		ExceptionMessage em = new ExceptionMessage();
		em.setMessage(ex.getMessage() != null ? ex.getMessage() : ex.toString());
		em.setStatusReason(status.getReasonPhrase());
		em.setStatusCode(status.value());
		em.setTimestamp(ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));
		em.setUri(uri);
		return em;
	}
}
