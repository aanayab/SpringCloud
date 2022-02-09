package mx.com.truper.springboot.practica11.queuebasedloadleveling.task.producer.impl;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.model.Message;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.ITaskConsumer;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.ITaskProducer;

@Slf4j
// Elimina abstract
public class SimpleTaskProducer implements ITaskProducer, Runnable {

	// Implementa
	private int messageCount;
	private ITaskConsumer taskConsumer;
	private int delay;

	public SimpleTaskProducer(int messageCount, ITaskConsumer taskConsumer, int delay) {
		this.messageCount = messageCount;
		this.taskConsumer = taskConsumer;
		this.delay = delay;
	}

	@Override
	public void run() {

		int count = this.messageCount;

		try {

			while (count > 0) {
				String message = "Message-" + count + " produced by " + Thread.currentThread().getName();

				Thread.sleep(delay);
				
				log.info("{}", message);
				
				this.produce(new Message(message));
				
				count--;
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

	}

	@Override
	public void produce(Message message) {
		taskConsumer.consume(message);
	}

}