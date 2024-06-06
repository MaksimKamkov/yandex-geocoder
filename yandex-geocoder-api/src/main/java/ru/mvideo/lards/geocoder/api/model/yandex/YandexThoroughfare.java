package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexThoroughfare {
	@JsonProperty("ThoroughfareName")
	private String thoroughfareName;
	@JsonProperty("Premise")
	private YandexPremise premise;
}
