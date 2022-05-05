package it.fabrix.packager.dto;

import lombok.Data;

import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BuildRequest {
	@Min(1)
	@Max(100000)
	private long maxWeight;

	@Valid
	@NotNull
	@Size(min = 1, max = 150)
	private Set<ItemDto> itemDtos;
}
