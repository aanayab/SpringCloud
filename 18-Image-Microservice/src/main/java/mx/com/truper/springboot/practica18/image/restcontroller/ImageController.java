package mx.com.truper.springboot.practica18.image.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

	@GetMapping("/image-path")
	public String getImagePath() {
		return "/product-image.png";
	}
}
