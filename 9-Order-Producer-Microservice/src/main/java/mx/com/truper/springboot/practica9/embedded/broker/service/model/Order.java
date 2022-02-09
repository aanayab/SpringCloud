package mx.com.truper.springboot.practica9.embedded.broker.service.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

	private static final long serialVersionUID = -5696223031485550323L;

	private int id;
	private String content;
	private Date timestamp;
}