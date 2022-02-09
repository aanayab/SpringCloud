package mx.com.truper.springboot.practica18.gateway._config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import mx.com.truper.springboot.practica18.gateway.client.ImageMicroserviceClient;
import mx.com.truper.springboot.practica18.gateway.client.PriceMicroserviceClient;

@Configuration
public class GatewayMicroserviceConfig {

	// Define RestTemplate restTemplate
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public CommandLineRunner startUp(ImageMicroserviceClient imageClient, 
									 PriceMicroserviceClient priceClient) {
		return (args) ->{
			System.out.println(imageClient.getImagePath());
			System.out.println(priceClient.getPrice());
		};
	}
}
