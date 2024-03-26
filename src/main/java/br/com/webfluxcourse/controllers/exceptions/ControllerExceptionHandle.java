package br.com.webfluxcourse.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import br.com.webfluxcourse.services.exceptions.ObjectNotFoundException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerExceptionHandle {

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Mono<StandardError>> duplicateKeyException(ServerHttpRequest request, Exception ex){;
			return ResponseEntity.badRequest()
						.body(Mono.just(
								StandardError.builder()
									.timestamp(LocalDateTime.now())
									.status(HttpStatus.BAD_REQUEST.value())
									.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
									.message(verifyDupKey(ex.getMessage()))
									.path(request.getPath().toString())
									.build()));
	}

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<Mono<ValidationError>> validationError(WebExchangeBindException ex, ServerHttpRequest request) {
		ValidationError error = new ValidationError(LocalDateTime.now(), request.getPath().toString(), HttpStatus.BAD_REQUEST.value(), "Validation Error", "Error on validation attributes");
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			error.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.just(error));
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Mono<StandardError>> objectNotFoundException(ServerHttpRequest request, ObjectNotFoundException ex){;
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Mono.just(
							StandardError.builder()
								.timestamp(LocalDateTime.now())
								.status(HttpStatus.NOT_FOUND.value())
								.error(HttpStatus.NOT_FOUND.getReasonPhrase())
								.message(ex.getMessage())
								.path(request.getPath().toString())
								.build()));
	}

	private String verifyDupKey(String message) {
		if(message.contains("email dup key")) {
			return "E-mail already registered";
		}
		return "Dup key exception";
	}
}
