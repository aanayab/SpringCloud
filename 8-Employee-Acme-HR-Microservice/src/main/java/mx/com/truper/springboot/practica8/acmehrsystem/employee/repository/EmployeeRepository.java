package mx.com.truper.springboot.practica8.acmehrsystem.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.truper.springboot.practica8.acmehrsystem.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findAllByOrderByIdAsc();

	List<Employee> findByFirstNameIgnoreCase(String firstName);

	List<Employee> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

	Employee findByEmployeeNumber(String employeeNumber);

}
