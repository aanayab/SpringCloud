package mx.com.truper.springboot.practica24.feign.usersmicroservice.restcontroller;

import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica24.feign.usersmicroservice.client.IAgeServiceClient;
import mx.com.truper.springboot.practica24.feign.usersmicroservice.client.IUppercaseServiceClient;
import mx.com.truper.springboot.practica24.feign.usersmicroservice.model.User;

@Slf4j
@RestController
public class UsersRestController {

	@Autowired
	private IAgeServiceClient ageServiceClient;

	@Autowired
	private IUppercaseServiceClient uppercaseServiceClient;

	@SneakyThrows
	@GetMapping("/{name}")
	public User users(@PathVariable String name) {

		log.info("Sending User");

		name = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
		
		Map<String, Object> uppercaseResponse = uppercaseServiceClient.toUppercase(name);
		
		log.info("uppercase-microservice response: {}", uppercaseResponse);
		
		Map<String, Object> ageResponse = ageServiceClient.age();
		
		log.info("age-microservice response: {}", ageResponse);
		
		return new User(uppercaseResponse.get("uppercase").toString(),
						(int)ageResponse.get("age"));
	}
}
