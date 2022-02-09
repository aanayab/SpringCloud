package mx.com.truper.springboot.practica8.acmehrsystem.employee.workstation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class WorkstationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8632362940032329348L;

	public WorkstationNotFoundException(String message) {
		super(message);
	}

}
