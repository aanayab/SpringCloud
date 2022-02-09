package mx.com.truper.springboot.practica19.account._config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Bean
	public Queue userCreatedAccountQueue() {
		return new Queue("usercreated.account.queue", true);
	}

	@Bean
	public Queue accountCreatedLogQueue() {
		return new Queue("accountcreated.log.queue", true);
	}
}
