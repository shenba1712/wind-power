package com.baywa.power.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// This configuration is added to enable @CreatedDate and @LastModifiedDate annotations.

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("com.baywa.power.persistence")
public class JPAConfiguration {
}
