package mx.com.truper.springboot.practica25.hystrix.nounmicroservice;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@EnableHystrixDashboard
@EnableHystrix //@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
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

	@Autowired
	private ExclamationMarkServiceClient exclamationMarkServiceClient;

	@SneakyThrows
	@GetMapping("/word")
	public String word() {

		String word = wordsArray[random.nextInt(wordsArray.length)];

		log.info("[{}] Before requesting exclamation-mark-microservice", serviceName);

		word += " " + exclamationMarkServiceClient.getExclamationMark();

		log.info("[{}] sending word {}", serviceName, word);

		return word;
	}
}