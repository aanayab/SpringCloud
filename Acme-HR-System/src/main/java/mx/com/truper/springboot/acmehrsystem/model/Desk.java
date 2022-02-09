package mx.com.truper.springboot.acmehrsystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Desk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String floor;

	private String deskNumber;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_EMPLOYEE_ID", nullable = true, updatable = true)
	private Employee employee;

}
