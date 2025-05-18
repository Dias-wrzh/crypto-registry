package com.example.documentsstoragingsepolia.config;

import com.example.documentsstoragingsepolia.service.EncryptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Value("${encryption.key}")
    private String base64Key;

    @Bean
    public EncryptionService encryptionService() {
        return new EncryptionService(base64Key);
    }
}

