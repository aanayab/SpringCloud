package mx.com.truper.springboot.practica14.throttling.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class ValidateAuthenticationFilter implements Filter {

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		printHeaders(httpRequest);

		String consumerId = httpRequest.getHeader("X-Authenticated-Id");

		if (consumerId == null) {
			httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"X-Authenticated-Id not present");
			return;
		}

		log.info(
				"Logging Request  {} : {}", httpRequest.getMethod(),
				httpRequest.getRequestURI());

		chain.doFilter(request, response);

		log.info(
				"Logging Response :{}",
				httpResponse.getContentType());
	}

	private void printHeaders(HttpServletRequest httpRequest) {
		Enumeration<String> headerNames = httpRequest.getHeaderNames();

		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				System.out.println("Header: " + headerName + " = " + httpRequest.getHeader(headerName));
			}
		}
	}
}