package br.com.webfluxcourse.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.webfluxcourse.entities.User;
import br.com.webfluxcourse.mapper.UserMapper;
import br.com.webfluxcourse.model.request.UserRequest;
import br.com.webfluxcourse.repositories.UserRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock private UserMapper mapper;
	@Mock private UserRepository repository;

	@InjectMocks private UserService service;

	@Test
	void testSave() {
		UserRequest request = new UserRequest("Valdir", "valdir@gmail.com", "123");
		User entity = User.builder().build();

		when(mapper.toEntity(any(UserRequest.class))).thenReturn(entity);
		when(repository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

		Mono<User> result = service.save(request);

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, times(1)).save(any(User.class));
	}

	@Test
	void testFindOneById() {
		when(repository.findOneById(anyString())).thenReturn(Mono.just(User.builder().id("1234").build()));

		Mono<User> result = service.findOneById("123");

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class && user.getId() == "1234")
			.expectComplete()
			.verify();

		Mockito.verify(repository, times(1)).findOneById(anyString());
	}
}
