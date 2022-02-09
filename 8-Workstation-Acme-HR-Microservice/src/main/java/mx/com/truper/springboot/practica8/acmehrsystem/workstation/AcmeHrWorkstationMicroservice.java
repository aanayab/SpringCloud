package mx.com.truper.springboot.practica8.acmehrsystem.workstation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mx.com.truper.springboot.practica8.acmehrsystem.utils.HRUtils;
import mx.com.truper.springboot.practica8.acmehrsystem.workstation.model.Workstation;
import mx.com.truper.springboot.practica8.acmehrsystem.workstation.repository.WorkstationRepository;

@SpringBootApplication
public class AcmeHrWorkstationMicroservice {

	public static void main(String[] args) {
		SpringApplication.run(AcmeHrWorkstationMicroservice.class, args);
	}

	private WorkstationRepository workstationRepository;

	public AcmeHrWorkstationMicroservice(WorkstationRepository workstationRepository) {
		this.workstationRepository = workstationRepository;
	}

	@Bean
	public CommandLineRunner startup() {

		return (args) -> {

			Workstation workstation = Workstation.builder().vendor("Mac").model("Mac Book Pro 15 Retina")
					.facilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber()).build();

			workstationRepository.save(workstation);

			workstation = Workstation.builder().vendor("Mac").model("Mac Book Air 13")
					.facilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber()).build();

			workstationRepository.save(workstation);

			workstation = Workstation.builder().vendor("Mac").model("iMac Pro 25 Retina")
					.facilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber()).build();

			workstationRepository.save(workstation);

			Workstation ws = workstationRepository.findById(1L).get();

			ws.setEmployeeId(1L);

			workstationRepository.save(ws);
		};
	}

}
