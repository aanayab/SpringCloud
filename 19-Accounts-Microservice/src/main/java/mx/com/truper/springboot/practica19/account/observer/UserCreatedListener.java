package mx.com.truper.springboot.practica19.account.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica19.account.model.Account;
import mx.com.truper.springboot.practica19.account.service.AccountService;
import mx.com.truper.springboot.practica19.account.user.events.UserCreatedEvent;

@Slf4j
@Component
public class UserCreatedListener {

	@Autowired
	private AccountService accountService;

	@SneakyThrows
	@EventListener
	public void handleUserCreatedEvent(UserCreatedEvent uce) {
		log.info("handling User Created Event {}", uce);

		Account account = Account.builder()
				.userId(uce.getId())
				.build();

		accountService.createAccount(account);
		log.info("--------------------------------------------------------------");
	}
}
