package mx.com.truper.springboot.practica18.gateway.aggregates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesktopProductAggregate implements ProductAggregate {

	private String price;
	private String imagePath;

}