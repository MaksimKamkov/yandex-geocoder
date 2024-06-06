package ru.mvideo.lards.geocoder.api.client;

import reactor.core.publisher.Mono;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderResponse;

public interface YandexGeocoderPublicApi {

	Mono<GeocoderResponse> getPoint(String address, String client);
}
