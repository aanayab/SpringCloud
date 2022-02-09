package mx.com.truper.springboot.practica26.zuul.principalmicroservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.truper.springboot.practica26.zuul.principalmicroservice.client.IUppercaseService;
import mx.com.truper.springboot.practica26.zuul.principalmicroservice.domain.Principal;

@Service
public class PrincipalService {

	private List<Principal> principal = Collections.synchronizedList(new ArrayList<>());

	private List<String> departments = Arrays.asList("ti", "hr", "sales", "e-commerce");

	@Autowired
	private IUppercaseService uppercaseServiceClient;

	private void setUp() {
		principal.clear();
		for (int i = 0; i < departments.size(); i++) {
			principal.add(Principal.builder().name("Principal " + (i + 1))
					.principalOf((String) uppercaseServiceClient.toUppercase(departments.get(i)).get("uppercase"))
					.build());
		}
		Collections.shuffle(departments);
	}

	public List<Principal> getPrincipals() {
		this.setUp();
		return principal;
	}

}
