package mx.com.truper.springboot.practica4.profiles.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica4.profiles.bean.api.DummyDataSource;

@Slf4j
@Component
// Bean in default profile
public class ConnectionDataBase {

	// Haz de cuenta que es un DataSource
	private DummyDataSource datasource;

	// define propiedad myapp.connection.url
	@Value("${myapp.connection.url:No connection url}")
	private String connectionURL;

	// NO es necesario @Autowired sobre constructor cuando solo tienes 1 constructor.
	public ConnectionDataBase(DummyDataSource ds) {
		this.datasource = ds;
	}

	public void init() {
		this.datasource.connect();
		log.info("Connected to: " + connectionURL);
	}
}
