package ru.mvideo.lards.geocoder.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.mvideo.lards.geocoder.adapter.YandexResponse2GeocoderResponseAdapterImpl;
import ru.mvideo.lards.geocoder.config.YandexClients;
import ru.mvideo.lards.geocoder.service.YandexConnector;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = YandexGeocoderControllerImpl.class)
@Import({YandexResponse2GeocoderResponseAdapterImpl.class, YandexClients.class})
public class YandexGeocoderControllerImplTest {

	@MockBean
	private YandexConnector yandexConnector;

	@Autowired
	private WebTestClient webClient;

	@Test
	public void test_missing_required_parameter_for_getOrder() {
		webClient
				.get()
				.uri("/geocoder/point")
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$.path").isEqualTo("/geocoder/point")
				.jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
				.jsonPath("$.error").isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.jsonPath("$.message").isNotEmpty()
				.jsonPath("$.requestId").isNotEmpty()
				.jsonPath("$.timestamp").isNotEmpty();
	}
}
