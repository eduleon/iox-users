package com.iox.users.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iox.users.handlers.UserHandler;

@Configuration
public class UserRouter {

	@Bean
	public RouterFunction<ServerResponse> addUser(UserHandler userHandler) {
		return route(GET("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUsers)
				.and(route(GET("/users/{email}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUserByMail)
				.and(route(POST("/users").and(contentType(MediaType.APPLICATION_JSON)), userHandler::addUser)));
	}

}
