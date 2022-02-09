package mx.com.truper.springboot.practica7._config;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.truper.springboot.practica7.appconfig.Feature;

@Configuration
public class CustomFeaturesConfig {

	private Map<String, Feature> features;

	public CustomFeaturesConfig(Map<String, Feature> features) {
		this.features = features;
	}

	@Bean
	public CommandLineRunner setupFeatures() {
		return (args) -> {
			features.put("ocr", Feature.builder().name("ocr").enabled(true).build());
			features.put("visor", Feature.builder().name("visor").enabled(true).build());
			features.put("common-email", Feature.builder().name("common-email").enabled(true).build());
			features.put("is-afternoon", Feature.builder().name("is-afternoon").enabled(true).build());
			features.put("say-hi", Feature.builder().name("say-hi").enabled(true).build());
		};
	}
}
