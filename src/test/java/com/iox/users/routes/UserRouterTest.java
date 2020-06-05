package com.iox.users.routes;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.iox.users.entity.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRouterTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testPostNewUser() {
		User newUser = new User("superjacky@chan.com", "Jacky Chan", "Jacky1", "+56900000001");
		User expectedNewUser = new User(7L, "jacky1@chan.com", "Jacky Chan", "Jacky1", "+56900000001");

		webTestClient.post()
				.uri("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(newUser)
				.exchange()

				// and use the dedicated DSL to test assertions against the response
				.expectStatus()
				.isCreated()
				.expectBody(User.class)
				.isEqualTo(expectedNewUser);

	}

	@Test
	public void testPostDuplicatedUser() {
		User newUser = new User("jacky1@chan.com", "Jacky Chan", "Jacky1", "+56900000001");

		webTestClient.post()
				.uri("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(newUser)
				.exchange()

				// and use the dedicated DSL to test assertions against the response
				.expectStatus()
				.is5xxServerError();
	}

	@Test
	public void testGetAllUsers() {
		List<User> expectedUsers = Arrays.asList(
				new User(1L, "jacky1@chan.com", "Jacky Chan", "Jacky1", "+56900000001"),
				new User(2L, "jacky2@chan.com", "Jacky Chan", "Jacky2", "+56900000002"),
				new User(3L, "jacky3@chan.com", "Jacky Chan", "Jacky3", "+56900000003"),
				new User(4L, "jacky4@chan.com", "Jacky Chan", "Jacky4", "+56900000004"),
				new User(5L, "jacky5@chan.com", "Jacky Chan", "Jacky5", "+56900000005"));

		webTestClient.get()
				.uri("/users")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()

				// and use the dedicated DSL to test assertions against the response
				.expectStatus()
				.isOk()
				.expectBodyList(User.class)
				.isEqualTo(expectedUsers);
	}

	@Test
	public void testGetUserByEmail() {
		User expectedUser = new User(1L, "jacky1@chan.com", "Jacky Chan", "Jacky1", "+56900000001");

		webTestClient.get()
				.uri("/users/jacky1@chan.com")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()

				// and use the dedicated DSL to test assertions against the response
				.expectStatus()
				.isOk()
				.expectBody(User.class)
				.isEqualTo(expectedUser);
	}
}