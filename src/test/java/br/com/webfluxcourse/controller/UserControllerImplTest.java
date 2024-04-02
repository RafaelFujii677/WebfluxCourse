package br.com.webfluxcourse.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static reactor.core.publisher.Mono.just;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.webfluxcourse.entities.User;
import br.com.webfluxcourse.model.request.UserRequest;
import br.com.webfluxcourse.services.UserService;

@SpringBootTest
@AutoConfigureWebTestClient
public class UserControllerImplTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean private UserService service;

	@Test
	@DisplayName("Test endpoint save with success")
	void testSaveWithSuccess() {
		final var request = new UserRequest("Valdir", "valdir@gmail.com", "123");

		when(service.save(any(UserRequest.class))).thenReturn(just(User.builder().build()));

		webTestClient.post().uri("/users")
			.contentType(APPLICATION_JSON)
			.body(fromValue(request))
			.exchange()
			.expectStatus().isCreated();

		verify(service).save(any(UserRequest.class));
	}

	@Test
	@DisplayName("Test endpoint save with bad request")
	void testSaveWithBadRequest() {
		final var request = new UserRequest(" Valdir", "valdir@gmail.com", "123");

		webTestClient.post().uri("/users")
			.contentType(APPLICATION_JSON)
			.body(fromValue(request))
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody()
			.jsonPath("$.path").isEqualTo("/users")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.error").isEqualTo("Validation Error")
			.jsonPath("$.message").isEqualTo("Error on validation attributes")
			.jsonPath("$.errors[0].fieldName").isEqualTo("name")
			.jsonPath("$.errors[0].message").isEqualTo("field cannot have blank spaces at the beginning or at end");

	}
}
