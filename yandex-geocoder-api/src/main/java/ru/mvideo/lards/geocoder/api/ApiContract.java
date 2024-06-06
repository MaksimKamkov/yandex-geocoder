package ru.mvideo.lards.geocoder.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiContract {

	@UtilityClass
	public static class Geocoder {
		public static final String BASE = "/geocoder";
		public static final String POINT = "/point";
	}
}
