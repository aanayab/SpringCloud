package mx.com.truper.springboot.practica13.compensatingtransactions.tickets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
	
	private String user;

	private String concert;

	private String place;

	private String folioNumber;

	private double ticketCost;

	private double serviceCost;

	private double discount;

	private double total;
}
