package mx.com.truper.springboot.practica18.gateway.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.truper.springboot.practica18.gateway.client.PriceMicroserviceClient;

@Service
public class PriceMicroserviceClientImpl implements PriceMicroserviceClient {

	@Value("${price-microservice.url}")
	private String priceMicroserviceUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String getPrice() {

		// Implementar
		String price = restTemplate.getForObject(priceMicroserviceUrl + "/price", String.class);

		return price;
	}
}
