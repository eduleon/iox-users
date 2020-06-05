package com.iox.users.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iox.users.entity.User;
import com.iox.users.repository.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class UserHandler {

	@Autowired
	private UserRepository userRepository;

	public Mono<ServerResponse> addUser(ServerRequest request) {
		Mono<User> user = request.bodyToMono(User.class).flatMap(userRepository::save);
		return status(HttpStatus.CREATED).body(user, User.class);
	}

	public Mono<ServerResponse> getUsers(ServerRequest request) {
		return ok().contentType(MediaType.APPLICATION_JSON).body(userRepository.findAll(), User.class);
	}

	public Mono<ServerResponse> getUserByMail(ServerRequest request) {
		String email = request.pathVariable("email");
		return ok().contentType(MediaType.APPLICATION_JSON)
				.body(userRepository.findByEmail(email), User.class)
				.switchIfEmpty(notFound().build());
	}
}