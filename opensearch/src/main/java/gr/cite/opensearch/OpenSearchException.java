package gr.cite.opensearch;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public class OpenSearchException extends Exception {
	public OpenSearchException() {
		super();
	}
	
	public OpenSearchException(String message) {
		super(message);
	}
	
	public OpenSearchException(Throwable cause) {
		super(cause);
	}
	
	public OpenSearchException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public OpenSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
