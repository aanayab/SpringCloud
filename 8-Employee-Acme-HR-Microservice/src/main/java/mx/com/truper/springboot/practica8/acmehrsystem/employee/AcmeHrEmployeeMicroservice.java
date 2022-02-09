package mx.com.truper.springboot.practica8.acmehrsystem.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mx.com.truper.springboot.practica8.acmehrsystem.employee.model.Employee;
import mx.com.truper.springboot.practica8.acmehrsystem.employee.repository.EmployeeRepository;
import mx.com.truper.springboot.practica8.acmehrsystem.utils.HRUtils;

@SpringBootApplication
public class AcmeHrEmployeeMicroservice {

	private EmployeeRepository employeeRepository;

	public AcmeHrEmployeeMicroservice(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AcmeHrEmployeeMicroservice.class, args);
	}

	@Bean
	public CommandLineRunner startup() {

		return (args) -> {
			Employee employee = Employee.builder().firstName("Ivan").lastName("Garcia")
					.employeeNumber(HRUtils.nextEmployeeNumber()).build();

			System.out.println(employee);

			employeeRepository.save(employee);

			employee = Employee.builder().firstName("Fernanda").lastName("Morales")
					.employeeNumber(HRUtils.nextEmployeeNumber()).build();

			System.out.println(employee);

			employeeRepository.save(employee);

			
			
			/*Workstation ws = workstationRepository.findById(1L).get();

			Employee ivan = employeeRepository.findById(1L).get();

			desk.setEmployee(ivan);
			ws.setEmployee(ivan);

			deskRepository.save(desk);
			workstationRepository.save(ws);*/
		};
	}

}
