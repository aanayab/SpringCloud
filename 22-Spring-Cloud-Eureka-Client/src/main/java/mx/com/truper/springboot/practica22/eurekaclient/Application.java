package mx.com.truper.springboot.practica22.eurekaclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

// Habilita Discovery Client
//@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class Application {

	@Value("${server.port}")
	private String port;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner startup() {
		return (args) ->{
			System.out.println("port???: " + port);
		};
	}

}
