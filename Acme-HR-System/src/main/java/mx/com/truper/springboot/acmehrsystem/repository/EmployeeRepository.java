package mx.com.truper.springboot.acmehrsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.truper.springboot.acmehrsystem.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findAllByOrderByIdAsc();

	@Query("SELECT distinct e FROM Employee e LEFT JOIN FETCH e.workstation LEFT JOIN FETCH e.desk ORDER BY e.id ASC")
	List<Employee> customFindAllByOrderByIdAsc();

	List<Employee> findByFirstNameIgnoreCase(String firstName);

	@Query("SELECT distinct e FROM Employee e LEFT JOIN FETCH e.workstation LEFT JOIN FETCH e.desk WHERE UPPER(e.firstName)=UPPER(:firstName)")
	List<Employee> customFindByFirstNameIgnoreCase(@Param("firstName") String firstName);

	List<Employee> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

	@Query("SELECT distinct e FROM Employee e LEFT JOIN FETCH e.workstation LEFT JOIN FETCH e.desk WHERE UPPER(e.firstName)=UPPER(:firstName) AND UPPER(e.lastName)=UPPER(:lastName)")
	List<Employee> customFindByFirstNameIgnoreCaseAndLastNameIgnoreCase(@Param("firstName") String firstName,
			@Param("lastName") String lastName);

	Employee findByEmployeeNumber(String employeeNumber);

}
