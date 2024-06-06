package ru.mvideo.lards.geocoder.api.model.yandex;

import lombok.Data;

@Data
public class YandexEnvelope {
	private String lowerCorner;
	private String upperCorner;
}
