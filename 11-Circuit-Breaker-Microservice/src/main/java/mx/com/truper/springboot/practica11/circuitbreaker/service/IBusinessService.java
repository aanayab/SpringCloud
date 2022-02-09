package mx.com.truper.springboot.practica11.circuitbreaker.service;

import mx.com.truper.springboot.practica11.circuitbreaker.controller.model.StatusResponse;

@FunctionalInterface
public interface IBusinessService {

	StatusResponse perform();
}
