package mx.com.truper.springboot.practica8.acmehrsystem.employee.service;

import java.util.List;

import mx.com.truper.springboot.practica8.acmehrsystem.employee.model.Employee;

public interface EmployeeService {

	Employee retrieveById(Long id); // ya

	List<Employee> retrieveAll(); // ya

	Employee register(Employee employee); // ya

	Employee update(Employee employee); // ya

	Employee partialUpdate(long employeeId, Employee partialEmployee);

	Employee delete(Employee employee); // ya

	List<Employee> retrieveByFirstName(String firstName); // ya

	List<Employee> retrieveByFirstNameAndLastName(String firstName, String lastName); // ya

	Employee retrieveByEmployeeNumber(String employeeNumber); // ya

}
