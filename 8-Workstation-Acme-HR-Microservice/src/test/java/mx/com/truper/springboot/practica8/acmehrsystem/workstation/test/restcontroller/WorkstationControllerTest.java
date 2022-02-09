package mx.com.truper.springboot.practica8.acmehrsystem.workstation.test.restcontroller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import mx.com.truper.springboot.practica8.acmehrsystem.workstation.model.Workstation;

public class WorkstationControllerTest {

	public String ws_context_path_uri = "http://localhost:9092/acme-hr-workstation";

	@Test
	public void getWorkstationsTest() {

		List<Workstation> workstations = getWorkstations();

		workstations.forEach(w -> {
			System.out.println(w);
		});

	}

	@Test
	public void getWorkstationTest() {

		Workstation workstation = getWorkstation(1L);

		System.out.println(workstation);
	}

	@Test
	public void postWorkstationTest() {
		List<Workstation> workstations = getWorkstations();

		workstations.forEach(ws -> {
			System.out.println(ws);
		});

		System.out.println("---");

		Workstation ws = Workstation.builder().vendor("Acer").model("travel mate").build();

		postWorkstation(ws);

		workstations = getWorkstations();

		workstations.forEach(w -> {
			System.out.println(w);
		});
	}

	@Test
	public void putWorkstationTest() {
		Workstation workstation = getWorkstation(1L);

		System.out.println(workstation);

		System.out.println("---");

		workstation.setId(1000L);
		workstation.setVendor(workstation.getVendor() + " modified");
		workstation.setModel(workstation.getModel() + " modified");
		workstation.setFacilitiesSerialNumber("xxx");
		workstation.setEmployeeId(workstation.getEmployeeId() != null && workstation.getEmployeeId() > 0 ? null : 1L);

		putWorkstation(1L, workstation);

		workstation = getWorkstation(1L);

		System.out.println(workstation);
	}

	@Test
	public void deleteWorkstationTest() {
		Workstation workstation = getWorkstation(1L);

		System.out.println(workstation);

		System.out.println("---");

		deleteWorkstation(1L);

		workstation = getWorkstation(1L);

		System.out.println(workstation);
	}

	private void deleteWorkstation(long workstationId) {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<Workstation> entity = null;

		ResponseEntity<Workstation> response = restTemplate.exchange(
				ws_context_path_uri + "/v1/workstations/" + workstationId, HttpMethod.DELETE, entity,
				Workstation.class);

		Assert.assertNotNull(response);

		System.out.println("returned from delete workstation: " + response.getBody());
	}

	private void putWorkstation(long workstationId, Workstation workstation) {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<Workstation> entity = new HttpEntity<>(workstation);

		ResponseEntity<Workstation> response = restTemplate.exchange(
				ws_context_path_uri + "/v1/workstations/" + workstationId, HttpMethod.PUT, entity, Workstation.class);

		Assert.assertNotNull(response);

		System.out.println("returned from put workstation: " + response.getBody());
	}

	private void postWorkstation(Workstation ws) {
		RestTemplate restTemplate = new RestTemplate();

		Workstation returnedWorkstation = restTemplate.postForObject(ws_context_path_uri + "/v1/workstations", ws,
				Workstation.class);

		System.out.println("returned from post workstation: " + returnedWorkstation);

	}

	private List<Workstation> getWorkstations() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Workstation>> response = restTemplate.exchange(ws_context_path_uri + "/v1/workstations",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Workstation>>() {
				});

		Assert.assertNotNull(response);

		return response.getBody();
	}

	private Workstation getWorkstation(long workstationId) {
		RestTemplate restTemplate = new RestTemplate();

		Workstation returnedWorkstation = null;
		
		/*
		 * 
    HttpClientErrorException – in case of HTTP status 4xx
    HttpServerErrorException – in case of HTTP status 5xx
    UnknownHttpStatusCodeException – in case of an unknown HTTP status

		 * */

		try {
			returnedWorkstation = restTemplate.getForObject(ws_context_path_uri + "/v1/workstations/" + workstationId, Workstation.class);
			
			Assert.assertNotNull(returnedWorkstation);
			
		} catch (RestClientResponseException ex) {
			System.out.println(ex.getStatusText() + " " + ex.getRawStatusCode());
		}

		System.out.println("returned from get workstation: " + returnedWorkstation);

		return returnedWorkstation;
	}

}
