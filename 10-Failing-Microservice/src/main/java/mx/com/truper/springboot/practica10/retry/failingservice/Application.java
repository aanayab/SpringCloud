package mx.com.truper.springboot.practica10.retry.failingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Define Bean Rest Controller
@RestController
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Implementa handler method "/{statusCode}"
	@GetMapping("/{statusCode}")
	public ResponseEntity<StatusResponse> getResponse(@PathVariable int statusCode) {
		
		if(statusCode == 200)
			return ResponseEntity.ok(new StatusResponse(200, "UP"));
		
		return ResponseEntity.status(500).body(new StatusResponse(500, "DOWN"));
	}
}
