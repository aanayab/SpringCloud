package mx.com.truper.springboot.practica19.user.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserCreatedEvent {

	public int id;

	public String name;

	public String email;
}
