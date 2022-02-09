package mx.com.truper.springboot.practica11.queuebasedloadleveling._config;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import mx.com.truper.springboot.practica11.queuebasedloadleveling._config.profile.QueuedBasedProfile;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.queue.MessageQueue;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.service.ITaskService;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.service.impl.TaskService;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.consumer.impl.MessageQueueBasedTaskConsumer;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.consumer.impl.SimpleTaskConsumer;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.producer.impl.MessageQueueBasedTaskProducer;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.producer.impl.SimpleTaskProducer;

@Configuration
@QueuedBasedProfile
public class QueuedBasedProfileConfiguration {

	// Implementa
	@Autowired
	private ObjectFactory<MessageQueueBasedTaskProducer> taskProducerObjectFactory;

	@Autowired
	@Qualifier("myExecutorService")
	private ExecutorService myExecutorService;

	// Implementa
	@Bean
	public MessageQueue messageQueue() {
		return new MessageQueue();
	}
	
	@Bean
	public ITaskService taskService() {
		return new TaskService(taskProducerObjectFactory, myExecutorService);
	}

	@Bean
	@Scope("prototype")
	public MessageQueueBasedTaskConsumer messageQueueTaskConsumer() {
		return new MessageQueueBasedTaskConsumer(messageQueue(), ApplicationConfig.CONSUMER_TASK_DELAY);
	}

	@Bean
	@Scope("prototype")
	public MessageQueueBasedTaskProducer simpleTaskProducer(@Value("${producer.message-count}") int messageCount) {
		return new MessageQueueBasedTaskProducer(messageCount, messageQueue(), ApplicationConfig.PRODUCER_TASK_DELAY);
	}
	
	@Bean
	public CommandLineRunner startConsumer(
			@Value("${consumer.threads}") int consumerThreads) {
		return (args) -> {
			for(int i = 0 ; i < consumerThreads; i++)
				myExecutorService.submit(messageQueueTaskConsumer());
		};
	}

}
