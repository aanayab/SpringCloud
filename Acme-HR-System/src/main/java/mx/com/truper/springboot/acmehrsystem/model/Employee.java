package mx.com.truper.springboot.acmehrsystem.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "desk", "payrolls" })
@EqualsAndHashCode(exclude = { "desk", "payrolls" })
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;

	private String lastName;

	@Column(unique = true)
	private String employeeNumber;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "employee", 
			cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Workstation workstation;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "employee", cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JsonIgnore
	private Desk desk; // @Getter(AccessLevel.NONE)

	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = {})
	private @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) List<Payroll> payrolls = new ArrayList<>();
}
