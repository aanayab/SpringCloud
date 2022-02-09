package mx.com.truper.springboot.practica23.ribbon.usersmicroservice.client;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("load-balanced-rest-template")
public class LoadBalancedRestTemplateUppercaseServiceClient implements IUppercaseService {

	@Value("${uppercase-service-name:uppercase-microservice}")
	private String serviceName;

	@Autowired
	private RestTemplate loadBalancedRestTemplate;

	@Override
	@SneakyThrows
	public String toUppercase(String name) {

		name = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
		
		URI uri = new URI(String.format("http://%s/uppercase-service/toUppercase/%s",
				serviceName, name));

		@SuppressWarnings("unchecked")
		Map<String, Object> mapResponse = loadBalancedRestTemplate.getForObject(uri, Map.class);

		log.info("[@LoadBalanced RestTemplate implementation] generating uppercase value for {}", name);
		log.info("{} response: {}", serviceName, mapResponse);

		return (String) mapResponse.get("uppercase");

	}

}
