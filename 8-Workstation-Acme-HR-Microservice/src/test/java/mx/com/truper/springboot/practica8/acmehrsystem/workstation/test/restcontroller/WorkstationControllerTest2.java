package mx.com.truper.springboot.practica8.acmehrsystem.workstation.test.restcontroller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import mx.com.truper.springboot.practica8.acmehrsystem.workstation.model.Workstation;
import reactor.core.publisher.Mono;

public class WorkstationControllerTest2 {

	public String ws_context_path_uri = "http://localhost:9092/acme-hr-workstation";

	public WebClient webClient = WebClient.create(ws_context_path_uri);

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
		Mono<Workstation> response = webClient.delete().uri("/v1/workstations/{workstationId}", workstationId)
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Workstation.class);

		Assert.assertNotNull(response);

		System.out.println("returned from delete workstation: " + response.block());
	}

	private void putWorkstation(long workstationId, Workstation workstation) {

		Mono<Workstation> response = webClient.put().uri("/v1/workstations/{workstationId}", workstationId)
				.accept(MediaType.APPLICATION_JSON).syncBody(workstation).retrieve().bodyToMono(Workstation.class);

		Assert.assertNotNull(response);

		System.out.println("returned from put workstation: " + response.block());
	}

	private void postWorkstation(Workstation ws) {

		Mono<Workstation> returnedWorkstation = webClient.post().uri("/v1/workstations")
				.contentType(MediaType.APPLICATION_JSON).syncBody(ws).retrieve().bodyToMono(Workstation.class);

		System.out.println("returned from post workstation: " + returnedWorkstation.block());

	}

	private List<Workstation> getWorkstations() {

		Mono<List<Workstation>> response = webClient.get().uri("/v1/workstations").accept(MediaType.APPLICATION_JSON)
				.retrieve().bodyToFlux(Workstation.class).collectList();

		Assert.assertNotNull(response);

		return response.block();
	}

	private Workstation getWorkstation(long workstationId) {

		Workstation returnedWorkstation = null;
		
		try {

			returnedWorkstation = webClient.get().uri("/v1/workstations/" + workstationId)
					.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Workstation.class).block();

			Assert.assertNotNull(returnedWorkstation);

		} catch (WebClientResponseException ex) {
			System.out.println(ex.getStatusText() + " " + ex.getRawStatusCode());
		}

		System.out.println("returned from get workstation: " + returnedWorkstation);

		return returnedWorkstation;
	}

}
