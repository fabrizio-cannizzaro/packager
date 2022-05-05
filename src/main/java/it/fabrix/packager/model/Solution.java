package it.fabrix.packager.model;

import it.fabrix.packager.dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Solution implements Comparable<Solution> {
	private final Set<ItemDto> itemDtos;

	public long getWeight() {
		return this.itemDtos.stream().mapToLong(ItemDto::getGrams).sum();
	}

	public BigDecimal getPrice() {
		return this.itemDtos.stream().map(ItemDto::getPrice).reduce(BigDecimal::add).orElseThrow();
	}

	public Set<Long> getIds() {
		return this.itemDtos.stream().map(ItemDto::getId).collect(Collectors.toSet());
	}

	@Override
	public int compareTo(Solution o) {
		int priceCompare = o.getPrice().compareTo(this.getPrice());
		return priceCompare == 0 ? Long.compare(this.getWeight(), o.getWeight()) : priceCompare;
	}
}
