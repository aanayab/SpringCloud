package mx.com.truper.springboot.practica11.circuitbreaker._config;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import mx.com.truper.springboot.practica11.circuitbreaker.service.IBusinessService;
import mx.com.truper.springboot.practica11.circuitbreaker.service.exception.FailingServiceException;
import mx.com.truper.springboot.practica11.circuitbreaker.service.impl.BusinessService;
import mx.com.truper.springboot.practica11.circuitbreaker.service.impl.CircuitBreakerBusinessService;

@Configuration
public class ApplicationConfig {

	// Define Bean Rest template
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// Inyecta propiedad String failingServiceURL
	@Value("${failing.service.url}")
	private String failingServiceURL;

	// Define Bean IBusinessService noCircuitBreakerBusinessService
	// de tipo concreto BusinessService
	@Bean
	public IBusinessService noCircuitBreakerBusinessService() {
		return new BusinessService(restTemplate(), failingServiceURL);
	}	
	
	// Defina Bean CircuitBreakerConfig circuitBreakerConfig
	@Bean
	public CircuitBreakerConfig circuitBreakerConfig() {
		
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
				.failureRateThreshold(50)
				.waitDurationInOpenState(Duration.ofMillis(5000))
				.ringBufferSizeInHalfOpenState(20)
				.ringBufferSizeInClosedState(20)
				.recordExceptions(IOException.class, TimeoutException.class, FailingServiceException.class)
				.build();
		
		return circuitBreakerConfig;
	}
	
	// Defina Bean CircuitBreakerRegistry circuitBreakerRegistry
	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig());
		return circuitBreakerRegistry;
	}
	
	// Defina Bean CircuitBreaker circuitBreaker
	@Bean
	public CircuitBreaker circuitBreaker() {
		return circuitBreakerRegistry().circuitBreaker("my-circuit-breaker");
	}
	
	// Define Bean IBusinessService circuitBreakerBusinessService
	// de tipo concreto CircuitBreakerBusinessService
	@Bean
	@Primary
	public IBusinessService circuitBreakerBusinessService() {
		return new CircuitBreakerBusinessService(noCircuitBreakerBusinessService(), circuitBreaker());
	}

}
