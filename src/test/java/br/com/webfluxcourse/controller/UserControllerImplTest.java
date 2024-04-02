package br.com.webfluxcourse.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mongodb.reactivestreams.client.MongoClient;

import br.com.webfluxcourse.entities.User;
import br.com.webfluxcourse.mapper.UserMapper;
import br.com.webfluxcourse.model.request.UserRequest;
import br.com.webfluxcourse.services.UserService;

@SpringBootTest
@AutoConfigureWebTestClient
public class UserControllerImplTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean private UserService service;
	@MockBean private UserMapper mapper;
	@MockBean private MongoClient mongoClient;

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

}
