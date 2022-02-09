package mx.com.truper.springboot.practica19.log._config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Bean
	public Queue userCreatedLogQueue() {
		return new Queue("usercreated.log.queue", true);
	}

	@Bean
	public Queue accountCreatedLogQueue() {
		return new Queue("accountcreated.log.queue", true);
	}
}
