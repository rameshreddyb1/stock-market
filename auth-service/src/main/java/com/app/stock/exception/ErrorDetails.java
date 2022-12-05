package com.app.stock.exception;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ErrorDetails {
	
	private int statusCode;
	private LocalDateTime timestamp;
	private String message;
	private String details;

}
