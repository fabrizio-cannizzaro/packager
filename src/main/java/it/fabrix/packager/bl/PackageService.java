package it.fabrix.packager.bl;

import it.fabrix.packager.dto.ItemDto;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public interface PackageService {

	/**
	 * Find the optimal packaging solution
	 *
	 * @param maxWeight the maximum package weight allowed
	 * @param itemDtos  list of candidate items to be packaged
	 * @return the optimal solution found
	 * @throws NoSuchElementException if no solution has been found
	 */
	Set<Long> buildPackage(long maxWeight, Set<ItemDto> itemDtos);

}
