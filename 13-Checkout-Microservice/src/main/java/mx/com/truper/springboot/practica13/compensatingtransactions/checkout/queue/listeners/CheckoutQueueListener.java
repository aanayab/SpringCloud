package mx.com.truper.springboot.practica13.compensatingtransactions.checkout.queue.listeners;

import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica13.compensatingtransactions.checkout.queue.CheckoutMicroserviceQueues;
import mx.com.truper.springboot.practica13.compensatingtransactions.checkout.queue.event.ReservationCheckoutWithdrawalEvent;
import mx.com.truper.springboot.practica13.compensatingtransactions.tickets.queue.producers.TicketsMicroserviceQueueProducer;

@Slf4j
// Defina Bean
@Component
public class CheckoutQueueListener {

	// Inyecte TicketsMicroserviceQueueProducer ticketsMicroserviceQueueProducer;
	@Autowired
	private TicketsMicroserviceQueueProducer ticketsMicroserviceQueueProducer;

	private boolean reservationCheckoutWithdrawal = false; 

	// Defina JMS Listener de queue
	// CheckoutMicroserviceQueues.RESERVE_WITHDRAWAL_QUEUE
	@JmsListener(destination = CheckoutMicroserviceQueues.RESERVE_WITHDRAWAL_QUEUE)
	public void applyReserveWithdrawal(@Payload ReservationCheckoutWithdrawalEvent reservationEvent,
			@Headers MessageHeaders headers, Message message, Session session) {

		// Implemente
		log.info("-----");
		log.info("[Checkout Microservice] applying 'reservation checkout withdrawal' event for ticket order id: {}",
				reservationEvent.getTicketOrderId());

		if (!successReservationCheckoutWithdrawal()) {
			log.info(
					"[Checkout Microservice] 'reservation checkout withdrawal' ticket order id: {} process failed "
					+ "(compensate triggering 'cancel ticket reservation' event)",
					reservationEvent.getTicketOrderId());
			
			ticketsMicroserviceQueueProducer.cancelTicketReservation(reservationEvent.getTicketOrderId());
			
		} else {
			log.info("[Checkout Microservice] 'reservation checkout withdrawal' success for ticket order id: {}",
					reservationEvent.getTicketOrderId());
		}

		log.info("-----");
	}

	private boolean successReservationCheckoutWithdrawal() {
		return reservationCheckoutWithdrawal;
	}

}