package ru.mvideo.lards.geocoder.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderResponse;

import javax.validation.constraints.NotBlank;


@Validated
@RequestMapping(path = "/geocoder", produces = MediaType.APPLICATION_JSON_VALUE)
public interface YandexGeocoderController {
	@GetMapping(path = "/point")
	Mono<GeocoderResponse> getOrder(@NotBlank String address, @NotBlank String client);
}
