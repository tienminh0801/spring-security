package com.tienminh.springsecurity.config;

import com.tienminh.springsecurity.entity.User;

import com.tienminh.springsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseBootstrapConfig {

    @Bean
    public CommandLineRunner bootstrapDB(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                if (userRepo.count() == 0) {
                    userRepo.save(User
                                    .builder()
                                    .id(1L)
                                    .username("tienminh")
                                    .password(passwordEncoder.encode("izanami"))
                                    .build());
                }
            }
        };
    }

}
