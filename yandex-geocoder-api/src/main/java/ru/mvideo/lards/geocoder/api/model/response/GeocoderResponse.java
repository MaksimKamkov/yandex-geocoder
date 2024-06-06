package ru.mvideo.lards.geocoder.api.model.response;

import lombok.Data;

import java.util.List;

@Data
public class GeocoderResponse {
	private String request;
	private String found;
	private String results;
	private List<GeocoderGeoObject> geoObjects;
}
