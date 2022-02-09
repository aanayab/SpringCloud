package mx.com.truper.springboot.practica19.log.rabbitlistener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica19.log.model.Account;
import mx.com.truper.springboot.practica19.log.repository.AccountLogRepository;

@Slf4j
@Component
public class AccountCreatedLogRabbitListener {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AccountLogRepository accountLogRepository;

	@SneakyThrows
	// Defina listener RabbitListener
	@RabbitListener(queues = { "#{accountCreatedLogQueue.name}" })
	public void handleUserCreatedEvent(String message) {
		log.info("rabbit listener Account Created Log Event");
		
		// Recupere objeto Account
		Account account = objectMapper.readValue(message, Account.class);

		log.info("event: {}", account);

		// Almacene objeto Account
		accountLogRepository.save(account);

		log.info("--------------------------------------------------------------");
	}
}
