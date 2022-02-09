package mx.com.truper.springboot.practica26.zuul.employeemicroservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
	private String name;
	private String department;
	private int age;
}
