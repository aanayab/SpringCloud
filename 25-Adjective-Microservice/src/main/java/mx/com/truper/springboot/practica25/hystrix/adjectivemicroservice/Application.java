package mx.com.truper.springboot.practica25.hystrix.adjectivemicroservice;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@EnableEurekaClient
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Random random() {
		return new Random();
	}

	@Autowired
	private Random random;

	@Value("${words}")
	private String[] wordsArray;

	@Value("${spring.application.name}")
	private String serviceName;

	@GetMapping("/word")
	public String word() {

		String word = wordsArray[random.nextInt(wordsArray.length)];

		log.info("[{}] sending word {}", serviceName, word);

		return word;
	}
}