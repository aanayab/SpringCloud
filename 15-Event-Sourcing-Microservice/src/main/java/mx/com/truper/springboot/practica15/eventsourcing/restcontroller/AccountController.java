package mx.com.truper.springboot.practica15.eventsourcing.restcontroller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica15.eventsourcing.domain.Account;
import mx.com.truper.springboot.practica15.eventsourcing.events.domainevents.AccountCreateEvent;
import mx.com.truper.springboot.practica15.eventsourcing.events.domainevents.MoneyDepositEvent;
import mx.com.truper.springboot.practica15.eventsourcing.events.domainevents.MoneyWithdrawalEvent;
import mx.com.truper.springboot.practica15.eventsourcing.holder.AccountHolder;
import mx.com.truper.springboot.practica15.eventsourcing.processor.DomainEventProcessor;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private DomainEventProcessor domainEventProcessor;

	@GetMapping("/")
	public Map<Integer, Account> accounts() {

		// Implementa
		return AccountHolder.getAll();
	}

	@GetMapping("/{tenant}")
	public Account accountOf(@PathVariable String tenant) {

		// Implementa
		return AccountHolder.getAccount(tenant);
	}

	@GetMapping("/{tenant}/create")
	public String createAccountOf(@PathVariable String tenant) {

		// Implementa
		AccountCreateEvent newAccount = new AccountCreateEvent(
				AccountHolder.nextEventId(), new Date().getTime(), 
				AccountHolder.nextAccountId(), tenant);
		
		domainEventProcessor.process(newAccount);
		
		Account account = AccountHolder.getAccount(tenant);
		
		log.info("Account for {} created, {}", account.getOwner(), account);
		
		return "Account for "+account.getOwner()+" Created";
	}

	@GetMapping("/{tenant}/deposit/{amount}")
	public String accountDeposit(@PathVariable String tenant, @PathVariable BigDecimal amount) {

		// Implementa
		Account account = AccountHolder.getAccount(tenant);
		
		MoneyDepositEvent depositEvent = new MoneyDepositEvent(
				AccountHolder.nextEventId(), new Date().getTime(), 
				account.getAccountNo(), amount);
		
		domainEventProcessor.process(depositEvent);
		
		account = AccountHolder.getAccount(tenant);
		
		log.info("Account deposit for {} done, {}", account.getOwner(), account);
		
		return "Deposit for Account of "+account.getOwner()+" done";
	}

	@GetMapping("/{tenant}/withdrawal/{amount}")
	public String accountWithdrawal(@PathVariable String tenant, @PathVariable BigDecimal amount) {

		// Implementa
		Account account = AccountHolder.getAccount(tenant);
		
		MoneyWithdrawalEvent withdrawalEvent = new MoneyWithdrawalEvent(
				AccountHolder.nextEventId(), new Date().getTime(), 
				account.getAccountNo(), amount);
		
		domainEventProcessor.process(withdrawalEvent);
		
		account = AccountHolder.getAccount(tenant);
		
		log.info("Account withdrawal for {} done, {}", account.getOwner(), account);
		
		return "Withdrawal for Account of "+account.getOwner()+" done";
	}
}
