package mx.com.truper.springboot.practica9.embedded.broker.service.restcontroller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import mx.com.truper.springboot.practica9.embedded.broker.service.model.Order;
import mx.com.truper.springboot.practica9.embedded.broker.service.producer.OrderProducer;

// Define Bean Rest Controller
@RestController
public class OrderController {

	private static int i = 1;

	// Inyecta OrderProducer
	@Autowired
	private OrderProducer orderProducer;

	// Implementa handler method "/place-order"
	@GetMapping("/place-order/{content}")
	public int placeOrder(@PathVariable String content) {

		Order order = new Order(i, content, new Date());

		orderProducer.send(order);

		return i++;
	}

}