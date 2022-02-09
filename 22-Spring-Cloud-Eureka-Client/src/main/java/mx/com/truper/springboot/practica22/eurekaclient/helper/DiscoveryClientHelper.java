package mx.com.truper.springboot.practica22.eurekaclient.helper;

import java.net.URI;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryClientHelper {

	// Inyecta DiscoveryClient discoveryClient;
	@Autowired
	private DiscoveryClient discoveryClient; // Es el cliente eureka, para recuperar
											 // las instancias registradas en eureka de un servicio.

	private Random random = new Random();

	public URI getServiceURI(String appName) {
		// Implementa
		
		List<ServiceInstance> listInstances = getInstances(appName);
		
		if(listInstances != null && listInstances.size() > 0) {
			return listInstances.get(random.nextInt(10) % listInstances.size()).getUri();
		}
		
		return null;
	}

	public List<ServiceInstance> getInstances(String appName) {
		// Implementa obten instancias del DiscoveryClient
		return discoveryClient.getInstances(appName); // my-client-app
	}
}
