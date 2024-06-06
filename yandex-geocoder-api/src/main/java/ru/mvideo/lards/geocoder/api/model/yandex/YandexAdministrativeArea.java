package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexAdministrativeArea {
	@JsonProperty("AdministrativeAreaName")
	private String administrativeAreaName;
	@JsonProperty("Locality")
	private YandexLocality Locality;
}
