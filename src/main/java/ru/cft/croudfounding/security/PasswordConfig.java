package ru.cft.croudfounding.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder(
            @Value("${passwordEncoder.strength}") int encoderStrength) {
        return new BCryptPasswordEncoder(encoderStrength);
    }
}
