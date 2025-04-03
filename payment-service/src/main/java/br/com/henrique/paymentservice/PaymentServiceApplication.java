package br.com.henrique.paymentservice;

// import java.util.HashMap;
// import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
// import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

@SpringBootApplication
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);

		// Map<String, PasswordEncoder> encoders = new HashMap<>();
		// Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("",
		// 8, 185000,
		// SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		// encoders.put("pbkdf2", pbkdf2PasswordEncoder);
		// DelegatingPasswordEncoder passwordEncoder = new
		// DelegatingPasswordEncoder("pbkdf2", encoders);

		// passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);

		// String result = passwordEncoder.encode("123456");
		// System.out.println("minha senha: " + result);
	}

}
