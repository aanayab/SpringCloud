package mx.com.truper.springboot.practica8.acmehrsystem.employee.workstation.webclient;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import mx.com.truper.springboot.practica8.acmehrsystem.employee.model.Workstation;

@Component
public class WorkstationWebClient {

	private String ws_context_path_uri = "http://localhost:9092/acme-hr-workstation";

	private RestTemplate restTemplate = new RestTemplate();

	public Optional<Workstation> findById(long workstationId) {
		Workstation returnedWorkstation = null;

		try {
			returnedWorkstation = restTemplate.getForObject(ws_context_path_uri + "/v1/workstations/" + workstationId,
					Workstation.class);

		} catch (RestClientResponseException ex) {
			System.out.println(ex.getStatusText() + " " + ex.getRawStatusCode());
		}

		return Optional.ofNullable(returnedWorkstation);
	}

	public Optional<Workstation> findByEmployeeId(long employeeId) {
		Workstation returnedWorkstation = null;

		try {

			returnedWorkstation = restTemplate.getForObject(
					ws_context_path_uri + "/v1/workstations/search/findByEmployeeId?employeeId=" + employeeId,
					Workstation.class);

		} catch (RestClientResponseException ex) {
			System.out.println(ex.getStatusText() + " " + ex.getRawStatusCode());
		}

		return Optional.ofNullable(returnedWorkstation);
	}

	public Workstation save(Workstation ws) {

		Workstation returnedWorkstation = restTemplate.postForObject(ws_context_path_uri + "/v1/workstations", ws,
				Workstation.class);

		return returnedWorkstation;
	}

	public Workstation update(Workstation ws) {

		System.out.println("Sent ws : " + ws);

		restTemplate.put(ws_context_path_uri + "/v1/workstations/" + ws.getId(), ws);

		return findById(ws.getId()).get();
	}

}
