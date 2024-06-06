package ru.mvideo.lards.geocoder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.mvideo.lards.geocoder.exception.ClientNotFoundException;
import ru.mvideo.lards.geocoder.exception.YandexGeocoderException;
import ru.mvideo.lards.geocoder.model.error.ErrorResponse;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private static final String ERROR_MESSAGE = "Exception has occurred: ";

	@ExceptionHandler(ClientNotFoundException.class)
	public Mono<ResponseEntity<ErrorResponse>> handleClientNotFoundException(ClientNotFoundException exception, ServerWebExchange exchange) {
		log.error(ERROR_MESSAGE, exception);
		return buildErrorResponse(HttpStatus.BAD_REQUEST, exchange, exception::getMessage);
	}

	@ExceptionHandler(YandexGeocoderException.class)
	public Mono<ResponseEntity<ErrorResponse>> handleYandexGeocoderException(YandexGeocoderException exception, ServerWebExchange exchange) {
		log.error(ERROR_MESSAGE, exception);
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode(), exchange, exception::getMessage);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public Mono<ResponseEntity<ErrorResponse>> handleConstraintViolationException(ConstraintViolationException exception, ServerWebExchange exchange) {
		log.error(ERROR_MESSAGE, exception);
		return buildErrorResponse(HttpStatus.BAD_REQUEST, exchange, exception::getMessage);
	}

	@ExceptionHandler(Throwable.class)
	public Mono<ResponseEntity<ErrorResponse>> handleThrowable(Throwable exception, ServerWebExchange exchange) {
		log.error(ERROR_MESSAGE, exception);
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exchange, getRootCauseMessageSupplier(exception));
	}

	private Mono<ResponseEntity<ErrorResponse>> buildErrorResponse(HttpStatus httpStatus, ServerWebExchange exchange, Supplier<String> message) {
		return buildErrorResponse(httpStatus, httpStatus.value(), exchange, message);
	}

	private Mono<ResponseEntity<ErrorResponse>> buildErrorResponse(HttpStatus httpStatus, int statusCode, ServerWebExchange exchange, Supplier<String> message) {
		ErrorResponse errorResponse = ErrorResponse.builder()
				.path(exchange.getRequest().getPath().value())
				.status(statusCode)
				.error(httpStatus.getReasonPhrase())
				.message(message.get())
				.requestId(exchange.getRequest().getId())
				.timestamp(LocalDateTime.now())
				.build();

		return Mono.just(
				ResponseEntity
						.status(httpStatus)
						.body(errorResponse)
		);
	}

	private Supplier<String> getRootCauseMessageSupplier(Throwable exception) {
		return () -> Optional.ofNullable(NestedExceptionUtils.getRootCause(exception)).orElse(exception).getMessage();
	}
}
