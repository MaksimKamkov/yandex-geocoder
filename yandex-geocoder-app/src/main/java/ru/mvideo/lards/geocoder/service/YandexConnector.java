package ru.mvideo.lards.geocoder.service;

import reactor.core.publisher.Mono;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexResponse;

public interface YandexConnector {
	Mono<YandexResponse> getGeocode(String address, String apiKey);
}
