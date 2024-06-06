package ru.mvideo.lards.geocoder.service;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.mvideo.lards.geocoder.config.YandexClientProperties;
import ru.mvideo.lards.geocoder.exception.YandexGeocoderException;
import ru.mvideo.lards.geocoder.model.ErrorDetails;
import ru.mvideo.lards.geocoder.api.model.yandex.YandexResponse;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class YandexConnectorImpl implements YandexConnector {
	private final YandexClientProperties yandexClientProperties;

	@Override
	public Mono<YandexResponse> getGeocode(String address, String apiKey) {
		WebClient webClient = makeWebClient();
		return webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.queryParam("geocode", address)
						.queryParam("apikey", apiKey)
						.queryParam("format", yandexClientProperties.getFormat())
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(HttpStatus::isError, this::handleHttpError)
				.bodyToMono(YandexResponse.class);
	}

	private WebClient makeWebClient() {
		TcpClient tcpClient = TcpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, yandexClientProperties.getConnectionTimeout())
				.doOnConnected(connection ->
						connection.addHandlerLast(new ReadTimeoutHandler(yandexClientProperties.getReadTimeout(), TimeUnit.MILLISECONDS)));
		return WebClient.builder()
				.baseUrl(yandexClientProperties.getUrl())
				.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
				.build();
	}

	private Mono<? extends Throwable> handleHttpError(ClientResponse clientResponse) {
		return clientResponse.bodyToMono(ErrorDetails.class)
				.flatMap(errorDetails -> {
					String message = String.format("Failed to get geo data. Status: %d. Error message: %s",
							errorDetails.getStatusCode(), errorDetails.getMessage());
					return Mono.error(new YandexGeocoderException(message, errorDetails.getStatusCode()));
				});
	}
}
