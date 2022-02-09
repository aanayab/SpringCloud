package mx.com.truper.springboot.practica18.price.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

	@GetMapping("/price")
	public String getPrice() {
		return "20";
	}
}
