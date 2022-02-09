package mx.com.truper.springboot.practica21.users.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@RefreshScope
@SpringBootApplication
public class Application implements ApplicationListener<RefreshScopeRefreshedEvent> {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Value("${hello.message:no message}")
	private String hi;

	@Value("${spring.profiles.active:no active profile}")
	private String profile;

	@Bean
	public CommandLineRunner startUp() {
		return (args) -> {
			System.out.println(hi);
			System.out.println(profile);
		};
	}

	@Override
	public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
		System.out.println(hi);
		System.out.println(profile);
	}
}
