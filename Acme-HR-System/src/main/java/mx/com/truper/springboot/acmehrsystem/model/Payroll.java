package mx.com.truper.springboot.acmehrsystem.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int fortnightNumber;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Double grossSalary;

	private Double taxes;

	private Double netSalary;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_BANK_CHECK_ID")
	private BankCheck bankCheck;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_EMPLOYEE_ID")
	private @Getter(AccessLevel.NONE) Employee employee;

}
