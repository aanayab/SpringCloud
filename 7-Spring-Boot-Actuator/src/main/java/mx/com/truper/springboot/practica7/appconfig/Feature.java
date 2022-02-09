package mx.com.truper.springboot.practica7.appconfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feature {
	private String name;
	private Boolean enabled;
}