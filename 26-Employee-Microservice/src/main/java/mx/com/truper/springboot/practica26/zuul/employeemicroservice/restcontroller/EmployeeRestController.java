package mx.com.truper.springboot.practica26.zuul.employeemicroservice.restcontroller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica26.zuul.employeemicroservice.MyListener;
import mx.com.truper.springboot.practica26.zuul.employeemicroservice.service.EmployeesService;

@Slf4j
@RestController
public class EmployeeRestController {

	@Autowired
	private EmployeesService employeesService;

	@Autowired
	private Environment env;

	@GetMapping("/employees")
	public Map<String, Object> age() {

		log.info("sending employees");

		Map<String, Object> map = new LinkedHashMap<>();

		map.put("employees", employeesService.getEmployees());
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		return map;
	}
}
