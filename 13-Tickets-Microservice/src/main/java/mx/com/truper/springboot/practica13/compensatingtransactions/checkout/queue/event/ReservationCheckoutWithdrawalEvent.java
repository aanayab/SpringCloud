package mx.com.truper.springboot.practica13.compensatingtransactions.checkout.queue.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCheckoutWithdrawalEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reservationCheckoutWithdrawalUUID;

	private String user;

	private int ticketOrderId;

	private double amount;

	private String creditCardNumber;
}
