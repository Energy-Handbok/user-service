package com.khaphp.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SecurityScheme(
//		name = "EnergyHandbook",
//		in = SecuritySchemeIn.HEADER,
//		type = SecuritySchemeType.HTTP,
//		scheme = "Bearer",
//		bearerFormat = "JWT"
//)
//@SecurityScheme(name = "EnergyHandbook", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
