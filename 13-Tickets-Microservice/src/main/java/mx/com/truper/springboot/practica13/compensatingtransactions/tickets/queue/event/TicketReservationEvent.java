package mx.com.truper.springboot.practica13.compensatingtransactions.tickets.queue.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketReservationEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ticketReservationUUID;

	private String user;

	private int ticketOrderId;

	private String concert;

	private String place;

	private String folioNumber;

	private double ticketCost;

	private double serviceCost;

	private double discount;

	private double total;
}
