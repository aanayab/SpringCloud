package mx.com.truper.springboot.practica8.acmehrsystem.workstation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.truper.springboot.practica8.acmehrsystem.workstation.model.Workstation;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {

	List<Workstation> findByVendor(String vendor);

	List<Workstation> findByFacilitiesSerialNumber(String facilitiesSerialNumber);

	Workstation findByEmployeeId(long employeeId);
}
