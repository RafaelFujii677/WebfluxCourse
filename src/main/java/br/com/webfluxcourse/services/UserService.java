package br.com.webfluxcourse.services;

import org.springframework.stereotype.Service;

import br.com.webfluxcourse.entities.User;
import br.com.webfluxcourse.mapper.UserMapper;
import br.com.webfluxcourse.model.request.UserRequest;
import br.com.webfluxcourse.repositories.UserRepository;
import br.com.webfluxcourse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public Mono<User> save(final UserRequest request){
		return userRepository.save(userMapper.toEntity(request));
	}

	public Mono<User> findOneById(final String id){
		return userRepository.findOneById(id).switchIfEmpty(Mono.error(
				new ObjectNotFoundException(String.format("Object not found. id: %s, Type: %s", id, User.class.getSimpleName()))));
	}

	public Flux<User> findAll(){
		return userRepository.findAll();
	}

	public Mono<User> update(final String id, final UserRequest userRequest){
		return findOneById(id)
				.map(entity -> userMapper.toEntity(userRequest, entity))
				.flatMap(userRepository::save);
	}
}
