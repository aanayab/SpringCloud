package mx.com.truper.springboot.practica13.compensatingtransactions.tickets;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.com.truper.springboot.practica13.compensatingtransactions.tickets.appdemo.service.AppDemoService;

@RestController
@SpringBootApplication
public class TicketsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketsMicroserviceApplication.class, args);
	}
	
	@Autowired
	private Supplier<Integer> fixedRandomInteger;
	

	@PostMapping("/reserve-ticket")
	public String reserveTicket(@RequestBody ReservationRequestDTO reservation) {
		
		appDemoService.reserveTicket(
				reservation.getUser(), fixedRandomInteger.get(), reservation.getConcert(), 
				reservation.getPlace(), reservation.getFolioNumber(), 
				reservation.getTicketCost(), reservation.getServiceCost(), 
				reservation.getDiscount(), reservation.getTotal());
		
		return "ticket reservation placed";
	}
	
	@Bean
	public Supplier<Integer> fixedRandomInteger(){
		return () -> {
			return (int) (Math.random() * Math.pow(10, 6));
		};
	}
	

	@Autowired
	private AppDemoService appDemoService;

	@Bean
	@Profile("reserve-ticket")
	public CommandLineRunner setUpReserveTicket() {

		return (args) -> {

			// Implementa
			appDemoService.reserveTicket(
					"Ivan", 123, "DLD", "Palacio de los deportes", "ABC-123", 
					1800.00, 250.00, 0, 2050.00);

		};
	}

	@Bean
	@Profile("cancel-ticket")
	public CommandLineRunner cancelTicketReservation() {

		return (args) -> {

			// Implementa
			appDemoService.cancelTicketReservation(123);
		};
	}

}
