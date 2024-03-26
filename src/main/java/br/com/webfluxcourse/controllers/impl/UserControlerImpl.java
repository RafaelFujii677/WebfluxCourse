package br.com.webfluxcourse.controllers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.webfluxcourse.controllers.UserController;
import br.com.webfluxcourse.mapper.UserMapper;
import br.com.webfluxcourse.model.request.UserRequest;
import br.com.webfluxcourse.model.response.UserResponse;
import br.com.webfluxcourse.services.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserControlerImpl implements UserController{

	private final UserService userService;
	private final UserMapper mapper;

	@Override
	public ResponseEntity<Mono<Void>> save(final UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request).then());
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> findOneById(final String id) {
		return ResponseEntity.ok().body(userService.findOneById(id).map(mapper::toResponse));
	}

	@Override
	public ResponseEntity<Flux<UserResponse>> findAll() {
		return ResponseEntity.ok().body(userService.findAll().map(mapper::toResponse));
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
		return ResponseEntity.ok().body(userService.update(id, request).map(mapper::toResponse));
	}

	@Override
	public ResponseEntity<Mono<Void>> delete(String id) {
		return null;
	}

}
