package mx.com.truper.springboot.practica23.ribbon.uppercasemicroservice.service;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UppercaseService {

	public String toUppercase(String string) {

		log.info("to uppercase string: {}", string);

		return string.toUpperCase();
	}

}
