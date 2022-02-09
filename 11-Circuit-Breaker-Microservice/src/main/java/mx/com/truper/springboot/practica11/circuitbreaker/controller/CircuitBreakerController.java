package mx.com.truper.springboot.practica11.circuitbreaker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.truper.springboot.practica11.circuitbreaker.controller.model.StatusResponse;
import mx.com.truper.springboot.practica11.circuitbreaker.service.IBusinessService;

@RestController
public class CircuitBreakerController {

	@Autowired
	private IBusinessService bs;

	@GetMapping("/")
	public StatusResponse getResponse() {
		return bs.perform();
	}

}
