package mx.com.truper.springboot.practica11.queuebasedloadleveling.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	private @Setter(AccessLevel.NONE) String message;

}