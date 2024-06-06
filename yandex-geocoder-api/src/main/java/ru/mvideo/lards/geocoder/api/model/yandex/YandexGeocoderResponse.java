package ru.mvideo.lards.geocoder.api.model.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YandexGeocoderResponse {
	@JsonProperty("GeoObjectCollection")
	private YandexGeoObjectCollection geoObjectCollection;
}
