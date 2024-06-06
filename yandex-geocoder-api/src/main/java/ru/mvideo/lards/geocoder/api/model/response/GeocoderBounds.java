package ru.mvideo.lards.geocoder.api.model.response;

import lombok.Data;

@Data
public class GeocoderBounds {
	private String lowerCorner;
	private String upperCorner;
}
