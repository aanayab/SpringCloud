package mx.com.truper.springboot.practica15.eventsourcing.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.truper.springboot.practica15.eventsourcing.events.DomainEvent;

@Component
public class DomainEventProcessor {

	@Autowired
	private JsonFileJournal processorJournal;

	public void process(DomainEvent domainEvent) {

		// Implementa
		domainEvent.process();
		
		processorJournal.write(domainEvent);
	}

	public void reset() {
		// Implementa
		processorJournal.reset();
	}

	public void recover() {

		// Implementa
		processorJournal.readJournal();
		
		while(true) {
			DomainEvent event = processorJournal.readNext();
			
			if(event == null) {
				break;
				
			} else {
				event.process();
			}
		}
	}
}
