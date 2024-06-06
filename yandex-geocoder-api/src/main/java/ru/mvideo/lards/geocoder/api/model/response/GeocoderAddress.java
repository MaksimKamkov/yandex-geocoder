package ru.mvideo.lards.geocoder.api.model.response;

import lombok.Data;

import java.util.List;

@Data
public class GeocoderAddress {
	private String countryCode;
	private String postalCode;
	private String formatted;
	private List<GeocoderAddressComponent> components;
}
