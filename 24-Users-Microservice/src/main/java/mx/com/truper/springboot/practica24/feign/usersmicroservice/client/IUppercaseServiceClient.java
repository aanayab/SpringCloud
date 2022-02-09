package mx.com.truper.springboot.practica24.feign.usersmicroservice.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Agrega Feign Client
@FeignClient(name = "${uppercase-microservice.service-name:uppercase-microservice}",
			 path = "${uppercase-microservice.context-path:uppercase-service}")
public interface IUppercaseServiceClient {

	// Agrega "/toUppercase/{name}" edpoint mediante Metodo GET.
	@GetMapping("/toUppercase/{name}")
	public Map<String, Object> toUppercase(@PathVariable String name);
	
}
