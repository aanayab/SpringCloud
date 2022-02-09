package mx.com.truper.springboot.practica14.throttling.throttler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CallsCount {

	private Map<String, AtomicLong> tenantCallsCount = new ConcurrentHashMap<>();

	public void addTenant(String tenantName) {
		tenantCallsCount.putIfAbsent(tenantName, new AtomicLong(0));
	}

	public void incrementCount(String tenantName) {
		tenantCallsCount.get(tenantName).incrementAndGet();
	}

	public long getCount(String tenantName) {
		return tenantCallsCount.get(tenantName).get();
	}

	public void resetAll() {

		log.debug("Resetting the map.");

		for (Entry<String, AtomicLong> e : tenantCallsCount.entrySet()) {
			tenantCallsCount.put(e.getKey(), new AtomicLong(0));
		}
	}

	public void resetTenant(String tenantName) {

		log.debug("Resetting for tenant {}.", tenantName);

		tenantCallsCount.put(tenantName, new AtomicLong(0));
	}
}
