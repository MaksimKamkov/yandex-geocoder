package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexGeocoderMetaData {
	private String kind;
	private String text;
	private String precision;
	@JsonProperty("Address")
	private YandexAddress address;
	@JsonProperty("AddressDetails")
	private YandexAddressDetails addressDetails;
}
