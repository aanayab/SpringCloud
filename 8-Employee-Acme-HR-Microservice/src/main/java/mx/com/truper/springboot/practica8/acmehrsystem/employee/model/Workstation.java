package mx.com.truper.springboot.practica8.acmehrsystem.employee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workstation {

	private long id;

	private String vendor;

	private String model;

	private String facilitiesSerialNumber;
	
	// @JsonIgnore
	private Long employeeId;
}
