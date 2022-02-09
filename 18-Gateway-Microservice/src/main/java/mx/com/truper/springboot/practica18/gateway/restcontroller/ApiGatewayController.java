package mx.com.truper.springboot.practica18.gateway.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mx.com.truper.springboot.practica18.gateway.aggregates.DesktopProductAggregate;
import mx.com.truper.springboot.practica18.gateway.aggregates.MobileProductAggregate;
import mx.com.truper.springboot.practica18.gateway.aggregates.ProductAggregate;
import mx.com.truper.springboot.practica18.gateway.client.ImageMicroserviceClient;
import mx.com.truper.springboot.practica18.gateway.client.PriceMicroserviceClient;

// Define RestController
@RestController
@RequiredArgsConstructor
public class ApiGatewayController {

	// Implementa
	private final ImageMicroserviceClient imageClient;

	private final PriceMicroserviceClient priceClient;

	@GetMapping("/desktop")
	public DesktopProductAggregate getProductDesktop() {
		return new DesktopProductAggregate(priceClient.getPrice(), imageClient.getImagePath());
	}
	
	@GetMapping("/mobile")
	public MobileProductAggregate getProductMobile(){
		return new MobileProductAggregate(priceClient.getPrice());
	}
	
	@GetMapping("/product")
	public ResponseEntity<ProductAggregate> getProduct(@RequestHeader("User-Agent") String userAgent) {
		return !userAgent.isEmpty() && userAgent.contains("mobile") ?
				ResponseEntity.ok(new MobileProductAggregate(priceClient.getPrice())): 
				ResponseEntity.ok(new DesktopProductAggregate(priceClient.getPrice(), imageClient.getImagePath()));
	}


}
