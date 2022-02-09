package mx.com.truper.springboot.practica11.circuitbreaker.service.exception;

public final class FailingServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public FailingServiceException(String message) {
		super(message);
	}
}
