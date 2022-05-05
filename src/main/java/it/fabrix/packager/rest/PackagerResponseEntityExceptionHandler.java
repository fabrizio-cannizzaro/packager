package it.fabrix.packager.rest;

import it.fabrix.packager.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class PackagerResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorDto> handleNoSuchElementException(NoSuchElementException noSuchElementException, WebRequest request) {
		log.info("handleNoSuchElementException({}, {})", noSuchElementException.getMessage(), request);
		ResponseEntity<ErrorDto> responseEntity = new ResponseEntity<>(new ErrorDto("No packaging solution found", noSuchElementException.getMessage()), HttpStatus.NOT_FOUND);
		log.info("handleNoSuchElementException return: {}", responseEntity);
		return responseEntity;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info("handleMethodArgumentNotValid({}, {})", ex.getMessage(), request);
		StringBuilder errors = new StringBuilder();
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		for (int i = 0; i < allErrors.size(); i++) {
			FieldError fieldError = (FieldError) allErrors.get(i);
			String errorKey = String.format("%d: %s", i + 1, fieldError.getField());
			String errorMessage = String.format("%s; rejected value: %s", fieldError.getDefaultMessage(), fieldError.getRejectedValue());
			errors.append(String.format("%s %s", errorKey, errorMessage));
			if (i < allErrors.size() - 1) {
				errors.append(" - ");
			}
		}
		ResponseEntity<Object> responseEntity = new ResponseEntity<>(new ErrorDto("Invalid request", errors.toString()), HttpStatus.BAD_REQUEST);
		log.info("handleMethodArgumentNotValid return: {}", responseEntity);
		return responseEntity;
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorDto> handleAllException(Exception ex, WebRequest request) {
		log.error(String.format("WebRequest: %s)", request), ex);
		ErrorDto ed = new ErrorDto(ex.getMessage(), request.getDescription(true));
		ResponseEntity<ErrorDto> responseEntity = new ResponseEntity<>(ed, HttpStatus.INTERNAL_SERVER_ERROR);
		log.error("handleAllException return: {}", responseEntity);
		return responseEntity;
	}
}
