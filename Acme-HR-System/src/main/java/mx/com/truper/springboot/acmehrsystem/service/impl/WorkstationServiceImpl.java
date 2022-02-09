package mx.com.truper.springboot.acmehrsystem.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.truper.springboot.acmehrsystem.exceptions.WorkstationNotFoundException;
import mx.com.truper.springboot.acmehrsystem.model.Workstation;
import mx.com.truper.springboot.acmehrsystem.repository.WorkstationRepository;
import mx.com.truper.springboot.acmehrsystem.service.WorkstationService;

@Service
@Transactional
public class WorkstationServiceImpl implements WorkstationService {

	@Autowired
	private WorkstationRepository workstationRepository;

	@Override
	public Workstation retrieveById(Long id) {
		return workstationRepository.findById(id)
				.orElseThrow(() -> new WorkstationNotFoundException("Workstation not found"));
	}

	@Override
	public List<Workstation> retrieveAll() {
		return workstationRepository.findAll();
	}

	@Override
	public Workstation register(Workstation workstation) {
		workstation = workstationRepository.save(workstation);
		return workstation;
	}

	@Override
	public Workstation update(Workstation workstation) {
		return register(workstation);
	}

	@Override
	public Workstation delete(Workstation workstation) {
		workstationRepository.delete(workstation);
		return workstation;
	}

	@Override
	public List<Workstation> retrieveByVendor(String vendor) {
		return workstationRepository.findByVendor(vendor);
	}

	@Override
	public List<Workstation> retrieveByFacilitiesSerialNumber(String facilitiesSerialNumber) {
		return workstationRepository.findByFacilitiesSerialNumber(facilitiesSerialNumber);
	}

	@Override
	public Workstation retrieveByEmployeeId(long employeeId) {
		return workstationRepository.findByEmployeeId(employeeId);
	}

}
