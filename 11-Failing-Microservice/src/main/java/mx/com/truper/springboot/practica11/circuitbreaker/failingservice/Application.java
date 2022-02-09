package mx.com.truper.springboot.practica11.circuitbreaker.failingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Define Bean Rest Controller
@RestController
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Define handler method "/"
	@GetMapping("/")
	public ResponseEntity<StatusResponse> getResponse() {

		return ResponseEntity.ok(new StatusResponse(200, "UP"));
	}
}
