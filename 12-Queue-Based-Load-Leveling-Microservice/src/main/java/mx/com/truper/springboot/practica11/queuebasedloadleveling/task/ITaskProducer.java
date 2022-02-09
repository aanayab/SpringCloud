package mx.com.truper.springboot.practica11.queuebasedloadleveling.task;

import mx.com.truper.springboot.practica11.queuebasedloadleveling.model.Message;

public interface ITaskProducer {

	void produce(Message message);
}
