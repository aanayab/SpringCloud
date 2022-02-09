package mx.com.truper.springboot.practica19.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	private int id;

	private String accountNumber;

	private int userId;
}
