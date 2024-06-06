package ru.mvideo.lards.geocoder.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.mvideo.lards.geocoder.adapter.YandexResponse2GeocoderResponseAdapter;
import ru.mvideo.lards.geocoder.config.YandexClients;
import ru.mvideo.lards.geocoder.exception.ClientNotFoundException;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderResponse;
import ru.mvideo.lards.geocoder.service.YandexConnector;

import javax.validation.constraints.NotBlank;

@RestController
@AllArgsConstructor
public class YandexGeocoderControllerImpl implements YandexGeocoderController {
	private final YandexConnector yandexConnector;
	private final YandexResponse2GeocoderResponseAdapter geocoderResponseAdapter;
	private final YandexClients yandexClients;

	@Override
	public Mono<GeocoderResponse> getOrder(@NotBlank String address, @NotBlank String client) {
		if (yandexClients.getApiKeys().containsKey(client)) {
			return yandexConnector.getGeocode(address, yandexClients.getApiKeys().get(client))
					.map(geocoderResponseAdapter::convert);
		} else {
			return Mono.error(new ClientNotFoundException("Client %s wasn't found", client));
		}
	}
}
