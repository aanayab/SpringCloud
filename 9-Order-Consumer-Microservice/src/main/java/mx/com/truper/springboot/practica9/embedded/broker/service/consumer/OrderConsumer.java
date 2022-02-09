package mx.com.truper.springboot.practica9.embedded.broker.service.consumer;

import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica9.embedded.broker.service._config.ActiveMQConfig;
import mx.com.truper.springboot.practica9.embedded.broker.service.model.Order;

@Slf4j
// Define como Bean
@Component
public class OrderConsumer {

	// Define JMS Listener que escuche la queue destino ORDER_QUEUE
	@JmsListener(destination = ActiveMQConfig.ORDER_QUEUE)
	public void receiveMessage(@Payload Order order, @Headers MessageHeaders headers, Message message, Session session) {
		log.info("received <" + order.getId() + ">");

		log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
		log.info("######          Message Details           #####");
		log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
		log.info("content: " + order.getContent());
		log.info("timestamp: " + order.getTimestamp());
		log.info("- - - - -");
		log.info("headers: " + headers);
		log.info("message: " + message);
		log.info("session: " + session);
		log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
	}

}