package ru.mvideo.lards.geocoder.exception;

public class ClientNotFoundException extends RuntimeException {
	public ClientNotFoundException(String message) {
		super(message);
	}

	public ClientNotFoundException(String message, Object... args) {
		super(String.format(message, args));
	}
}
