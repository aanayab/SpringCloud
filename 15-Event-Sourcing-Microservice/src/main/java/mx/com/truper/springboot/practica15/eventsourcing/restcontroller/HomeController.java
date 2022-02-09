package mx.com.truper.springboot.practica15.eventsourcing.restcontroller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	private static Map<String, String> apisList = new LinkedHashMap<>();

	private static int port;
	private static String context;
	private static String uri;

	public HomeController(
			@Value("${server.port:8080}") int port,
			@Value("${server.servlet.context-path:/event-sourcing-service}") String context) {
		this.port = port;
		this.context = context;

		this.uri = "http://localhost:" + port + context;
	}

	@PostConstruct
	public void setup() {
		apisList.put("GET apis-list", uri + "/");

		apisList.put("GET All Tenant Accont", uri + "/account/");
		apisList.put("GET Tenant Accont", uri + "/account/{tenant}");

		apisList.put("GET Create Tenant Accont", uri + "/account/{tenant}/create");

		apisList.put("GET Deposit Tenant Accont", uri + "/account/{tenant}/deposit/{amount}");
		apisList.put("GET Withdrawal Tenant Accont", uri + "/account/{tenant}/withdrawal/{amount}");

		apisList.put("GET Transfer Between Accounts", uri
				+ "/transfer/from/{tenantFrom}/to/{tenantTo}/amount/{amount}");

		apisList.put("GET Recover Transactions", uri + "/recover/transactions");

		apisList.put("GET Soft Reset Transactions", uri + "/reset/soft/transactions");
		apisList.put("GET Hard Reset Transactions", uri + "/reset/hard/transactions");
	}

	@GetMapping("/")
	public Map<?, ?> apis() {

		return apisList;
	}
}
