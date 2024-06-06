package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexGeoObjectMetaDataProperty {
	@JsonProperty("GeocoderMetaData")
	private YandexGeocoderMetaData geocoderMetaData;
}
