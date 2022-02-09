package mx.com.truper.springboot.acmehrsystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "employee" })
@EqualsAndHashCode(exclude = { "employee" })
public class Workstation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String vendor;

	private String model;

	private String facilitiesSerialNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_EMPLOYEE_ID")
	@JsonIgnore
	private Employee employee;
}
