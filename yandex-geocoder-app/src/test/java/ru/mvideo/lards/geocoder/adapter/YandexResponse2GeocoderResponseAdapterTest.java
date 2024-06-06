package ru.mvideo.lards.geocoder.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderResponse;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YandexResponse2GeocoderResponseAdapterTest {
	private YandexResponse yandexResponse;
	private GeocoderResponse geocoderResponseModel;

	@Test
	void convert_YandexResponse2GeocoderResponse_EqualsToModel() {
		YandexResponse2GeocoderResponseAdapter adapter = new YandexResponse2GeocoderResponseAdapterImpl();
		ObjectMapper mapper = new ObjectMapper();
		try {
			yandexResponse = mapper.readValue(ResourceUtils.getFile("classpath:yandex-response.json"), YandexResponse.class);
			geocoderResponseModel = mapper.readValue(ResourceUtils.getFile("classpath:geocoder-model-response.json"), GeocoderResponse.class);
		} catch (IOException ignored) {}

		assertEquals(geocoderResponseModel, adapter.convert(yandexResponse));
	}
}