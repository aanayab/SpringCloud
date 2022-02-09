package mx.com.truper.springboot.practica19.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private int id;

	private String name;
	
	private String email;
}
