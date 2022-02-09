package mx.com.truper.springboot.practica19.account.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountCreatedEvent {

	public int id;

	public String accountNumber;

	public int userId;

}
