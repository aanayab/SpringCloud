package mx.com.truper.springboot.practica23.ribbon.uppercasemicroservice.restcontroller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica23.ribbon.uppercasemicroservice.MyListener;
import mx.com.truper.springboot.practica23.ribbon.uppercasemicroservice.service.UppercaseService;

@Slf4j
@RestController
public class ToUppercaseRestController {

	@Autowired
	private UppercaseService uppercaseService;

	@Autowired
	private Environment env;

	@GetMapping("/toUppercase/{name}")
	public Map<String, Object> toUppercase(@PathVariable String name) {

		log.info("sending toUppercase");

		Map<String, Object> map = new LinkedHashMap<>();

		map.put("uppercase", uppercaseService.toUppercase(name));
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		return map;
	}
}
/*
{
	'uppercase': 'IVAN GARCIA',
	'from': 'http://auppercase-microservice:PORT'
}
*/