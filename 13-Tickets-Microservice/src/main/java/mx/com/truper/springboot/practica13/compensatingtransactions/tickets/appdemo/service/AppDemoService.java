package mx.com.truper.springboot.practica13.compensatingtransactions.tickets.appdemo.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica13.compensatingtransactions.tickets.queue.TicketsMicroserviceQueues;
import mx.com.truper.springboot.practica13.compensatingtransactions.tickets.queue.event.CancelTicketReservationEvent;
import mx.com.truper.springboot.practica13.compensatingtransactions.tickets.queue.event.TicketReservationEvent;

@Slf4j
@Service
public class AppDemoService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Supplier<String> secureRandomUUID;

	public void reserveTicket(String user, int ticketOrderId, String concert, String place, String folioNumber,
			double ticketCost, double serviceCost, double discount, double total) {

		TicketReservationEvent event = new TicketReservationEvent(
				secureRandomUUID.get(), user, ticketOrderId, concert, place,
				folioNumber, ticketCost, serviceCost, discount, total);

		log.info("---");
		log.info("[Client app] sending 'ticket reservation' event for ticket order Id {} to queue {}.",
				event.getTicketOrderId(), TicketsMicroserviceQueues.TICKET_RESERVATION_QUEUE);
		log.info("---");
		jmsTemplate.convertAndSend(TicketsMicroserviceQueues.TICKET_RESERVATION_QUEUE, event);
	}

	public void cancelTicketReservation(int ticketOrderId) {

		CancelTicketReservationEvent event = new CancelTicketReservationEvent(
				secureRandomUUID.get(), ticketOrderId);

		log.info("---");
		log.info(
				"[Client app] sending 'cancel ticket reservation compensation' event for ticket order Id {} to queue {}.",
				event.getTicketOrderId(), TicketsMicroserviceQueues.CANCEL_TICKET_RESERVATION_QUEUE);
		log.info("---");
		jmsTemplate.convertAndSend(TicketsMicroserviceQueues.CANCEL_TICKET_RESERVATION_QUEUE, event);
	}
}
