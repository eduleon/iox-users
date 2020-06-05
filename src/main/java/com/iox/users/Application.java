package com.iox.users;

import java.time.Duration;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.iox.users.entity.User;
import com.iox.users.repository.UserRepository;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import reactor.test.StepVerifier;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = { "com.iox.users.repository" })
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		runBD();

		UserWebClient gwc = new UserWebClient();
		LOGGER.info(gwc.getResult());
	}

	private static void runBD() {

		ConnectionFactory connectionFactory = ConnectionFactories
				.get("r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

		DatabaseClient client = DatabaseClient.create(connectionFactory);

//		client.execute("CREATE TABLE user" + "(email VARCHAR(255) PRIMARY KEY," + "name VARCHAR(255),"
//				+ "user_name VARCHAR(255)," + "phone VARCHAR(20))")
//				.fetch()
//				.rowsUpdated()
//				.as(StepVerifier::create)
//				.expectNextCount(1)
//				.verifyComplete();

		client.insert()
				.into(User.class)
				.using(new User("eledu@iox.com", "Eduardo León", "Eledu", "+56996453877"))
				.then()
				.as(StepVerifier::create)
				.verifyComplete();

		client.select()
				.from(User.class)
				.fetch()
				.first()
				.doOnNext(it -> LOGGER.info("Query: {}", it))
				.as(StepVerifier::create)
				.expectNextCount(1)
				.verifyComplete();

	}
	
	 @Bean
	    public CommandLineRunner demo(UserRepository repository) {
		 return (args) -> {
	            // save a few users
	            repository.saveAll(Arrays.asList(
	            		new User("jacky1@chan.com", "Jacky Chan", "Jacky1", "+56900000001"),
	            		new User("jacky2@chan.com", "Jacky Chan", "Jacky2", "+56900000002"),
	            		new User("jacky3@chan.com", "Jacky Chan", "Jacky3", "+56900000003"),
	            		new User("jacky4@chan.com", "Jacky Chan", "Jacky4", "+56900000004"),
	            		new User("jacky5@chan.com", "Jacky Chan", "Jacky5", "+56900000005")))
	                .blockLast(Duration.ofSeconds(10));

	            // fetch all users
	            LOGGER.info("Users found with findAll():");
	            LOGGER.info("-------------------------------");
	            repository.findAll().doOnNext(user -> {
	                LOGGER.info(user.toString());
	            }).blockLast(Duration.ofSeconds(10));

	            LOGGER.info("");

	            // fetch an individual user by ID
				repository.findById(3L).doOnNext(user -> {
					LOGGER.info("User found with findById(3L):");
					LOGGER.info("--------------------------------");
					LOGGER.info(user.toString());
					LOGGER.info("");
				}).block(Duration.ofSeconds(10));

	            LOGGER.info("");

	            // fetch an individual user by mail
				repository.findByEmail("jacky3@chan.com").doOnNext(user -> {
					LOGGER.info("User found with findById(jacky3@chan.com):");
					LOGGER.info("--------------------------------");
					LOGGER.info(user.toString());
					LOGGER.info("");
				}).block(Duration.ofSeconds(10));

	            LOGGER.info("");
	        };
		 
	 }
}