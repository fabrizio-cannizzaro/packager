package it.fabrix.packager.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDto {
	private final Date timestamp;
	private final String message;
	private final String details;

	public ErrorDto(String message, String details) {
		this.timestamp = new Date();
		this.message = message;
		this.details = details;
	}
}
