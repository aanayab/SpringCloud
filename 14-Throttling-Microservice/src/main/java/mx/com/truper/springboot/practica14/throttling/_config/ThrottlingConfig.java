package mx.com.truper.springboot.practica14.throttling._config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.truper.springboot.practica14.throttling.throttler.CallsCount;
import mx.com.truper.springboot.practica14.throttling.throttler.Tenant;
import mx.com.truper.springboot.practica14.throttling.throttler.ThrottleTimerImpl;
import mx.com.truper.springboot.practica14.throttling.throttler.Throttler;

// Define clase de configuracion
@Configuration
public class ThrottlingConfig {

	// Define bean CallsCount callsCounter
	@Bean
	public CallsCount callsCount() {
		return new CallsCount();
	}

	// Define bean Throttler throttler, llama a su metodo de inicializacion
	@Bean(initMethod = "start")
	public Throttler throttler() {
		return new ThrottleTimerImpl(1000, callsCount());
	}

	// Define bean Map<String, Tenant> tenantsMap
	@Bean
	public Map<String, Tenant> tenantsMap(){
		Map<String, Tenant> map = new HashMap<>();
		
		Tenant tenant1 = new Tenant("ivan", 1, callsCount());
		Tenant tenant2 = new Tenant("pepe", 1, callsCount());
		Tenant tenant3 = new Tenant("karla", 1, callsCount());
		
		map.put(tenant1.getName(), tenant1);
		map.put(tenant2.getName(), tenant2);
		map.put(tenant3.getName(), tenant3);
		
		return map;
	}

}
