package mx.com.truper.springboot.acmehrsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankCheck {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Double total;

	private long serialNumber;

	private String beneficiaryFullName;

}
