package mx.com.truper.springboot.practica7.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import mx.com.truper.springboot.practica7.appconfig.Feature;
import mx.com.truper.springboot.practica7.spel.SpelUtils;

@RestController
public class HelloController {

	private static final String HELLO = "hello";
	private static final String BYE = "bye";

	private static final String GOOD_AFTERNOON = "good afternoon";
	private static final String GOOD_MORNING = "good morning";

	private static Long count = 0L;

	@Autowired
	private MeterRegistry registry;

	@Autowired
	private Map<String, Feature> features;

	@GetMapping("/{name}")
	public String hello(@PathVariable String name) {

		Metrics.counter("times.entering.hello.name", "times", "hello").increment();

		return buildPhrase(name);
	}

	@GetMapping("/ocr")
	public String ocr() {
		Metrics.counter("times.entering.ocr", "times", "ocr").increment();

		if (!SpelUtils.resolve("features['ocr'].enabled", boolean.class)) {

			Metrics.counter("times.ocr.down", "times", "ocr").increment();

			throw new UnsupportedOperationException("ocr is down");
		}

		return "ocr is up and running";
	}

	@GetMapping("/visor")
	public String visor() {
		Metrics.counter("times.entering.visor", "times", "visor").increment();

		if (!SpelUtils.resolve("features['visor'].enabled", boolean.class)) {

			Metrics.counter("times.visor.down", "times", "visor").increment();

			throw new UnsupportedOperationException("visor is down");
		}

		return "visor is up and running";
	}

	@GetMapping("/common-email")
	public String commonEmail() {
		Metrics.counter("times.entering.common-email", "times", "common-email").increment();

		if (!SpelUtils.resolve("features['common-email'].enabled", boolean.class)) {

			Metrics.counter("times.common-email.down", "times", "common-email").increment();

			throw new UnsupportedOperationException("common-email is down");
		}

		return "common-email is up and running";
	}

	private String buildPhrase(String name) {

		StringBuilder sb = new StringBuilder();

		if (SpelUtils.resolve("(features['say-hi']?.enabled)?:false", boolean.class))
			sb.append(HELLO);
		else
			sb.append(BYE);

		sb.append(' ').append(name).append(" !!, ");

		if (SpelUtils.resolve("(features['is-afternoon']?.enabled)?:false", boolean.class))
			sb.append(GOOD_AFTERNOON);
		else
			sb.append(GOOD_MORNING);

		sb.append(".");

		return sb.toString();
	}

}
