package mx.com.truper.springboot.practica4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica4.profiles.bean.ConnectionDataBase;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		// Simular que estamos pasando la propiedad por linea de comandos en runtime
		System.setProperty("spring.profiles.active", "production");
		
		SpringApplication.run(Application.class, args);
		log.info("hola");
	}
	
	@Bean
	public CommandLineRunner startup(ConnectionDataBase connectionDataBase) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				for(String a : args) {
					System.out.println("argumento: " + a);
				}
				
				connectionDataBase.init();
			}
		};
	}

}
