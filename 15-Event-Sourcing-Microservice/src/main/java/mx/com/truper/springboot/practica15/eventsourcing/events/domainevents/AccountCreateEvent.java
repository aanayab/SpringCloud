package mx.com.truper.springboot.practica15.eventsourcing.events.domainevents;

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
public class AccountCreateEvent extends DomainEvent {

	private static final long serialVersionUID = 1L;

	private int accountNo;
	private String owner;

	// Define constructor
	public AccountCreateEvent(long sequenceId, long createdTime, int accountNo, String owner) {
		super(sequenceId, createdTime, AccountCreateEvent.class.getSimpleName());
		this.accountNo = accountNo;
		this.owner = owner;
	} 

	@Override
	public void process() {
		// implementa
		Account account = AccountHolder.getAccount(owner);
		if(account != null) {
			throw new AccountException("Account already exists");
		}
		
		account = Account.builder()
						 .accountNo(accountNo)
						 .owner(owner)
						 .build();
		
		account.handleEvent(this);
	}
}
