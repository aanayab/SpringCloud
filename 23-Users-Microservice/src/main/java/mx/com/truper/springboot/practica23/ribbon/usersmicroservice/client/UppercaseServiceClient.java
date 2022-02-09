package mx.com.truper.springboot.practica23.ribbon.usersmicroservice.client;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UppercaseServiceClient implements IUppercaseService {

	@Autowired
	private RestTemplate loadBalancedRestTemplate;

	@Value("${uppercase-service-name:uppercase-microservice}")
	private String serviceName;

	@Override
	@SneakyThrows
	public String toUppercase(String name) { // name = 'Ivan Garcia'

		// build URI using service-name
		name = URLEncoder.encode(name, "UTF-8").replace("+", "%20");

		String url = String.format("http://%s/uppercase-service/toUppercase/%s", serviceName, name);
		URI uri = new URI(url);

		// getting map from loadBalanced RestTemplate
		@SuppressWarnings("unchecked")
		Map<String, Object> mapResponse = loadBalancedRestTemplate.getForObject(uri, Map.class);
		// { 'uppercase': 'IVAN GARCIA', 'from': 'http://uppercase-microservice:PORT'}

		log.info("{} response: {}", serviceName, mapResponse);

		return (String) mapResponse.get("uppercase");
	}

}
