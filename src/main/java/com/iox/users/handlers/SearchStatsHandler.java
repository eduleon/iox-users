package com.iox.users.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import java.security.spec.KeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iox.users.dto.SearchStatsDTO;
import com.iox.users.dto.SearchStatsInternalDTO;

import reactor.core.publisher.Mono;

@Component
public class SearchStatsHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchStatsHandler.class);

	// TODO as config
	private static final String SERVICE_PRIVATE_KEY = "ionix123456";

	private static final String SERVICE_ALGORITHM = "DES";

	private static final String SERVICE_ENCODING = "UTF8";

	private WebClient client = WebClient.create("https://sandbox.ionix.cl");

	public Mono<ServerResponse> getStats(ServerRequest request) {

		LOGGER.debug("param: {}", request.queryParam("param"));

		String param = request.queryParam("param").orElseThrow(IllegalArgumentException::new);

		String encryptedRut = encryptParam(param);
		LOGGER.debug("encryptedRut: {}", encryptedRut);

		if (encryptedRut == null) {
			return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		Instant start = Instant.now();
		Mono<SearchStatsDTO> stats = client.get()
				.uri("/test-tecnico/search")
				.attribute("rut", encryptedRut)
				.exchange()
				.flatMap(res -> res.bodyToMono(SearchStatsInternalDTO.class))
				.map(mono -> processSearchStatsInternalDTO(mono, Duration.between(start, Instant.now()).toMillis()));

		LOGGER.debug("response: {}", stats);

		return ok().contentType(MediaType.APPLICATION_JSON).body(stats, SearchStatsDTO.class);
	}

	private SearchStatsDTO processSearchStatsInternalDTO(SearchStatsInternalDTO stats, long elapsedTime) {
		int registerCount = stats.getResult().getItems().size();
		return new SearchStatsDTO(registerCount, elapsedTime);
	}

	private String encryptParam(String param) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SERVICE_ALGORITHM);
			KeySpec keySpec = new DESKeySpec(SERVICE_PRIVATE_KEY.getBytes(SERVICE_ENCODING));
			Cipher cipher = Cipher.getInstance(SERVICE_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generateSecret(keySpec));
			byte[] cleartext = param.getBytes(SERVICE_ENCODING);
			return Base64.getEncoder().encodeToString(cipher.doFinal(cleartext));
		} catch (Exception e) {
			LOGGER.error("There was an error in the encryption process", e);
		}

		return null;

	}

}