package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexCountry {
	@JsonProperty("AddressLine")
	private String addressLine;
	@JsonProperty("CountryNameCode")
	private String countryNameCode;
	@JsonProperty("CountryName")
	private String countryName;
	@JsonProperty("AdministrativeArea")
	private YandexAdministrativeArea administrativeArea;
}
