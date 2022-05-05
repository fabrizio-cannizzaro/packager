package it.fabrix.packager.bl.impl;

import com.google.common.collect.Sets;
import it.fabrix.packager.bl.PackageService;
import it.fabrix.packager.dto.ItemDto;
import it.fabrix.packager.model.Solution;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Long> buildPackage(long maxWeight, Set<ItemDto> itemDtos) {
		Set<Solution> solutions = new HashSet<>();
		Set<ItemDto> filteredItemDtos = itemDtos.stream().filter(itemDto -> itemDto.getGrams() <= maxWeight).collect(Collectors.toSet());
		for (int i = 1; i <= filteredItemDtos.size(); i++) {
			solutions.addAll(Sets.combinations(filteredItemDtos, i).stream().map(Solution::new).collect(Collectors.toSet()));
		}
		Solution acceptedSolution = solutions.stream().filter(solution -> solution.getWeight() <= maxWeight).sorted().findFirst().orElseThrow();
		return acceptedSolution.getIds();
	}


}
