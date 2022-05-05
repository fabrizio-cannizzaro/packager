package it.fabrix.packager.dto;

import lombok.Data;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ItemDto {
	@Min(1)
	private long id;

	@NotNull
	@Min(1)
	@Max(100)
	private BigDecimal price;

	@Min(1)
	@Max(100000)
	private long grams;
}
