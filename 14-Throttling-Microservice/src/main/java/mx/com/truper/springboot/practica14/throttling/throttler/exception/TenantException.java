package mx.com.truper.springboot.practica14.throttling.throttler.exception;

public class TenantException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TenantException(String message) {
		super(message);
	}
}
