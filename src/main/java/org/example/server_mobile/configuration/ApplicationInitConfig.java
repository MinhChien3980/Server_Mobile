package org.example.server_mobile.configuration;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.server_mobile.entity.User;
import org.example.server_mobile.enums.Role;
import org.example.server_mobile.repository.UserRepo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepo userRepo) {
        return args -> {
            if (userRepo.findByEmail("admin").isEmpty()) {
                var roles = Set.of(Role.ADMIN.name());
                User admin = User.builder()
                        .email("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .role(roles)
                        .build();
                userRepo.save(admin);
                log.warn("Admin user created with password: admin, please change it!");
            };
        };
    }
}
