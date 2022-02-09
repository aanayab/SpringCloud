package mx.com.truper.springboot.practica13.compensatingtransactions.tickets.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TicketReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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