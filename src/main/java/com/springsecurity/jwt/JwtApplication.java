package com.springsecurity.jwt;

import com.springsecurity.jwt.entity.Role;
import com.springsecurity.jwt.entity.User;
import com.springsecurity.jwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_MANAGER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
//			userService.saveUser(new User(null, "tienminh", "password", "Tiến Minh",null));
//			userService.saveUser(new User(null, "tm0801", "password", "Minh Tiến", null));
//			userService.saveUser(new User(null, "hmt01", "password", "HMT", null));
//			userService.saveUser(new User(null, "math", "password", "SomeOne", null));
//
//			userService.addRoleToUser("math", "ROLE_USER");
//			userService.addRoleToUser("tienminh", "ROLE_MANAGER");
//			userService.addRoleToUser("tienminh", "ROLE_ADMIN");
//			userService.addRoleToUser("tm0801", "ROLE_MANAGER");
//			userService.addRoleToUser("hmt01", "ROLE_USER");
//		};
//	}

}
