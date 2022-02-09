package mx.com.truper.springboot.practica19.user.observer;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica19.user.events.UserCreatedEvent;

@Slf4j
@Component
public class UserCreatedListener {

	// Inyecte Bean RabbitTemplate rabbitTemplate.
	@Autowired
	private RabbitTemplate rabbitTemplate;

	// Inyecte Bean FanoutExchange fanoutExchange
	@Autowired
	private FanoutExchange fanoutExchange;

	// Inyecte ObjectMapper objectMapper
	@Autowired
	private ObjectMapper objectMapper;

	@SneakyThrows
	// Manejador de evento UserCreatedEvent
	@EventListener
	public void handleUserCreatedEvent(UserCreatedEvent uce) {
		log.info("sending User Created Event to {}", fanoutExchange.getName());

		// Implemente
		rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", objectMapper.writeValueAsString(uce));
		
		log.info("--------------------------------------------------------------");
	}
}
