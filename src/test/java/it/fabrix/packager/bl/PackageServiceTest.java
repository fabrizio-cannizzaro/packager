package it.fabrix.packager.bl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import it.fabrix.packager.dto.BuildRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PackageServiceTest {

	public static final String REQUEST_FILE = "src/test/resources/request.json";
	public static final String REQUEST_FILE_NF = "src/test/resources/request_nf.json";
	private final ObjectMapper objectMapper;

	@Autowired
	private PackageService packageService;

	public PackageServiceTest() {
		this.objectMapper = new JsonMapper();
	}

	@Test
	public void buildPackage() throws IOException {
		Set<Long> expected = new HashSet<>(Arrays.asList(2L, 7L));
		BuildRequest buildRequest = this.objectMapper.readValue(Paths.get(REQUEST_FILE).toFile(), BuildRequest.class);
		Set<Long> result = this.packageService.buildPackage(buildRequest.getMaxWeight(), buildRequest.getItemDtos());
		assertEquals(expected, result);
	}

	@Test
	public void buildPackageNf() throws IOException {
		BuildRequest buildRequest = this.objectMapper.readValue(Paths.get(REQUEST_FILE_NF).toFile(), BuildRequest.class);
		assertThrows(NoSuchElementException.class, () -> this.packageService.buildPackage(buildRequest.getMaxWeight(), buildRequest.getItemDtos()));
	}

}