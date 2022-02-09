package mx.com.truper.springboot.practica11.queuebasedloadleveling.task.producer.impl;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.model.Message;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.queue.MessageQueue;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.ITaskProducer;

@Slf4j
// Elimina abstract
public class MessageQueueBasedTaskProducer implements ITaskProducer, Runnable {

	private int messageCount;
	private MessageQueue messageQueue;
	private int delay;

	public MessageQueueBasedTaskProducer(int messageCount, MessageQueue messageQueue, int delay) {
		this.messageCount = messageCount;
		this.messageQueue = messageQueue;
		this.delay = delay;
	}

	// Implementa
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
		try {
			this.messageQueue.submitMessage(message);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

}