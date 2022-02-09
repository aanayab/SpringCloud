package mx.com.truper.springboot.practica15.eventsourcing._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

// Define bean de configuracion
@Configuration
public class ApplicationConfig {

	// Define bean Gson gson
	@Bean
	public Gson gson() {
		return new Gson();
	}

}
