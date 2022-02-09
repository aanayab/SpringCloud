package mx.com.truper.springboot.practica26.zuul.apigateway.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.exception.HystrixTimeoutException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UpperCaseFallbackProvider implements FallbackProvider {

	private String responseBody = "{\"message\":\"Service Unavailable. Please try after sometime\"}";

	@Override
	public String getRoute() {
		// Service Id not route.
		// return "uppercase-microservice"; 
		return "*";
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, final Throwable cause) {

		System.out.println("fallo");

		log.info("[Fallback Provider] Exception {}: {}", cause.getClass().getSimpleName(), cause.getMessage());

		cause.printStackTrace();

		if (cause instanceof HystrixTimeoutException) {
			return new GatewayClientResponse(HttpStatus.GATEWAY_TIMEOUT, responseBody);
		} else {
			return new GatewayClientResponse(HttpStatus.SERVICE_UNAVAILABLE, responseBody);
		}

	}

}
