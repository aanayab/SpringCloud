package mx.com.truper.springboot.practica8.acmehrsystem.workstation.restcontroller;

import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Setter;
import lombok.SneakyThrows;
import mx.com.truper.springboot.practica8.acmehrsystem.model.ApiError;
import mx.com.truper.springboot.practica8.acmehrsystem.workstation.model.Workstation;
import mx.com.truper.springboot.practica8.acmehrsystem.workstation.service.WorkstationService;

@RestController
@RequestMapping("/v1/workstations")
public class WorkstationController {

	@Autowired
	private @Setter WorkstationService workstationService;

	@GetMapping
	public List<Workstation> getAllWorkstation() {
		return workstationService.retrieveAll();
	}

	@SneakyThrows
	@GetMapping("/{workstationId}")
	public Workstation getWorkstation(@PathVariable long workstationId) {
		// Thread.sleep(4000);
		return workstationService.retrieveById(workstationId);
	}

	@GetMapping("/search/findByEmployeeId")
	public Workstation getWorkstationByEmployeeId(@RequestParam(required = true) long employeeId) {
		return workstationService.retrieveByEmployeeId(employeeId);
	}

	@PostMapping
	public Workstation postWorkstation(@RequestBody Workstation workstation) {
		workstation.setId(0);

		return workstationService.register(workstation);
	}

	@PutMapping("/{workstationId}")
	public Workstation putWorkstation(@PathVariable long workstationId, @RequestBody Workstation workstation) {

		System.out.println("Received ws : " + workstation);

		workstation.setId(workstationId);

		return workstationService.update(workstation);
	}

	@DeleteMapping("/{workstationId}")
	public Workstation deleteWorkstation(@PathVariable long workstationId) {

		return workstationService.delete(workstationService.retrieveById(workstationId));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handle(ConstraintViolationException ex, WebRequest request) {

		ServletWebRequest servletWebRequest = (ServletWebRequest) request;

		ApiError apiError = ApiError.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value())
				.error(ex.getSQLException().getMessage()).message(ex.getMessage())
				.path(servletWebRequest.getRequest().getContextPath() + servletWebRequest.getRequest().getServletPath())
				.build();

		return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
	}

}