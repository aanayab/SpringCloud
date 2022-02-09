package mx.com.truper.springboot.practica21.hello.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RefreshScope
@RestController
@SpringBootApplication
public class Application implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "dev");
		ctx = new SpringApplicationBuilder()
						.listeners(new Application())
						.sources(Application.class)
						.run(args);
	}

	@Value("${hello.message:no message}")
	private String hi;

	@Value("${spring.profiles.active:no active profile}")
	private String profile;
	
	@Value("${usuario:no user set}")
	private String usuario;

	@Value("${password:no password set}")
	private String password;

	// localhost:9091/hello-config-client
	@GetMapping
	public String hi() {
		log.info("usuario: {}", usuario);
		log.info("password: {}", password);
		
		return hi + ", using profile: " + profile;
	}

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		if (event.getEnvironment().getActiveProfiles().length == 0) {
			log.error("No active profile set... set dev, pre or pro active profiles");
			System.exit(1);
		}
	}

	@Value("${someKey:no value}")
	private String someKey;

	@GetMapping("/someKey")
	public String someKey() {
		return "someKey = " + someKey;
	}
	
	/*@GetMapping("/restart")
	public String restart() {
		Application.doRestart();
		return "restarging";
	}

	public static void doRestart() {
		ApplicationArguments args = ctx.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			ctx.close();
			ctx = new SpringApplicationBuilder().listeners(new Application()).sources(Application.class)
					.run(args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}*/

}
