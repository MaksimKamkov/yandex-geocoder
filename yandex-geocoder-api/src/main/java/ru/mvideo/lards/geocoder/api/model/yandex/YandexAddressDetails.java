package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexAddressDetails {
	@JsonProperty("Country")
	private YandexCountry country;
}
