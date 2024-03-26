package br.com.webfluxcourse.repositories;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.webfluxcourse.entities.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
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

	public Flux<User> findAll() {
		return mongoTemplate.findAll(User.class);
	}

	public Mono<User> findOneByIdAndRemove(String id) {
		Query query = new Query();
		Criteria where = Criteria.where("id").is(id);
		return mongoTemplate.findAndRemove(query.addCriteria(where), User.class);
	}

}
