package mx.com.truper.springboot.embedded.broker.service._config;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbededBrokerServiceConfig {

	//Define bean BrokerService tcp://localhost:61616
	@Bean
	public BrokerService broker() throws Exception {
		
		BrokerService broker = new BrokerService();
		broker.addConnector("tcp://localhost:61616");
		return broker;
	}
	
}