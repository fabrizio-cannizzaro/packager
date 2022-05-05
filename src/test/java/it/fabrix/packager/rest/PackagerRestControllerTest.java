package it.fabrix.packager.rest;

import it.fabrix.packager.bl.PackageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PackagerRestController.class)
class PackagerRestControllerTest {
	public static final String REQUEST_FILE = "src/test/resources/request.json";
	public static final String REQUEST_FILE_W = "src/test/resources/request_wrong.json";
	public static final String API_PACKAGES_BUILD = "/api/packages/build";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PackageService packageService;

	@Test
	public void buildPackage() throws Exception {
		Path path = Paths.get(REQUEST_FILE);
		String read = Files.readString(path);
		this.mvc.perform(post(API_PACKAGES_BUILD).content(read).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void buildPackageWrong() throws Exception {
		Path path = Paths.get(REQUEST_FILE_W);
		String read = Files.readString(path);
		this.mvc.perform(post(API_PACKAGES_BUILD).content(read).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
}