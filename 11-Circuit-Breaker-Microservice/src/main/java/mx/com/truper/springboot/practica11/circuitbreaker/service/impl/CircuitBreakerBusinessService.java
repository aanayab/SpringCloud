package mx.com.truper.springboot.practica11.circuitbreaker.service.impl;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica11.circuitbreaker.controller.model.StatusResponse;
import mx.com.truper.springboot.practica11.circuitbreaker.service.IBusinessService;
import mx.com.truper.springboot.practica11.circuitbreaker.service.exception.ServiceException;

@Slf4j
@RequiredArgsConstructor
public class CircuitBreakerBusinessService implements IBusinessService {

	// Defina Target object IBusinessService businessService
	private final IBusinessService targetBusiessBusinessService;

	// Defina propiedad Circuit Breaker
	private final CircuitBreaker circuitBreaker;

	// Defina propiedad Supplier<StatusResponse> decoratedSupplier
	private Supplier<StatusResponse> decoratedSupplier;

	// Inyecte por constructor propiedades IBusinessService businessService, CircuitBreaker circuitBreaker
	// En el constructor decore el Supplier mediante CircuitBreaker.decorateSupplier(this.circuitBreaker, this.businessService::perform);

	@PostConstruct
	public void setUp() {
		this.decoratedSupplier = CircuitBreaker.decorateSupplier(this.circuitBreaker, 
																 targetBusiessBusinessService::perform);
	}
	
	@Override
	public StatusResponse perform() throws ServiceException {
		
		return Try.ofSupplier(this.decoratedSupplier)
				  .recover(this::fallback)
				  .get();
	}

	// Defina metodo fallback
	public StatusResponse fallback(Throwable throwable){
		log.info("exception {} occured, message: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
		
		return new StatusResponse(204, "Default message"); // OK => 200, UP
	}

}
