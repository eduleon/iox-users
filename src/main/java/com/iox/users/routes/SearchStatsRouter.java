package com.iox.users.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iox.users.handlers.SearchStatsHandler;

@Configuration
public class SearchStatsRouter {

	@Bean
	public RouterFunction<ServerResponse> getStats(SearchStatsHandler searchHandler) {
		return route(POST("/search-stats").and(accept(MediaType.APPLICATION_JSON)),
				searchHandler::getStats);
	}

}