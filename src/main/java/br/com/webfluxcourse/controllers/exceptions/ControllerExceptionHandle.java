package br.com.webfluxcourse.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

	private String verifyDupKey(String message) {
		if(message.contains("email dup key")) {
			return "E-mail already registered";
		}
		return "Dup key exception";
	}
}
