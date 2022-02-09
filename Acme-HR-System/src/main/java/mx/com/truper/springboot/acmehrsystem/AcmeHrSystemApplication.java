package mx.com.truper.springboot.acmehrsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mx.com.truper.springboot.acmehrsystem.model.Desk;
import mx.com.truper.springboot.acmehrsystem.model.Employee;
import mx.com.truper.springboot.acmehrsystem.model.Workstation;
import mx.com.truper.springboot.acmehrsystem.repository.DeskRepository;
import mx.com.truper.springboot.acmehrsystem.repository.EmployeeRepository;
import mx.com.truper.springboot.acmehrsystem.repository.WorkstationRepository;
import mx.com.truper.springboot.acmehrsystem.utils.HRUtils;

@SpringBootApplication
public class AcmeHrSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcmeHrSystemApplication.class, args);
	}

	private EmployeeRepository employeeRepository;

	private DeskRepository deskRepository;

	private WorkstationRepository workstationRepository;

	public AcmeHrSystemApplication(EmployeeRepository employeeRepository, DeskRepository deskRepository,
			WorkstationRepository workstationRepository) {

		this.employeeRepository = employeeRepository;
		this.deskRepository = deskRepository;
		this.workstationRepository = workstationRepository;
	}

	@Bean
	public CommandLineRunner startup() {

		return (args) -> {
			Employee employee = Employee.builder()
										.firstName("Ivan")
										.lastName("Garcia")
										.employeeNumber(HRUtils.nextEmployeeNumber())
										.build();

			System.out.println(employee);

			employeeRepository.save(employee);

			employee = Employee.builder()
							   .firstName("Fernanda")
							   .lastName("Morales")
							   .employeeNumber(HRUtils.nextEmployeeNumber())
							   .build();

			System.out.println(employee);

			employeeRepository.save(employee);

			Workstation workstation = Workstation.builder()
												 .vendor("Mac")
												 .model("Mac Book Pro 15 Retina")
												 .facilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber())
												 .build();

			workstationRepository.save(workstation);

			workstation = Workstation.builder()
									 .vendor("Mac")
									 .model("Mac Book Air 13")
									 .facilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber())
									 .build();

			workstationRepository.save(workstation);

			workstation = Workstation.builder()
									 .vendor("Mac")
									 .model("iMac Pro 25 Retina")
									 .facilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber())
									 .build();

			workstationRepository.save(workstation);

			for (int i = 1; i < 20; i++) {
				Desk desk = Desk.builder()
								.floor("1st Floor")
								.deskNumber(HRUtils.nextDeskNumber()).build();

				deskRepository.save(desk);
			}

			Desk desk = deskRepository.findById(1L).get();
			
			Workstation ws = workstationRepository.findById(1L).get();

			Employee ivan = employeeRepository.findById(1L).get();

			desk.setEmployee(ivan);
			ws.setEmployee(ivan);

			deskRepository.save(desk);
			workstationRepository.save(ws);
			
			
			Employee fernanda = employeeRepository.findById(2L).get();
			
			Workstation wsFernanda = workstationRepository.findById(3L).get();
			
			wsFernanda.setEmployee(fernanda);
			
			workstationRepository.save(wsFernanda);
		};
	}

}
