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
public class MoneyDepositEvent extends DomainEvent {

	private static final long serialVersionUID = 1L;

	private BigDecimal money;
	private int accountNo;

	// Define constructor
	public MoneyDepositEvent(long sequenceId, long createdTime, int accountNo, BigDecimal money) {
		super(sequenceId, createdTime, MoneyDepositEvent.class.getSimpleName());
		this.accountNo = accountNo;
		this.money = money;
	} 

	@Override
	public void process() {
		// Implementa
		Account account = AccountHolder.getAccount(accountNo);
		if(account == null) {
			throw new AccountException("Account not found");
		}
		account.handleEvent(this);
	}
}
