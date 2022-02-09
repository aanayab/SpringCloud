package mx.com.truper.springboot.practica13.compensatingtransactions.tickets._config;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.SneakyThrows;
import mx.com.truper.springboot.practica13.compensatingtransactions.checkout.queue.CheckoutMicroserviceQueues;

@Configuration
@ComponentScan(basePackageClasses = CheckoutMicroserviceQueues.class)
public class TicketsMicroserviceApplicationConfig {

	@Bean
	public Supplier<String> secureRandomUUID() {

		return new Supplier<String>() {

			private final char[] hexArray = "0123456789ABCDEF".toCharArray();

			@SneakyThrows
			@Override
			public String get() {
				MessageDigest salt = MessageDigest.getInstance("SHA-256");
				salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
				String digest = bytesToHex(salt.digest());

				return digest;
			}

			public String bytesToHex(byte[] bytes) {
				char[] hexChars = new char[bytes.length * 2];
				for (int j = 0; j < bytes.length; j++) {
					int v = bytes[j] & 0xFF;
					hexChars[j * 2] = hexArray[v >>> 4];
					hexChars[j * 2 + 1] = hexArray[v & 0x0F];
				}
				return new String(hexChars);
			}

		};
	}

}