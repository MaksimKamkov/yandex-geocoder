package ru.mvideo.lards.geocoder.api.model.yandex;

import lombok.Data;

import java.util.List;

@Data
public class YandexGeoObjectCollection {
	private YandexMetaDataProperty metaDataProperty;
	private List<YandexFeatureMember> featureMember;
}
