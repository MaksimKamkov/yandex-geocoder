package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexPremise {
	@JsonProperty("PremiseNumber")
	private String premiseNumber;
	@JsonProperty("PostalCode")
	private YandexPostalCode postalCode;
}
