package mx.com.truper.springboot.practica24.feign.agemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Logger;

// Habilita Feign Clients
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Define bean Logger.Level feignLoggerLevel
	@Bean
	public Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
}
