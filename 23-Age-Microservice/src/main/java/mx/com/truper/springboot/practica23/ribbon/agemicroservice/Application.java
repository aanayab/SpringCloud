package mx.com.truper.springboot.practica23.ribbon.agemicroservice;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// Habilita Eureka Client
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

	@Bean
	// Define el Bean RestTemplate como LoadBalanced 
	@LoadBalanced
	// Definido despues, al hacer la integración con 23-Random-Microservice
	public RestTemplate loadBalancedRestTemplate() {
		return new RestTemplate();
	}
}
