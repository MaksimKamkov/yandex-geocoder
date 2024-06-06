package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexLocality {
	@JsonProperty("LocalityName")
	private String localityName;
	@JsonProperty("Thoroughfare")
	private YandexThoroughfare thoroughfare;
}
