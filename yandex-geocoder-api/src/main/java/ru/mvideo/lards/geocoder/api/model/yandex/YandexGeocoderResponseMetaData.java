package ru.mvideo.lards.geocoder.api.model.yandex;

import lombok.Data;

@Data
public class YandexGeocoderResponseMetaData {
	private String request;
	private String found;
	private String results;
}
