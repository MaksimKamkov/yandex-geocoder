package ru.mvideo.lards.geocoder.exception;

import lombok.Getter;

public class YandexGeocoderException extends RuntimeException {
	@Getter
	private int statusCode;

	public YandexGeocoderException(String message) {
		super(message);
	}

	public YandexGeocoderException(String message, Object... args) {
		super(String.format(message, args));
	}

	public YandexGeocoderException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}
}
