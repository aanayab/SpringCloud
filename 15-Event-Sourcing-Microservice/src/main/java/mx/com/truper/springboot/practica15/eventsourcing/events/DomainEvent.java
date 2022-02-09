package mx.com.truper.springboot.practica15.eventsourcing.events;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class DomainEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private long sequenceId;
	private long createdTime;
	private String eventClassName;
	private boolean realTime = true;

	public DomainEvent(long sequenceId, long createdTime, String eventClassName) {
		this.sequenceId = sequenceId;
		this.createdTime = createdTime;
		this.eventClassName = eventClassName;
	}

	public abstract void process();

}
