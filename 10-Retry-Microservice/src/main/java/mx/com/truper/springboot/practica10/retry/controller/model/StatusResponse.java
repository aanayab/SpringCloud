package mx.com.truper.springboot.practica10.retry.controller.model;

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
