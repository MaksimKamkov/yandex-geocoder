package ru.mvideo.lards.geocoder.adapter;

import org.springframework.stereotype.Component;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderAddress;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderAddressComponent;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderBounds;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderGeoObject;
import ru.mvideo.lards.geocoder.api.model.response.GeocoderResponse;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexAddress;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexFeatureMember;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexGeoObject;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexGeoObjectBoundedBy;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexGeoObjectCollection;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexGeocoderMetaData;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexGeocoderResponseMetaData;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexResponse;

import java.util.stream.Collectors;

@Component
public class YandexResponse2GeocoderResponseAdapterImpl implements YandexResponse2GeocoderResponseAdapter {
	@Override
	public GeocoderResponse convert(YandexResponse yandexResponse) {
		GeocoderResponse geocoderResponse = new GeocoderResponse();
		YandexGeoObjectCollection geoObjectCollection = yandexResponse.getResponse().getGeoObjectCollection();

		YandexGeocoderResponseMetaData geocoderResponseMetaData = geoObjectCollection.getMetaDataProperty().getGeocoderResponseMetaData();
		geocoderResponse.setRequest(geocoderResponseMetaData.getRequest());
		geocoderResponse.setFound(geocoderResponseMetaData.getFound());
		geocoderResponse.setResults(geocoderResponseMetaData.getResults());

		geocoderResponse.setGeoObjects(geoObjectCollection.getFeatureMember()
				.stream()
				.map(this::prepareGeoObject)
				.collect(Collectors.toList()));

		return geocoderResponse;
	}

	private GeocoderGeoObject prepareGeoObject(YandexFeatureMember featureMember) {
		GeocoderGeoObject geocoderGeoObject = new GeocoderGeoObject();

		YandexGeocoderMetaData geocoderMetaData = featureMember.getGeoObject().getMetaDataProperty().getGeocoderMetaData();
		geocoderGeoObject.setKind(geocoderMetaData.getKind());
		geocoderGeoObject.setText(geocoderMetaData.getText());
		geocoderGeoObject.setPrecision(geocoderMetaData.getPrecision());
		geocoderGeoObject.setGeocoderAddress(prepareAddress(geocoderMetaData.getAddress()));

		YandexGeoObject yandexGeoObject = featureMember.getGeoObject();
		geocoderGeoObject.setDescription(yandexGeoObject.getDescription());
		geocoderGeoObject.setName(yandexGeoObject.getName());
		geocoderGeoObject.setBoundedBy(prepareBounds(yandexGeoObject.getBoundedBy()));
		geocoderGeoObject.setPoint(yandexGeoObject.getPoint().getPos());
		return geocoderGeoObject;
	}

	private GeocoderAddress prepareAddress(YandexAddress yandexAddress) {
		GeocoderAddress geocoderAddress = new GeocoderAddress();
		geocoderAddress.setCountryCode(yandexAddress.getCountry_code());
		geocoderAddress.setPostalCode(yandexAddress.getPostal_code());
		geocoderAddress.setFormatted(yandexAddress.getFormatted());
		geocoderAddress.setComponents(yandexAddress.getComponents()
				.stream()
				.map(yandexComponent -> {
					GeocoderAddressComponent component = new GeocoderAddressComponent();
					component.setKind(yandexComponent.getKind());
					component.setName(yandexComponent.getName());
					return component;
				})
				.collect(Collectors.toList()));
		return geocoderAddress;
	}

	private GeocoderBounds prepareBounds(YandexGeoObjectBoundedBy geoObjectBoundedBy) {
		GeocoderBounds geocoderBounds = new GeocoderBounds();
		geocoderBounds.setLowerCorner(geoObjectBoundedBy.getEnvelope().getLowerCorner());
		geocoderBounds.setUpperCorner(geoObjectBoundedBy.getEnvelope().getUpperCorner());
		return geocoderBounds;
	}
}
