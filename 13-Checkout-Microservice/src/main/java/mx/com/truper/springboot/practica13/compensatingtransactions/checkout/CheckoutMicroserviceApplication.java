package mx.com.truper.springboot.practica13.compensatingtransactions.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import mx.com.truper.springboot.practica13.compensatingtransactions.checkout.appdemo.service.AppDemoService;


@SpringBootApplication
public class CheckoutMicroserviceApplication {

	public static void main(String[] args) {
		//System.setProperty("spring.profiles.active", "reserve-checkout-withdrawal");
		SpringApplication.run(CheckoutMicroserviceApplication.class, args);
	}

	// Inyecte AppDemoService appDemoService;
	@Autowired
	private AppDemoService appDemoService;

	// Define bean CommandLineRunner para perfil "reserve-checkout-withdrawal"
	@Bean
	@Profile("reserve-checkout-withdrawal")
	public CommandLineRunner startUpTest() {
		return (args) -> {
			appDemoService.reserveCheckoutWithdrawal("Ivan", 123, 2500.50, "1234-1234-4567-4567");
		};
	}

}
