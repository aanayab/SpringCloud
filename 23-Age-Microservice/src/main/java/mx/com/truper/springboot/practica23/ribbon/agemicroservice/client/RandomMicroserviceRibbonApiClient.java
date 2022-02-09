package mx.com.truper.springboot.practica23.ribbon.agemicroservice.client;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Component
public class RandomMicroserviceRibbonApiClient implements IRandomServiceClient {

	@Autowired
	private LoadBalancerClient loadBalancer;

	private RestTemplate simpleRestTemplate = new RestTemplate();

	@Value("${random-service-name:random-microservice}")
	private String serviceName;

	@Override
	@SneakyThrows
	public int getRandomValue() {

		log.info(
				"[Random microservice call implementation using LoadBalancerClient] getting random value from service");

		ServiceInstance instance = loadBalancer.choose(serviceName);

		// build URI using service-name
		String url = String.format("http://%s:%s/random-service/next", instance.getHost(), instance.getPort());
		URI uri = new URI(url);

		// getting map from loadBalanced RestTemplate
		@SuppressWarnings("unchecked")
		Map<String, Object> mapResponse = simpleRestTemplate.getForObject(uri, Map.class);
		// { 'random': 30, 'from': 'http://random-microservice:PORT'}

		log.info("{} response: {}", serviceName, mapResponse);

		return (int) mapResponse.get("random");
	}

}
