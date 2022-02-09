package mx.com.truper.springboot.practica10.retry.service.exception;

public final class FailingServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public FailingServiceException(String message) {
		super(message);
	}
}
