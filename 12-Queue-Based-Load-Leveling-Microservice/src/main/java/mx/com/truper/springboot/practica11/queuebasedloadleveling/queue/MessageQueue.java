package mx.com.truper.springboot.practica11.queuebasedloadleveling.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.model.Message;

@Slf4j
public class MessageQueue {

	private BlockingQueue<Message> blockingQueue = new ArrayBlockingQueue<Message>(1024);

	public void submitMessage(Message message) {

		// implementa
		try {

			if (message != null)
				blockingQueue.add(message);

		} catch (Exception ex) {
			log.info(ex.getMessage());
		}
		log.info("elements in queue {}", blockingQueue.size());
	}

	public Message retrieveMessage() {

		// Implementa
		Message message = null;
		try {

			message = blockingQueue.poll();

		} catch (Exception ex) {
			log.info(ex.getMessage());
		}
		log.info("elements in queue {}", blockingQueue.size());

		return message;
	}
}