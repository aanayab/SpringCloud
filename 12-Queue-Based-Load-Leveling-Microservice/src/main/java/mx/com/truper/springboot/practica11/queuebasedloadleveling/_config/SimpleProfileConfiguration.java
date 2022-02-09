package mx.com.truper.springboot.practica11.queuebasedloadleveling._config;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import mx.com.truper.springboot.practica11.queuebasedloadleveling._config.profile.SimpleProfile;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.service.ITaskService;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.service.impl.TaskService;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.consumer.impl.SimpleTaskConsumer;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.producer.impl.SimpleTaskProducer;

@Configuration
@SimpleProfile
public class SimpleProfileConfiguration {

	@Autowired
	private ObjectFactory<SimpleTaskProducer> taskProducerObjectFactory;

	@Autowired
	@Qualifier("myExecutorService")
	private ExecutorService myExecutorService;

	// Implementa
	@Bean
	public ITaskService taskService() {
		return new TaskService(taskProducerObjectFactory, myExecutorService);
	}

	@Bean
	public SimpleTaskConsumer simpleTaskConsumer() {
		return new SimpleTaskConsumer(ApplicationConfig.CONSUMER_TASK_DELAY);
	}

	@Bean
	@Scope("prototype")
	public SimpleTaskProducer simpleTaskProducer(@Value("${producer.message-count}") int messageCount) {
		return new SimpleTaskProducer(messageCount, simpleTaskConsumer(), ApplicationConfig.PRODUCER_TASK_DELAY);
	}

}
