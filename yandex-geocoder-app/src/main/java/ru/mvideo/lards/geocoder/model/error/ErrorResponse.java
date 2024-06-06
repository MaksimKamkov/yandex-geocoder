package ru.mvideo.lards.geocoder.model.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import ru.mvideo.lards.geocoder.model.UnixTimeLocalDateTimeSerializer;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

	private final String path;
	private final Integer status;
	private final String error;
	private final String message;
	private final String requestId;
	@JsonSerialize(using = UnixTimeLocalDateTimeSerializer.class)
	private final LocalDateTime timestamp;
}
