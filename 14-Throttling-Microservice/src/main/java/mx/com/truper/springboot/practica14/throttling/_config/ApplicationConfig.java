package mx.com.truper.springboot.practica14.throttling._config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.truper.springboot.practica14.throttling.restcontroller.model.User;
import mx.com.truper.springboot.practica14.throttling.restcontroller.model.Users;

// Define clase de Configuracion
@Configuration
public class ApplicationConfig {

	// Define bean Users
	@Bean
	public Users users() {
		return new Users(usersList());
	}

	// Define beanList<User>
	@Bean
	public List<User> usersList() {
		List<User> list = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			list.add(new User("Ivan " + i, 30 + i));
		}
		return list;
	}
}
