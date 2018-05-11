package gr.cite.opensearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class OpenSearchApplicationExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(OpenSearchApplicationExceptionHandler.class);
	
	@ExceptionHandler(value = OpenSearchException.class)
	public ResponseEntity<String> handleOpenSearchException(OpenSearchException exception) {
		logger.error(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(exception.getMessage());
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
		logger.error(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception.getMessage());
	}
	
	@ExceptionHandler(value = NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException exception) {
		logger.error(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(exception.getMessage());
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handleException(Exception exception) {
		logger.error(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(exception.getMessage());
	}
}