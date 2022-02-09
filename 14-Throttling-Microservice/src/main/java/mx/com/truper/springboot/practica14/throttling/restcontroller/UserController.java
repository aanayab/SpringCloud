package mx.com.truper.springboot.practica14.throttling.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.truper.springboot.practica14.throttling.restcontroller.model.Users;

@RestController
public class UserController {

	@Autowired
	private Users users;

	@GetMapping("/users")
	public Users getUser() {
		return users;
	}
}
