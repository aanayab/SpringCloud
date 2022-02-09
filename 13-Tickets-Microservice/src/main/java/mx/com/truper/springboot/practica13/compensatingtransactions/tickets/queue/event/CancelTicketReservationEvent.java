package mx.com.truper.springboot.practica13.compensatingtransactions.tickets.queue.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelTicketReservationEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cancelTicketReservationUUID;

	private int ticketOrderId;
}
