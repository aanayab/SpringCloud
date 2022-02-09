package mx.com.truper.springboot.practica26.zuul.principalmicroservice.restcontroller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica26.zuul.principalmicroservice.MyListener;
import mx.com.truper.springboot.practica26.zuul.principalmicroservice.service.PrincipalService;

@Slf4j
@RestController
public class PrincipalRestController {

	@Autowired
	private PrincipalService principalService;

	@Autowired
	private Environment env;

	@GetMapping("/principals")
	public Map<String, Object> age() {

		log.info("sending age");

		Map<String, Object> map = new LinkedHashMap<>();

		map.put("principals", principalService.getPrincipals());
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		return map;
	}
}
