package mx.com.truper.springboot.practica7.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import mx.com.truper.springboot.practica7.appconfig.Feature;
import mx.com.truper.springboot.practica7.spel.SpelUtils;

@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private MeterRegistry registry;

	@Autowired
	private Map<String, Feature> features;

	@Autowired
	private SpelUtils spelUtils;
	
	@Value("#{'Hola ' + str}") // expression SpEL
	private String saludo;

	@GetMapping("/")
	public String hello() {

		System.out.println("saludo: " + saludo);
		
		Metrics.counter("times.saying.hello", "times", "hello").increment();

		//registry.gauge("entity.status.count", 5);

		// System.out.println(features);

		System.out.println(spelUtils.resolveExpression("@features", Map.class));
		
		Map<String, Feature> map = spelUtils.resolveExpression("@features", Map.class);
		
		System.out.println(map.get("ocr"));

		// System.out.println(spelUtils.resolveExpression("(@features['perrototota']?.enabled)?:false",
		// boolean.class));

		// System.out.println("value: " + value);

		System.out.println(SpelUtils.resolveBean("(@features['say-hi']?.enabled)?:false", boolean.class));

		System.out.println(SpelUtils.resolve("(features['say-hi']?.enabled)?:false", boolean.class));
		System.out.println(SpelUtils.resolve("(@features['say-hi']?.enabled)?:false", boolean.class));

		if (SpelUtils.resolveBean("(@features['say-hi']?.enabled)?:false", boolean.class))
			return "hello";
		else
			return "bye";
	}

}
