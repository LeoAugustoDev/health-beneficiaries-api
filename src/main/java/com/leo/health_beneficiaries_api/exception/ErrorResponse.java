package com.leo.health_beneficiaries_api.exception;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {

	String message;
	int status;
	OffsetDateTime timestamp;
}
