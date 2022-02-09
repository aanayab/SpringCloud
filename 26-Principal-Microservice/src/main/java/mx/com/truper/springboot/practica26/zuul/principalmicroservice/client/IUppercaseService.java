package mx.com.truper.springboot.practica26.zuul.principalmicroservice.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica26.zuul.principalmicroservice.MyListener;

@FeignClient(name = "${uppercase-microservice.service-name:uppercase-microservice}", 
			 path = "${uppercase-microservice.context-path:uppercase-service}", 
			 fallback = IUppercaseServiceFallback.class)
public interface IUppercaseService {

	@GetMapping("/toUppercase/{name}")
	public Map<String, Object> toUppercase(@PathVariable String name);

}

@Slf4j
@Component
class IUppercaseServiceFallback implements IUppercaseService {
	
	@Autowired
	private Environment env;

	@Override
	public Map<String, Object> toUppercase(String name) {
		Map<String, Object> map = new LinkedHashMap<>();

		map.put("uppercase", name.toUpperCase().concat(":(from fallback)"));
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		log.info("[Fallback IUppercaseService] sending uppercase {}", map.get("uppercase"));
		return map;
	}

}