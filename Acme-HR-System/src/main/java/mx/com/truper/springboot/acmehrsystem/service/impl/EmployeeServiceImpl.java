package mx.com.truper.springboot.acmehrsystem.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.truper.springboot.acmehrsystem.exceptions.EmployeeNotFoundException;
import mx.com.truper.springboot.acmehrsystem.exceptions.WorkstationException;
import mx.com.truper.springboot.acmehrsystem.exceptions.WorkstationNotFoundException;
import mx.com.truper.springboot.acmehrsystem.model.Desk;
import mx.com.truper.springboot.acmehrsystem.model.Employee;
import mx.com.truper.springboot.acmehrsystem.model.Workstation;
import mx.com.truper.springboot.acmehrsystem.repository.DeskRepository;
import mx.com.truper.springboot.acmehrsystem.repository.EmployeeRepository;
import mx.com.truper.springboot.acmehrsystem.repository.WorkstationRepository;
import mx.com.truper.springboot.acmehrsystem.service.EmployeeService;
import mx.com.truper.springboot.acmehrsystem.utils.HRUtils;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DeskRepository deskRepository;

	@Autowired
	private WorkstationRepository workstationRepository;

	@Override
	public Employee retrieveById(Long id) {
		return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
	}

	@Override
	public List<Employee> retrieveAll() {
		return employeeRepository.findAllByOrderByIdAsc();
	}
	
	@Override
	public List<Employee> retrieveAllBetter() {
		return employeeRepository.customFindAllByOrderByIdAsc();
	}

	@Override
	public Employee register(Employee employee) {
		
		employee.setId(0);

		Workstation ws = employee.getWorkstation();

		if (ws != null && ws.getId() > 0) { // workstation exists...

			ws = workstationRepository.findById(ws.getId())
					.orElseThrow(() -> new WorkstationNotFoundException("Workstation not found"));

			Employee assignedEmlpoyee = ws.getEmployee();
			if (assignedEmlpoyee == null) {
				employee.setWorkstation(ws);
				ws.setEmployee(employee);
			} else {
				throw new WorkstationException("Workstation you tried to assign, already assigned to other Employee");
			}

			employee.setEmployeeNumber(HRUtils.nextEmployeeNumber());

			employee = employeeRepository.save(employee);

		} else if (ws != null && ws.getId() == 0) { // workstation not exists...

			employee.setEmployeeNumber(HRUtils.nextEmployeeNumber());

			employee = employeeRepository.save(employee);

			ws.setFacilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber());

			ws.setEmployee(employee);

			ws = workstationRepository.save(ws);

			employee.setWorkstation(ws);

		} else if (ws == null) { // create Employee without workstation

			employee.setEmployeeNumber(HRUtils.nextEmployeeNumber());

			employee = employeeRepository.save(employee);
		}

		return employee;
	}

	@Override
	public Employee update(Employee employee) {

		Employee registeredEmployee = employeeRepository.findById(employee.getId())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

		registeredEmployee.setFirstName(employee.getFirstName());
		registeredEmployee.setLastName(employee.getLastName());

		Workstation workstation = employee.getWorkstation();
		Workstation registeredWorkstation = null;

		if (workstation != null && workstation.getId() > 0) { // workstation exists...

			registeredWorkstation = workstationRepository.findById(workstation.getId())
					.orElseThrow(() -> new WorkstationNotFoundException("Workstation not found"));

			Employee assignedEmlpoyee = workstation.getEmployee();
			if (assignedEmlpoyee == null) { // if workstation is not assigned

				registeredEmployee.setWorkstation(registeredWorkstation);
				registeredWorkstation.setEmployee(registeredEmployee);

				registeredWorkstation.setVendor(workstation.getVendor());
				registeredWorkstation.setModel(workstation.getModel());

				registeredEmployee = employeeRepository.save(registeredEmployee);
				registeredWorkstation = workstationRepository.save(registeredWorkstation);

			} else if (assignedEmlpoyee != null && assignedEmlpoyee.getId() == registeredEmployee.getId()) { // workstation
																												// is
																												// assigned
																												// to it
																												// self

				registeredWorkstation.setVendor(workstation.getVendor());
				registeredWorkstation.setModel(workstation.getModel());

				registeredEmployee = employeeRepository.save(registeredEmployee);
				registeredWorkstation = workstationRepository.save(registeredWorkstation);

			} else {
				throw new WorkstationException("Workstation you tried to assign, already assigned to other Employee");
			}
		} else if (workstation != null && workstation.getId() == 0) { // workstation not exists...

			workstation.setFacilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber());

			workstation.setEmployee(registeredEmployee);

			workstation = workstationRepository.save(workstation);

			registeredEmployee.setWorkstation(workstation);

			registeredEmployee = employeeRepository.save(registeredEmployee);

		} else if (workstation == null) { // update Employee without workstation (setting workstation null)

			registeredWorkstation = registeredEmployee.getWorkstation();

			if (registeredWorkstation != null) { // if already employee has an assigned worksation uset it.
				registeredWorkstation.setEmployee(null);
				registeredWorkstation = workstationRepository.save(registeredWorkstation);
			}

			registeredEmployee.setWorkstation(null);

			registeredEmployee = employeeRepository.save(registeredEmployee);
		}

		return registeredEmployee;
	}

	@Override
	public Employee partialUpdate(long employeeId, Employee partialEmployee) {
		return null;
	}

	@Override
	public Employee delete(Employee employee) {
		Desk desk = employee.getDesk();
		if (desk != null) {
			desk.setEmployee(null);
			deskRepository.save(desk);
		}

		Workstation ws = employee.getWorkstation();
		if (ws != null) {
			ws.setEmployee(null);
			workstationRepository.save(ws);
		}

		employeeRepository.delete(employee);
		return employee;
	}

	@Override
	public List<Employee> retrieveByFirstName(String firstName) {
		return employeeRepository.findByFirstNameIgnoreCase(firstName);
	}

	@Override
	public List<Employee> retrieveByFirstNameBetter(String firstName) {
		return employeeRepository.customFindByFirstNameIgnoreCase(firstName);
	}

	@Override
	public List<Employee> retrieveByFirstNameAndLastName(String firstName, String lastName) {
		return employeeRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
	}

	@Override
	public List<Employee> retrieveByFirstNameAndLastNameBetter(String firstName, String lastName) {
		return employeeRepository.customFindByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
	}

	@Override
	public Employee retrieveByEmployeeNumber(String employeeNumber) {
		return employeeRepository.findByEmployeeNumber(employeeNumber);
	}

}
