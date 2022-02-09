package mx.com.truper.springboot.practica14.throttling.throttler;

import java.security.InvalidParameterException;

public class Tenant {

	private String name;

	private int allowedCallsPerSecond;

	public Tenant(String name, int allowedCallsPerSecond, CallsCount callsCount) {

		if (allowedCallsPerSecond < 0) {
			throw new InvalidParameterException("Number of calls less than 0 not allowed");
		}

		this.name = name;
		this.allowedCallsPerSecond = allowedCallsPerSecond;

		callsCount.addTenant(name);
	}

	public String getName() {
		return name;
	}

	public int getAllowedCallsPerSecond() {
		return allowedCallsPerSecond;
	}
}
