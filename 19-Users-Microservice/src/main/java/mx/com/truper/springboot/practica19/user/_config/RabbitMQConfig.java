package mx.com.truper.springboot.practica19.user._config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	// Define bean FanoutExchange fanoutExchange
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange("usercreated.fanout");
	}
	
	// Define bean Queue userCreatedAccountQueue
	@Bean
	public Queue userCreatedAccountQueue() {
		return new Queue("usercreated.account.queue", true);
	}

	// Define bean Queue userCreatedLogQueue
	@Bean
	public Queue userCreatedLogQueue() {
		return new Queue("usercreated.log.queue", true);
	}
	
	// Define Binding binding1
	@Bean
	public Binding binding1() {
		return BindingBuilder.bind(userCreatedAccountQueue()).to(fanoutExchange());
	}

	// Define Binding binding2
	@Bean
	public Binding binding2() {
		return BindingBuilder.bind(userCreatedLogQueue()).to(fanoutExchange());
	}
	
}
