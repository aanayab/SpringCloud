package mx.com.truper.springboot.practica23.ribbon.randommicroservice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// Debe ser un Eureka Client (para conectarse y registrarse en eureka, como lo haces?)
@EnableEurekaClient
@RestController
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Random random() {
		return new Random();
	}

	@Bean
	public RandomService randomService(Random random) {
		return () -> {
			return random.nextInt(40);
		};
	}

	@Autowired
	private RandomService randomService;

	@Autowired
	private Environment env;

	@GetMapping("/next")
	public Map<String, Object> next() {
		log.info("sending random number");

		Map<String, Object> map = new LinkedHashMap<>();

		map.put("random", randomService.next());
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		return map;
	} // { 'random': 0-40, 'from': 'http://random-microservice:PORT'}

}

@FunctionalInterface
interface RandomService {
	int next();
}