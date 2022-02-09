package mx.com.truper.springboot.practica26.zuul.employeemicroservice.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica26.zuul.employeemicroservice.MyListener;

@FeignClient(name = "${random-microservice.service-name:random-microservice}", 
			 path = "${random-microservice.context-path:random-service}",
			 fallback = IRandomServiceFallback.class)
public interface IRandomService {

	@GetMapping("/next")
	public Map<String, Object> next();
}

@Slf4j
@Component
class IRandomServiceFallback implements IRandomService {

	@Autowired
	private Environment env;

	@Override
	public Map<String, Object> next(){
		Map<String, Object> map = new LinkedHashMap<>();

		map.put("random", -1);
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		log.info("[Fallback IRandomService] sending random {}", map.get("random"));
		return map;
	}

}
