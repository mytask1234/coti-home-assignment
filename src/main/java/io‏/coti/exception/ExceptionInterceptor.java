package io‏.coti.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io‏.coti.dto.ErrorDetailsResponseDto;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionInterceptor.class);

	// handling specific exception
	@ExceptionHandler(IndexNotFoundException.class)
	public final ResponseEntity<ErrorDetailsResponseDto> handleAllExceptions(IndexNotFoundException e, WebRequest request) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.error(e.getMessage(), e);
		}

		ErrorDetailsResponseDto errorDetails = new ErrorDetailsResponseDto(new Date(), e.getMessageResponse(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// handling specific exception
	@ExceptionHandler(InvalidInputException.class)
	public final ResponseEntity<ErrorDetailsResponseDto> handleAllExceptions(InvalidInputException e, WebRequest request) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.error(e.getMessage(), e);
		}

		ErrorDetailsResponseDto errorDetails = new ErrorDetailsResponseDto(new Date(), e.getMessageResponse(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// handling specific exception
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDetailsResponseDto> methodArgumentTypeMismatchHandling(MethodArgumentTypeMismatchException e, WebRequest request) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.error(e.getMessage(), e);
		}

		ErrorDetailsResponseDto errorDetails = new ErrorDetailsResponseDto(new Date(), e.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// handling global exception
	@ExceptionHandler(Throwable.class)
	public final ResponseEntity<ErrorDetailsResponseDto> handleAllExceptions(Throwable t, WebRequest request) {

		LOGGER.error(t.getMessage(), t);

		ErrorDetailsResponseDto errorDetails = new ErrorDetailsResponseDto(new Date(), t.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
