package mx.com.truper.springboot.practica23.ribbon.usersmicroservice.client;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgeServiceClient {

	@Autowired
	private Random random;

	public int getAge() {
		return random.nextInt(40);
	}

}
