package mx.com.truper.springboot.practica11.queuebasedloadleveling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica11.queuebasedloadleveling.service.ITaskService;

@Slf4j
// Define Rest Controller
@RestController
public class TaskController {

	// Inyecta ITaskService
	@Autowired
	private ITaskService taskService;

	@GetMapping("/{triggerThreads}")
	public String getResponse(@PathVariable int triggerThreads) {

		// Implementa
		long startTime = System.nanoTime();
		
		String response = taskService.triggerThreads(triggerThreads);
		
		long estimatedTime = System.nanoTime() - startTime;
		
		String elapsedTime = String.format("elapsed time %s sec.", ((double) estimatedTime/1_000_000_000));
		
		log.info("{}", elapsedTime);
		
		return String.format("%s %s", response, elapsedTime);
	}

}
