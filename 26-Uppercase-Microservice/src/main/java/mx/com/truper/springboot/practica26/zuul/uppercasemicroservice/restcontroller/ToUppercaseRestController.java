package mx.com.truper.springboot.practica26.zuul.uppercasemicroservice.restcontroller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica26.zuul.uppercasemicroservice.MyListener;
import mx.com.truper.springboot.practica26.zuul.uppercasemicroservice.service.UppercaseService;

@Slf4j
@RestController
public class ToUppercaseRestController {

	@Autowired
	private UppercaseService uppercaseService;

	@Autowired
	private Environment env;

	@SneakyThrows
	@GetMapping("/toUppercase/{name}")
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> toUppercase(@PathVariable String name, 
										   @RequestHeader Map<String, String> headers) {

		headers.forEach((key, value) -> {
	        System.out.println(String.format("Header '%s' = %s", key, value));
	    });
		
		log.info("sending toUppercase 1");

		Map<String, Object> map = new LinkedHashMap<>();

		map.put("uppercase", uppercaseService.toUppercase(name));
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		//Thread.sleep(80_000);
		
		return map;
		
		// throw new RuntimeException("Fallo");
	}

	@GetMapping("/upper/toUppercase/{name}")
	public Map<String, Object> toUppercase2(@PathVariable String name) {

		log.info("sending toUppercase 2");

		Map<String, Object> map = new LinkedHashMap<>();

		map.put("uppercase", uppercaseService.toUppercase(name));
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		return map;
	}
}
