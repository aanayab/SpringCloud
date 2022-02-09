package mx.com.truper.springboot.practica11.queuebasedloadleveling.task.consumer.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.model.Message;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.queue.MessageQueue;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.ITaskConsumer;

@Slf4j
// elimina abstract
public class MessageQueueBasedTaskConsumer implements ITaskConsumer, Runnable {

	private MessageQueue messageQueue;
	private int delay;

	public MessageQueueBasedTaskConsumer(MessageQueue messageQueue, int delay) {
		this.messageQueue = messageQueue;
		this.delay = delay;
	}

	@SneakyThrows
	@Override
	public void consume(Message message) {
		Thread.sleep(delay);
		log.info("{} is consumed.", message.getMessage());
	}

	@Override
	public void run() {
		try {
			
			while(!Thread.currentThread().isInterrupted()) {
				Message message = messageQueue.retrieveMessage();
				
				if(message != null) {
					this.consume(message);
				} else {
					log.info("Consumer waiting for messages to serve...");
				}
				Thread.sleep(1000);
			}
			
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

	}

}
