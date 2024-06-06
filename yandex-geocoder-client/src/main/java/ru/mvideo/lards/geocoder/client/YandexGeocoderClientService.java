package ru.mvideo.lards.geocoder.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.mvideo.lards.geocoder.api.ApiContract;
import ru.mvideo.lards.geocoder.api.client.YandexGeocoderPublicApi;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderResponse;
import ru.mvideo.lib.client.BaseClient;
import ru.mvideo.lib.client.LogHelper;

@Slf4j
public class YandexGeocoderClientService extends BaseClient implements YandexGeocoderPublicApi {

	public YandexGeocoderClientService(WebClient client, LogHelper logHelper) {
		super(client, logHelper, log);
	}

	@Override
	public Mono<GeocoderResponse> getPoint(String address, String client) {
		return getMono(uriBuilder -> uriBuilder
						.path(ApiContract.Geocoder.BASE + ApiContract.Geocoder.POINT)
						.queryParam("address", address)
						.queryParam("client", client)
						.build(),
				new ParameterizedTypeReference<>() {});
	}
}
