package mx.com.truper.springboot.practica18.gateway.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.truper.springboot.practica18.gateway.client.ImageMicroserviceClient;

@Service
public class ImageMicroserviceClientImpl implements ImageMicroserviceClient {

	@Value("${image-microservice.url}")
	private String imageMicroserviceUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public String getImagePath() {
		
		// Implementar
		String imagePath = restTemplate.getForObject(imageMicroserviceUrl+"/image-path", String.class);

		return imagePath;
	}
}
