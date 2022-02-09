package mx.com.truper.springboot.practica17.springbootadminclient.restcontroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SpringBootAdminClientController {

	@Value("${spring.application.name}")
	private String appName;
	
	@GetMapping
	public String hello() {
		
		String message = String.format("Hello I'm %s app !!!", appName);
		
		log.info("{}", message);
		
		return message;
	}
}