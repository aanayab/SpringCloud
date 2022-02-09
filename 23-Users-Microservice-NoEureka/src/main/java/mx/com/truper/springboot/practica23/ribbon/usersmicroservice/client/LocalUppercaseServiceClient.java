package mx.com.truper.springboot.practica23.ribbon.usersmicroservice.client;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("local")
public class LocalUppercaseServiceClient implements IUppercaseService {

	@Override
	@SneakyThrows
	public String toUppercase(String name) {

		log.info("[local implementation] generating uppercase value for {}", name);

		return name.toUpperCase();
	}

}
