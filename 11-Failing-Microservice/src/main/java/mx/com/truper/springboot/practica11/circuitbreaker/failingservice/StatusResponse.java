package mx.com.truper.springboot.practica11.circuitbreaker.failingservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
	
	private int statusCode;
	private String status;
}
