package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class YandexAddress {
	private String country_code;
	private String postal_code;
	private String formatted;
	@JsonProperty("Components")
	private List<YandexComponent> components;
}
