package mx.com.truper.springboot.practica13.compensatingtransactions.checkout.appdemo.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica13.compensatingtransactions.checkout.queue.CheckoutMicroserviceQueues;
import mx.com.truper.springboot.practica13.compensatingtransactions.checkout.queue.event.ReservationCheckoutWithdrawalEvent;

@Slf4j
@Service
public class AppDemoService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Supplier<String> secureRandomUUID;

	public void reserveCheckoutWithdrawal(String user, int ticketOrderId, double amount, String creditCardNumber) {

		ReservationCheckoutWithdrawalEvent event = new ReservationCheckoutWithdrawalEvent(
				secureRandomUUID.get(), user, ticketOrderId, amount, creditCardNumber);

		log.info("---");
		log.info("[Client app] sending 'reservation checkout withdrawal' event for ticket order Id {} to queue {}.",
				event.getTicketOrderId(), CheckoutMicroserviceQueues.RESERVE_WITHDRAWAL_QUEUE);
		log.info("---");
		jmsTemplate.convertAndSend(CheckoutMicroserviceQueues.RESERVE_WITHDRAWAL_QUEUE, event);
	}
}
