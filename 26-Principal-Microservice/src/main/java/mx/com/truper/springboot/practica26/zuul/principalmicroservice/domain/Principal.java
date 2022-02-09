package mx.com.truper.springboot.practica26.zuul.principalmicroservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Principal {
	private String name;
	private String principalOf;
}
