package mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.client.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.client.IAdjectiveServiceClient;

@Slf4j
@Service
public class AdjectiveServiceClient implements IAdjectiveServiceClient {

	@Value("${adjective-microservice.service-name:adjective-microservice}")
	private String serviceName;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@SneakyThrows
	// Define comando Hystrix
	@HystrixCommand(fallbackMethod = "defaultAdjective")
	public String getAdjective() {
		URI uri = new URI(String.format("http://%s/word", serviceName));

		log.info("calling service URI {}", uri.toURL());

		return restTemplate.getForObject(uri, String.class);
	}

	public String defaultAdjective(Throwable ex) {
		log.info("Fallback method entered, exception {}: {}", ex.getClass().getSimpleName(), ex.getMessage());
		log.error("exception", ex);
		return "(some adjective)";
	}

}
