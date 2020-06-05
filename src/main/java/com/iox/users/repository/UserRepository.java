package com.iox.users.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.iox.users.entity.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

	Mono<Boolean> existsByEmail(String email);

	Mono<User> findByEmail(String email);
}
