package mx.com.truper.springboot.practica11.queuebasedloadleveling.task.consumer.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.model.Message;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.task.ITaskConsumer;

@Slf4j
// elimina abstract
public class SimpleTaskConsumer implements ITaskConsumer {

	// Implementa
	private int delay;

	public SimpleTaskConsumer(int delay) {
		this.delay = delay;
	}

	@SneakyThrows
	@Override
	public void consume(Message message) {
		Thread.sleep(delay);
		log.info("{} is consumed.", message.getMessage());
	}

}
