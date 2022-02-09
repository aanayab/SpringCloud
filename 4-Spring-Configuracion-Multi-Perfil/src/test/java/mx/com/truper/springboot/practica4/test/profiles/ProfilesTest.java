package mx.com.truper.springboot.practica4.test.profiles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica4.profiles.bean.ConnectionDataBase;
import mx.com.truper.springboot.practica4.profiles.bean.api.DummyDataSource;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class) // Needed for JUnit 4
//@ActiveProfiles("production")
public class ProfilesTest {

	@Autowired // Just one bean definition, no ambiguity
	private ConnectionDataBase connectionDataBase;

	@Autowired // There are defined 4, ambiguity must happen
	private DummyDataSource dummyDataSource;

	@Value("${myapp.connection.url}")
	private String connectionURL;

	@Autowired
	private Environment env;

	@Before
	public void beforeClass() {
		Assert.assertNotNull(connectionDataBase);
		Assert.assertNotNull(dummyDataSource);
		Assert.assertNotNull(connectionURL);
	}

	@Test
	public void profilesTest() {
		log.info("profilesTest [START] ------------------------");

		String activeProfile = env.getActiveProfiles()[0];

		connectionDataBase.init();

		if (activeProfile.equals("dev")) {
			Assert.assertEquals("jdbc://dev-server:3306/myapp_db", connectionURL);
		}

		if (activeProfile.equals("qa")) {
			Assert.assertEquals("jdbc://qa-server:3306/myapp_db", connectionURL);
		}

		if (activeProfile.equals("staging")) {
			Assert.assertEquals("jdbc://staging-server:3306/myapp_db", connectionURL);
		}

		if (activeProfile.equals("production")) {
			Assert.assertEquals("jdbc://production-server:3306/myapp_db", connectionURL);
		}

		log.info("profilesTest [END] ------------------------");
	}
}
