package mx.com.truper.springboot.practica14.throttling.throttler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
public class ThrottlerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ThrottlerException(String message) {
		super(message);
	}
}
