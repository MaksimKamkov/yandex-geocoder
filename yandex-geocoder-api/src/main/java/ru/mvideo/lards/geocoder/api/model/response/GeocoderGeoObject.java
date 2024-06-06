package ru.mvideo.lards.geocoder.api.model.response;

import lombok.Data;

@Data
public class GeocoderGeoObject {
	private String kind;
	private String text;
	private String precision;
	private GeocoderAddress geocoderAddress;
	private String description;
	private String name;
	private GeocoderBounds boundedBy;
	private String point;
}
