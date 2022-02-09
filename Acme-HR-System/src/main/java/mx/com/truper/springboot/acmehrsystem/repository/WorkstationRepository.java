package mx.com.truper.springboot.acmehrsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.truper.springboot.acmehrsystem.model.Workstation;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {

	List<Workstation> findByVendor(String vendor);

	List<Workstation> findByFacilitiesSerialNumber(String facilitiesSerialNumber);

	Workstation findByEmployeeId(long employeeId);
}
