package mx.com.truper.springboot.practica15.eventsourcing.restcontroller;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica15.eventsourcing.domain.Account;
import mx.com.truper.springboot.practica15.eventsourcing.events.domainevents.MoneyTransferEvent;
import mx.com.truper.springboot.practica15.eventsourcing.holder.AccountHolder;
import mx.com.truper.springboot.practica15.eventsourcing.processor.DomainEventProcessor;

@Slf4j
@RestController
@RequestMapping("/transfer")
public class TransferController {

	@Autowired
	private DomainEventProcessor domainEventProcessor;

	@GetMapping("/from/{tenantFrom}/to/{tenantTo}/amount/{amount}")
	public String accountOf(@PathVariable String tenantFrom,
							@PathVariable String tenantTo,
							@PathVariable BigDecimal amount) {

		// Implementa
		Account accountFrom = AccountHolder.getAccount(tenantFrom);
		Account accountTo = AccountHolder.getAccount(tenantTo);
		
		MoneyTransferEvent moneyTransferEvent  = new MoneyTransferEvent(
				AccountHolder.nextEventId(), new Date().getTime(), 
				amount, accountFrom.getAccountNo(), accountTo.getAccountNo());
		
		domainEventProcessor.process(moneyTransferEvent);
		
		String message = String.format("Transfer from %s to %s of $ %s done !", 
				accountFrom.getOwner(), accountTo.getOwner(), amount);
		
		log.info("{}", message);
		
		return message;
	}
}
