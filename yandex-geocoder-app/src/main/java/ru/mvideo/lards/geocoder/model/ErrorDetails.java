package ru.mvideo.lards.geocoder.model;

import lombok.Data;

@Data
public class ErrorDetails {
	private Integer statusCode;
	private String error;
	private String message;
}
