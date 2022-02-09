package mx.com.truper.springboot.practica23.ribbon.usersmicroservice.client;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Service
public class UppercaseServiceClientRibbonApi implements IUppercaseService {

	@Autowired
	private LoadBalancerClient loadBalancer;
	
	private RestTemplate simplRestTemplate = new RestTemplate();

	@Value("${uppercase-service-name:uppercase-microservice}")
	private String serviceName;

	@Override
	@SneakyThrows
	public String toUppercase(String name) { // name = 'Ivan Garcia'

		// build URI using service-name
		name = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
		
		ServiceInstance instance = loadBalancer.choose(serviceName);

		String url = String.format("http://%s:%s/uppercase-service/toUppercase/%s", 
																instance.getHost(), instance.getPort(), name);
		URI uri = new URI(url);

		// getting map from loadBalanced RestTemplate
		@SuppressWarnings("unchecked")
		Map<String, Object> mapResponse = simplRestTemplate.getForObject(uri, Map.class);
		// { 'uppercase': 'IVAN GARCIA', 'from': 'http://uppercase-microservice:PORT'}

		log.info("{} response: {}", serviceName, mapResponse);

		return (String) mapResponse.get("uppercase");
	}

}
