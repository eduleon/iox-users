package com.iox.users;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.iox.users.entity.User;

import reactor.core.publisher.Mono;

public class UserWebClient {
	private WebClient client = WebClient.create("http://localhost:8080");

	private Mono<ClientResponse> newUser = client.post()
			.uri("/users")
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(new User("asdasd@asdasd,com", "asd", "asdasd", "+1091298123"))
			.exchange();

	private Mono<ClientResponse> allUser = client.get()
			.uri("/users")
			.accept(MediaType.APPLICATION_JSON)
			.exchange();

	private Mono<ClientResponse> singleUser = client.get()
			.uri("/users/asdasd@asdasd,com")
			.accept(MediaType.APPLICATION_JSON)
			.exchange();

	public String getResult() {
//		">> New User = " + 
		newUser.flatMap(res -> res.bodyToMono(User.class)).block();
//		">> All User = " + 
		allUser.flatMap(res -> res.bodyToMono(User[].class)).block();
//		">> Single User = " + 
		singleUser.flatMap(res -> res.bodyToMono(User.class)).block();
		
		return ":B";
	}
}