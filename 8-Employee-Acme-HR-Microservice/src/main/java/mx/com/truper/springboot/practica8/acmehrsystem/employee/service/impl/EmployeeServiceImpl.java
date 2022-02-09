package mx.com.truper.springboot.practica8.acmehrsystem.employee.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.truper.springboot.practica8.acmehrsystem.employee.exceptions.EmployeeNotFoundException;
import mx.com.truper.springboot.practica8.acmehrsystem.employee.model.Employee;
import mx.com.truper.springboot.practica8.acmehrsystem.employee.model.Workstation;
import mx.com.truper.springboot.practica8.acmehrsystem.employee.repository.EmployeeRepository;
import mx.com.truper.springboot.practica8.acmehrsystem.employee.service.EmployeeService;
import mx.com.truper.springboot.practica8.acmehrsystem.employee.workstation.exceptions.WorkstationException;
import mx.com.truper.springboot.practica8.acmehrsystem.employee.workstation.exceptions.WorkstationNotFoundException;
import mx.com.truper.springboot.practica8.acmehrsystem.employee.workstation.webclient.WorkstationWebClient;
import mx.com.truper.springboot.practica8.acmehrsystem.utils.HRUtils;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private WorkstationWebClient workstationWebClient;

	@Override
	public Employee retrieveById(Long id) {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
		
		Workstation ws = workstationWebClient.findByEmployeeId(employee.getId()).orElse(null);

		if (ws != null) {
			employee.setWorkstation(ws);
		}
		
		return employee;
	}

	@Override
	public List<Employee> retrieveAll() {
		List<Employee> listemployee = employeeRepository.findAllByOrderByIdAsc();

		for (Employee employee : listemployee) {
			Workstation ws = workstationWebClient.findByEmployeeId(employee.getId()).orElse(null);

			if (ws != null) {
				employee.setWorkstation(ws);
			}
		}

		return listemployee;
	}

	@Override
	public Employee register(Employee employee) {

		employee.setId(0);

		Workstation ws = employee.getWorkstation();

		if (ws != null && ws.getId() > 0) { // workstation exists...

			ws = workstationWebClient.findById(ws.getId())
					.orElseThrow(() -> new WorkstationNotFoundException("Workstation not found"));

			if (ws.getEmployeeId() != null) {
				Employee assignedEmlpoyee = employeeRepository.findById(ws.getEmployeeId()).orElse(null);
				if (assignedEmlpoyee == null) {
					employee.setWorkstation(ws);
				} else {
					throw new WorkstationException(
							"Workstation you tried to assign, already assigned to other Employee");
				}
			} else {
				employee.setWorkstation(ws);
			}

			employee.setEmployeeNumber(HRUtils.nextEmployeeNumber());
			employee = employeeRepository.save(employee);

			ws.setEmployeeId(employee.getId());

			ws = workstationWebClient.update(ws);

		} else if (ws != null && ws.getId() == 0) { // workstation not exists...

			employee.setEmployeeNumber(HRUtils.nextEmployeeNumber());

			employee = employeeRepository.save(employee);

			ws.setFacilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber());

			ws.setEmployeeId(employee.getId());

			ws = workstationWebClient.save(ws);

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

			registeredWorkstation = workstationWebClient.findById(workstation.getId())
					.orElseThrow(() -> new WorkstationNotFoundException("Workstation not found"));

			if (workstation.getEmployeeId() != null) {
				Employee assignedEmlpoyee = employeeRepository.findById(workstation.getEmployeeId()).get();
				if (assignedEmlpoyee == null) { // if workstation is not assigned

					registeredEmployee.setWorkstation(registeredWorkstation);
					registeredWorkstation.setEmployeeId(registeredEmployee.getId());

					// registeredWorkstation.setVendor(workstation.getVendor());
					// registeredWorkstation.setModel(workstation.getModel());

					registeredEmployee = employeeRepository.save(registeredEmployee);
					registeredWorkstation = workstationWebClient.update(registeredWorkstation);

				} else if (assignedEmlpoyee != null && assignedEmlpoyee.getId() == registeredEmployee.getId()) { // workstation
																													// is
																													// assigned
																													// to
																													// it
																													// self

					// registeredWorkstation.setVendor(workstation.getVendor());
					// registeredWorkstation.setModel(workstation.getModel());

					registeredEmployee = employeeRepository.save(registeredEmployee);
					registeredWorkstation = workstationWebClient.save(registeredWorkstation);

				} else {
					throw new WorkstationException(
							"Workstation you tried to assign, already assigned to other Employee");
				}
			} else {
				registeredEmployee.setWorkstation(registeredWorkstation);
				registeredWorkstation.setEmployeeId(registeredEmployee.getId());

				// registeredWorkstation.setVendor(workstation.getVendor());
				// registeredWorkstation.setModel(workstation.getModel());

				registeredEmployee = employeeRepository.save(registeredEmployee);
				registeredWorkstation = workstationWebClient.update(registeredWorkstation);
			}
		} else if (workstation != null && workstation.getId() == 0) { // workstation not exists...

			workstation.setFacilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber());

			workstation.setEmployeeId(registeredEmployee.getId());

			workstation = workstationWebClient.save(workstation);

			registeredEmployee.setWorkstation(workstation);

			registeredEmployee = employeeRepository.save(registeredEmployee);

		} else if (workstation == null) { // update Employee without workstation (setting workstation null)

			registeredWorkstation = registeredEmployee.getWorkstation();

			System.out.println("reigstered ws " + registeredWorkstation);

			if (registeredWorkstation != null) { // if already employee has an assigned worksation uset it.
				registeredWorkstation.setEmployeeId(null);
				registeredWorkstation = workstationWebClient.save(registeredWorkstation);
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

		Workstation ws = employee.getWorkstation();
		if (ws != null) {
			ws.setEmployeeId(null);
			workstationWebClient.save(ws);
		}

		employeeRepository.delete(employee);
		return employee;
	}

	@Override
	public List<Employee> retrieveByFirstName(String firstName) {
		return employeeRepository.findByFirstNameIgnoreCase(firstName);
	}

	@Override
	public List<Employee> retrieveByFirstNameAndLastName(String firstName, String lastName) {
		return employeeRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
	}

	@Override
	public Employee retrieveByEmployeeNumber(String employeeNumber) {
		return employeeRepository.findByEmployeeNumber(employeeNumber);
	}

}
