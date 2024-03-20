package br.com.webfluxcourse.services;

import org.springframework.stereotype.Service;

import br.com.webfluxcourse.entities.User;
import br.com.webfluxcourse.mapper.UserMapper;
import br.com.webfluxcourse.model.request.UserRequest;
import br.com.webfluxcourse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public Mono<User> save(final UserRequest request){
		return userRepository.save(userMapper.toEntity(request));
	}
}
