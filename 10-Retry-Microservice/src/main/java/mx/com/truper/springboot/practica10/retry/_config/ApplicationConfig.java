package mx.com.truper.springboot.practica10.retry._config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import mx.com.truper.springboot.practica10.retry.service.IBusinessService;
import mx.com.truper.springboot.practica10.retry.service.impl.BusinessService;
import mx.com.truper.springboot.practica10.retry.service.impl.RetryBusinessService;

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
	
	@Value("${retry.max-attempts}")
	private int maxAttempts;
	
	@Value("${retry.delay}")
	private long delay;
	
	// Define Bean IBusinessService noRetriableBusinessService
	// de tipo concreto BusinessService
	@Bean
	public IBusinessService noRetriableBusinessService() {
		return new BusinessService(restTemplate(), failingServiceURL);
	}

	// Define Bean IBusinessService retriableBusinessService
	// de tipo concreto RetryBusinessService
	@Bean
	@Primary
	public IBusinessService retriableBusinessService() {
		return new RetryBusinessService(noRetriableBusinessService(), maxAttempts, delay);
	}

}