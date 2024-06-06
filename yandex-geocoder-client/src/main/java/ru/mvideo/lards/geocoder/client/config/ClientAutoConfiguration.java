package ru.mvideo.lards.geocoder.client.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import ru.mvideo.lards.geocoder.client.YandexGeocoderClientService;
import ru.mvideo.lards.geocoder.client.config.properties.ClientProperties;
import ru.mvideo.lards.geocoder.api.client.YandexGeocoderPublicApi;
import ru.mvideo.lib.client.LogHelper;

@Configuration
public class ClientAutoConfiguration {

	@Bean
	public ClientProperties yandexGeocoderClientProperties() {
		return new ClientProperties();
	}

	public ObjectMapper yandexGeocoderClientObjectMapper() {
		return new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.setSerializationInclusion(JsonInclude.Include.NON_NULL)
			.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Bean
	public LogHelper yandexGeocoderClientLogHelper() {
		return new LogHelper(yandexGeocoderClientObjectMapper());
	}

	@Bean
	public WebClient yandexGeocoderWebClient(WebClient.Builder clientBuilder, ClientProperties yandexGeocoderClientProperties) {
		var objectMapper = yandexGeocoderClientObjectMapper();

		var connectionProvider = ConnectionProvider.builder("yandex-geocoder-api-client")
			.maxIdleTime(yandexGeocoderClientProperties.getMaxIdleTime())
			.build();

		var httpClient = HttpClient.create(connectionProvider)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) yandexGeocoderClientProperties.getConnectionTimeout().toMillis())
			.doOnConnected(connection ->
					connection.addHandlerLast(
						new ReadTimeoutHandler(yandexGeocoderClientProperties.getReadTimeout().toMillis(),
							TimeUnit.MILLISECONDS))
			);

		var clientConnector = new ReactorClientHttpConnector(httpClient);

		return clientBuilder
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.baseUrl(yandexGeocoderClientProperties.getHost())
			.clientConnector(clientConnector)
			.codecs(clientDefaultCodecsConfigurer -> {
				clientDefaultCodecsConfigurer.defaultCodecs()
					.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
				clientDefaultCodecsConfigurer.defaultCodecs()
					.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
			})
			.exchangeStrategies(ExchangeStrategies
				.builder()
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
				.build())
			.build();
	}

	@Bean
	public YandexGeocoderPublicApi yandexGeocoderPublicApi(
			@Qualifier("yandexGeocoderWebClient") WebClient yandexGeocoderWebClient,
			@Qualifier("yandexGeocoderClientLogHelper") LogHelper yandexGeocoderClientLogHelper) {

		return new YandexGeocoderClientService(yandexGeocoderWebClient, yandexGeocoderClientLogHelper);
	}

}
