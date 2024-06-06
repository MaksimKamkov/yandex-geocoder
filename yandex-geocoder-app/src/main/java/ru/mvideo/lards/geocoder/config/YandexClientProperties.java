package ru.mvideo.lards.geocoder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "yandex-geocoder-details.client")
public class YandexClientProperties {
	@NotBlank
	private String url;
	@NotNull
	@Positive
	private Integer connectionTimeout;
	@NotNull
	@Positive
	private Integer readTimeout;
	private String format;
}
