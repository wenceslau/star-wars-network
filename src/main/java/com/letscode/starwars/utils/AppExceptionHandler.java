
package com.letscode.starwars.utils;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que intercepta as excecoes do sistema e devolve respostas http conforme o Erro
 * @author Wenceslau
 *
 */
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Execeção para mensagens formato JSON invalidas
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String msgs = "Formato JSON invalido. " + exceptionMessage(ex);
		return handleExceptionInternal(ex, new Erro(msgs,HttpStatus.UNPROCESSABLE_ENTITY), headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	// Execeção para Null Pointer
	@ExceptionHandler({ NullPointerException.class })
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
		String msgs = "Internal failure. " + exceptionMessage(ex);
		return handleExceptionInternal(ex, new Erro(msgs,HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

	}

	// Execeção para recursos nao encontrado
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String msgs = exceptionMessage(ex);
		return handleExceptionInternal(ex,  new Erro(msgs,HttpStatus.NOT_FOUND), new HttpHeaders(),  HttpStatus.NOT_FOUND, request);
	}

	// Execeção para erros de PK no banco
	@ExceptionHandler({ DataIntegrityViolationException.class, JpaObjectRetrievalFailureException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
		String msgs = "Integridade de dados na base violada. " + exceptionMessage(ex);
		return handleExceptionInternal(ex,  new Erro(msgs,HttpStatus.BAD_REQUEST), new HttpHeaders(),  HttpStatus.BAD_REQUEST, request);
	}

	// Execeção para recursos nao encontrado
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		String msgs = exceptionMessage(ex);
		return handleExceptionInternal(ex,  new Erro(msgs,HttpStatus.BAD_REQUEST), new HttpHeaders(),  HttpStatus.BAD_REQUEST, request);
	}

	private String exceptionMessage(Exception ex) {

		List<String> list = new ArrayList<>();
		Throwable[] frames = ExceptionUtils.getThrowables(ex);
		for (Throwable throwable : frames) {
			String value = removeNameException(throwable.getMessage());
			System.out.println(value);
			if (!list.contains(value))
				list.add(value);
		}
		return String.join(". ", list);
	}

	/**
	 * Identifica o nome de uma execao na string
	 * A string deve ser a execao completa
	 * @param value texto
	 * @return texto
	 */
	private String removeNameException(final String value) {

		if (value == null)
			return "";
		
		String result = "";
		int pos = value.indexOf("n:") + 1;
		if (pos > 1)
			result = value.substring(pos).trim();

		if (result.isEmpty())
			return value;

		return result;

	}

	public static class Erro {

		private final String message;
		private final HttpStatus httpStatus;

		public Erro(String message, HttpStatus httpStatus) {
			super();
			this.message = message;
			this.httpStatus = httpStatus;
		}
		public String getMessage() {
			return message;
		}

		public HttpStatus getHttpStatus() {
			return httpStatus;
		}
	}


}
