package br.com.webfluxcourse.repositories;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

import br.com.webfluxcourse.entities.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	public Mono<User> save(final User user){
		return mongoTemplate.save(user);
	}

	public Mono<User> findOneById(String id) {
		return mongoTemplate.findById(id, User.class);
	}

}
