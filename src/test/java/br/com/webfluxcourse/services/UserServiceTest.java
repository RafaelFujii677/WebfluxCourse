package br.com.webfluxcourse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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
import br.com.webfluxcourse.services.exceptions.ObjectNotFoundException;
import reactor.core.publisher.Flux;
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
		when(repository.save(any(User.class))).thenReturn(Mono.just(entity));

		Mono<User> result = service.save(request);

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, times(1)).save(any(User.class));
	}

	@Test
	void testFindOneById() {
		when(repository.findOneById(anyString())).thenReturn(Mono.just(User.builder().build()));

		Mono<User> result = service.findOneById("123");

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, times(1)).findOneById(anyString());
	}

	@Test
	void testFindAll() {
		when(repository.findAll()).thenReturn(Flux.just(User.builder().build()));

		Flux<User> result = service.findAll();

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, times(1)).findAll();
	}

	@Test
	void testUpdate() {
		UserRequest request = new UserRequest("Valdir", "valdir@gmail.com", "123");
		User entity = User.builder().build();

		when(mapper.toEntity(any(UserRequest.class), any(User.class))).thenReturn(entity);
		when(repository.findOneById(anyString())).thenReturn(Mono.just(entity));
		when(repository.save(any(User.class))).thenReturn(Mono.just(entity));

		Mono<User> result = service.update("123", request);

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, times(1)).save(any(User.class));
	}

	@Test
	void testDelete() {
		User entity = User.builder().build();
		when(repository.findOneByIdAndRemove(anyString())).thenReturn(Mono.just(entity));

		Mono<User> result = service.delete("123");

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, times(1)).findOneByIdAndRemove(anyString());
	}

	@Test
	void TestHandleNotFound() {
		when(repository.findOneById(anyString())).thenReturn(Mono.empty());
		try {
			service.findOneById("123").block();
		} catch (Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals(String.format("Object not found. id: %s, Type: %s", "123", User.class.getSimpleName()), e.getMessage());
		}
	}
}
