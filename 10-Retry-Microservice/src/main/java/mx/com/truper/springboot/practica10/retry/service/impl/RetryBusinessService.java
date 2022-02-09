package mx.com.truper.springboot.practica10.retry.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

import mx.com.truper.springboot.practica10.retry.controller.model.StatusResponse;
import mx.com.truper.springboot.practica10.retry.service.IBusinessService;
import mx.com.truper.springboot.practica10.retry.service.exception.ServiceException;

//Cambie de abstract a final
public class RetryBusinessService implements IBusinessService {

	// Define propiedad Target Object
	private final IBusinessService targetBusinessService;

	private final int maxAttempts;
	private final long delay;
	private final AtomicInteger attempts;

	// Inyecta propiedades por constructor excepto attempts, en el constructor
	// inicializa el atomic integer.
	public RetryBusinessService(IBusinessService targetBusinessService, int maxAttempts, long delay) {
		this.targetBusinessService = targetBusinessService;
		this.targetBusinessService.setRetries(maxAttempts);
		this.maxAttempts = maxAttempts;
		this.delay = delay;
		this.attempts = new AtomicInteger();
	}

	// Define metodo attempts

	@Override
	public StatusResponse perform() throws ServiceException {

		// Implemente
		this.attempts.set(0);
		do {

			try {

				StatusResponse sr = targetBusinessService.perform();

				targetBusinessService.setAttempts(0);

				return sr;

			} catch (ServiceException ex) {

				if (this.attempts.incrementAndGet() >= this.maxAttempts) {
					
					throw ex;
				}

				try {
					Thread.sleep(this.delay);
				} catch (InterruptedException e) {
					// nothing to do
				}
			}

		} while (true);
	}

	// Implementa metodo setRetries
	@Override
	public void setRetries(int retries) {
		this.targetBusinessService.setRetries(retries);
	}

	// Implementa metodo setAttempts
	@Override
	public void setAttempts(int i) {
		this.targetBusinessService.setAttempts(i);
	}
	
}
