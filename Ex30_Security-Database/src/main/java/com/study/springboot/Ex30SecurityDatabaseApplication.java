package com.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@SpringBootApplication
public class Ex30SecurityDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex30SecurityDatabaseApplication.class, args);
//		String encoded=PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("1234");
//		System.out.println(encoded);

	}

}
