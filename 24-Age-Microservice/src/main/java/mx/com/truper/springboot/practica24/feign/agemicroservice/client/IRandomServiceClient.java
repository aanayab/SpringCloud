package mx.com.truper.springboot.practica24.feign.agemicroservice.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// Agrega Feign Client
@FeignClient(name = "${random-microservice.service-name:random-microservice}", 
			 path = "${random-microservice.context-path:random-service}")
public interface IRandomServiceClient {

	// GET http://random-microservice/random-service/next
	// Agrega "/next" edpoint mediante Metodo GET.
	@GetMapping("/next")
	public Map<String, Object> next();
	
}
