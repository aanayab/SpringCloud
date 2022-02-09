package mx.com.truper.springboot.practica23.ribbon.usersmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica23.ribbon.usersmicroservice.client.AgeServiceClient;
import mx.com.truper.springboot.practica23.ribbon.usersmicroservice.client.IUppercaseService;
import mx.com.truper.springboot.practica23.ribbon.usersmicroservice.model.User;

@Slf4j
@Service
public class UsersService {

	@Autowired
	private AgeServiceClient ageServiceClient;

	@Autowired
	private IUppercaseService uppercaseServiceClient;

	@SneakyThrows
	public User getUser(String name) {

		log.info("before sending GET Age HTTP Request");

		int age = ageServiceClient.getAge();

		log.info("age web service endpoint was called and response recieved ");

		log.info("before sending GET Age HTTP Request");

		String nameUppercase = uppercaseServiceClient.toUppercase(name);

		log.info("Age web service endpoint was called and response recieved ");

		return new User(nameUppercase, age);

	}

}
