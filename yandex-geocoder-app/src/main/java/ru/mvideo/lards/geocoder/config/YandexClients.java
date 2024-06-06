package ru.mvideo.lards.geocoder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "yandex-clients")
public class YandexClients {
	private Map<String, String> apiKeys;
}
