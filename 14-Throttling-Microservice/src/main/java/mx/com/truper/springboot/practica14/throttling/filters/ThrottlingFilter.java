package mx.com.truper.springboot.practica14.throttling.filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica14.throttling.throttler.CallsCount;
import mx.com.truper.springboot.practica14.throttling.throttler.Tenant;
import mx.com.truper.springboot.practica14.throttling.throttler.exception.TenantException;
import mx.com.truper.springboot.practica14.throttling.throttler.exception.ThrottlerException;

@Slf4j
// Define bean component
@Component
@Order(2)
public class ThrottlingFilter implements Filter {

	// Inyecta propiedades requeridas
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private Map<String, Tenant> tenantsMap;
	
	@Autowired
	private CallsCount counter;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			doFilterInternal(request, response, chain);

		} catch (TenantException ex) { // x-authenticated-id: tania

			sendError(ex, HttpStatus.BAD_REQUEST, response);

		} catch (ThrottlerException ex) {
			
			sendError(ex, HttpStatus.TOO_MANY_REQUESTS, response);
		}
	}

	// Analiza metodo sendError.
	private void sendError(Exception ex, HttpStatus status, ServletResponse response)
			throws JsonProcessingException, IOException {

		Map<String, Object> map = new HashMap<>();

		map.put("status", status.value());
		map.put("exception", ex.getClass().getSimpleName());
		map.put("error", ex.getMessage());
		map.put("timestamp", new Date());

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(status.value());
		httpResponse.setContentType("application/json;charset=UTF-8");
		httpResponse.getOutputStream().print(convertObjectToJson(map));
	}

	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		return mapper.writeValueAsString(object);
	}

	private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String consumerId = httpRequest.getHeader("X-Authenticated-Id");

		// Implementa
		Tenant tenant = Optional.ofNullable(tenantsMap.get(consumerId))
								.orElseThrow(() -> new TenantException("Unknown Tenant"));
		
		if(counter.getCount(tenant.getName()) >= tenant.getAllowedCallsPerSecond() ) {
			throw new ThrottlerException("Max calls for API access of tenant: "+ tenant.getName() + " reached. "
					+ "Too many requests.");
		}
		
		counter.incrementCount(tenant.getName());
		
		if(tenant.getName().equals("ivan"))
			counter.resetTenant(tenant.getName());
		
		chain.doFilter(httpRequest, httpResponse);
	}
	
}