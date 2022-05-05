package it.fabrix.packager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
public class BuildResponseDto {
	private final Set<Long> ids;
}
