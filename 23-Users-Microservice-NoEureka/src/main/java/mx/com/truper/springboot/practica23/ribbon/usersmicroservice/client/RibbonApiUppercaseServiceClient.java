package mx.com.truper.springboot.practica23.ribbon.usersmicroservice.client;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("ribbon-api")
public class RibbonApiUppercaseServiceClient implements IUppercaseService {

	@Value("${uppercase-service-name:uppercase-microservice}")
	private String serviceName;

	@Autowired
	private LoadBalancerClient loadBalancer;

	private RestTemplate localRestTemplate = new RestTemplate();

	@Override
	@SneakyThrows
	public String toUppercase(String name) {
		
		name = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
		
		ServiceInstance instance = loadBalancer.choose(serviceName);

		URI uri = new URI(String.format("http://%s:%s/uppercase-service/toUppercase/%s",
				instance.getHost(), instance.getPort(), name));

		@SuppressWarnings("unchecked")
		Map<String, Object> mapResponse = localRestTemplate.getForObject(uri, Map.class);
		
		log.info("[Ribbon API implementation] generating random value from");
		log.info("{} response: {}", serviceName, mapResponse);

		return (String) mapResponse.get("uppercase");

	}

}
