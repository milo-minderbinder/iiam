package co.insecurity.iiam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.insecurity.iiam.persistence.repository.UserRepository;
import co.insecurity.iiam.persistence.service.UserPersistenceEventHandler;
import co.insecurity.iiam.persistence.service.UserPersistenceService;

@Configuration
public class ServiceConfig {

    @Bean
    public UserPersistenceService userPersistenceService(UserRepository userRepository) {
        return new UserPersistenceEventHandler(userRepository);
    }
}

