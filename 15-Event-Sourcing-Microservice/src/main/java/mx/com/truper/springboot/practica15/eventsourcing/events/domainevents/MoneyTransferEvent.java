package mx.com.truper.springboot.practica15.eventsourcing.events.domainevents;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import mx.com.truper.springboot.practica15.eventsourcing.domain.Account;
import mx.com.truper.springboot.practica15.eventsourcing.events.DomainEvent;
import mx.com.truper.springboot.practica15.eventsourcing.events.exception.AccountException;
import mx.com.truper.springboot.practica15.eventsourcing.holder.AccountHolder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MoneyTransferEvent extends DomainEvent {

	private static final long serialVersionUID = 1L;

	private BigDecimal money;
	private int accountNoFrom;
	private int accountNoTo;

	// Define constructor
	public MoneyTransferEvent(long sequenceId, long createdTime, BigDecimal money, int accountNoFrom,
																				   int accountNoTo) {
		super(sequenceId, createdTime, MoneyTransferEvent.class.getSimpleName());
		this.accountNoFrom = accountNoFrom;
		this.accountNoTo = accountNoTo;
		this.money = money;
	}

	@Override
	public void process() {

		// Implementa
		Account accountFrom = AccountHolder.getAccount(accountNoFrom);
		if (accountFrom == null) {
			throw new AccountException("Account not found " + accountNoFrom);
		}
		
		Account accountTo = AccountHolder.getAccount(accountNoTo);
		if (accountTo == null) {
			throw new AccountException("Account not found " + accountNoTo);
		}
		
		accountFrom.handleTransferFromEvent(this);
		
		accountTo.handleTransferToEvent(this);
	}
}
