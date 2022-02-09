package mx.com.truper.springboot.practica11.queuebasedloadleveling.service.impl;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.ObjectFactory;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.service.ITaskService;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.ITaskProducer;

@Slf4j
// elimina abstract
public class TaskService implements ITaskService {

	private ObjectFactory<? extends ITaskProducer> taskProducerObjectFactory;
	private ExecutorService executorService;

	public TaskService(ObjectFactory<? extends ITaskProducer> taskProducerObjectFactory, ExecutorService executorService) {
		this.taskProducerObjectFactory = taskProducerObjectFactory;
		this.executorService = executorService;
	}

	// Implementa
	@Override
	public String triggerThreads(int triggeredThreads) {

		int triggered = 0;

		for (int i = 0; i < triggeredThreads; i++) {

			ITaskProducer producer = taskProducerObjectFactory.getObject();

			executorService.submit((Runnable) producer);

			triggered++;

			log.info("Task {} triggered!", (i + 1));
		}

		return String.format("%d tasks triggered", triggered);
	}

}
