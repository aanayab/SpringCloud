package mx.com.truper.springboot.practica23.ribbon.agemicroservice.client;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RandomMicroserviceRestTemplateClient implements IRandomServiceClient {

	@Autowired
	private RestTemplate loadBalancedRestTemplate;

	@Value("${random-service-name:random-microservice}")
	private String serviceName;

	@Override
	@SneakyThrows
	public int getRandomValue() {

		log.info(
				"[Random microservice call implementation using @LoadBalanced RestTemplate] getting random value from service");

		// build URI using service-name
		String url = String.format("http://%s/random-service/next", serviceName);
		URI uri = new URI(url);

		// getting map from loadBalanced RestTemplate
		@SuppressWarnings("unchecked")
		Map<String, Object> mapResponse = loadBalancedRestTemplate.getForObject(uri, Map.class);
		// { 'random': 30, 'from': 'http://random-microservice:PORT'}

		log.info("{} response: {}", serviceName, mapResponse);

		return (int) mapResponse.get("random");
	}

}
