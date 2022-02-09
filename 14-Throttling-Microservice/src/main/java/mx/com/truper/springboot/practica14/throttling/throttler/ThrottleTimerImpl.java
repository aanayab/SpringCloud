package mx.com.truper.springboot.practica14.throttling.throttler;

import java.util.Timer;
import java.util.TimerTask;

import lombok.RequiredArgsConstructor;

//Inyecta propiedades por constructor
@RequiredArgsConstructor
public class ThrottleTimerImpl implements Throttler {

	// Define propiedad correspondiente el delay en el cual el throttler (regulador)
	// va a regular la cantidad de llamadas por segundo.
	private final int throttlerPeriod;

	// Define la propiedad de bean CallsCount
	private final CallsCount callsCount;

	@Override
	public void start() {

		// Implementa
		new Timer(true).schedule(new TimerTask() {

			@Override
			public void run() {

				callsCount.resetAll();

			}

		}, 0, throttlerPeriod);
	}
}
