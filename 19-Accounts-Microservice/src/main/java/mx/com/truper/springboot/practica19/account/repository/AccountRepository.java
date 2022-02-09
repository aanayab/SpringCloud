package mx.com.truper.springboot.practica19.account.repository;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica19.account.model.Account;

@Slf4j
@Component
public class AccountRepository {

	private Random random = new Random();

	public static int nexAccountId() {
		return (int) (Math.random() * 1000000L);
	}

	public void save(Account account) {
		account.setId(nexAccountId());
		account.setAccountNumber(
				String.valueOf((random.nextLong() % 100000000000000L) + 5200000000000000L));
		account.setUserId(account.getUserId());
		log.info("account {} saved", account);
	}
}
