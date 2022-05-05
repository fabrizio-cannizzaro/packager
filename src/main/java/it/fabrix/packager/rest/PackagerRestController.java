package it.fabrix.packager.rest;

import it.fabrix.packager.bl.PackageService;
import it.fabrix.packager.dto.BuildRequest;
import it.fabrix.packager.dto.BuildResponseDto;
import it.fabrix.packager.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@RestController
@RequestMapping("/api/packages/")
public class PackagerRestController {

	private final PackageService packageService;

	public PackagerRestController(PackageService packageService) {
		this.packageService = packageService;
	}

	@PostMapping("build")
	@Operation(summary = "Build an optimal package")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Optimal package solution found",
			content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BuildResponseDto.class))}),
		@ApiResponse(responseCode = "400", description = "Invalid request body supplied",
			content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))}),
		@ApiResponse(responseCode = "404", description = "No package solution found",
			content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))}),
		@ApiResponse(responseCode = "500", description = "Internal error",
			content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))})
	})
	public BuildResponseDto buildPackage(@Parameter(description = "package and items data to be processed") @Valid @RequestBody BuildRequest buildRequest) {
		log.info("buildPackage({})", buildRequest);
		BuildResponseDto ret = new BuildResponseDto(packageService.buildPackage(buildRequest.getMaxWeight(), buildRequest.getItemDtos()));
		log.info("buildPackage result: {}", ret);
		return ret;
	}

}