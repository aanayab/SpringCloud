package mx.com.truper.springboot.practica26.zuul.employeemicroservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.truper.springboot.practica26.zuul.employeemicroservice.client.IRandomService;
import mx.com.truper.springboot.practica26.zuul.employeemicroservice.domain.Employee;

@Service
public class EmployeesService {

	private List<Employee> employees = Collections.synchronizedList(new ArrayList<>());

	@Autowired
	private IRandomService randomServiceClient;

	private void setUp() {
		employees.clear();
		for (int i = 0; i < 5; i++) {
			employees.add(Employee.builder()
					.name("Employee " + (i + 1))
					.age((int) randomServiceClient.next().get("random"))
					.department("TI")
					.build());
		}
	}

	public List<Employee> getEmployees() {
		this.setUp();
		return employees;
	}

}
