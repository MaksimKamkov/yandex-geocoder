package ru.mvideo.lards.geocoder.adapter;

import ru.mvideo.lards.geocoder.api.model.response.GeocoderResponse;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexResponse;

public interface YandexResponse2GeocoderResponseAdapter {
	GeocoderResponse convert(YandexResponse yandexResponse);
}
