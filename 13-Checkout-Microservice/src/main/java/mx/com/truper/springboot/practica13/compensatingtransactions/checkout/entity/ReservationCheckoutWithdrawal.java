package mx.com.truper.springboot.practica13.compensatingtransactions.checkout.entity;

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
public class ReservationCheckoutWithdrawal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String reservationCheckoutWithdrawalUUID;

	private String user;

	private int ticketOrderId;

	private double amount;

	private String creditCardNumber;

	private boolean reservationConfirmation;

	private boolean processedCheckout;

}