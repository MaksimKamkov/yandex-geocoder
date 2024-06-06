package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YandexGeoObject {
	private YandexGeoObjectMetaDataProperty metaDataProperty;
	private String description;
	private String name;
	private YandexGeoObjectBoundedBy boundedBy;
	@JsonProperty("Point")
	private YandexGeoObjectPoint point;
}
