package com.stimulusfake.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화 - application.java에 있던 @EnableJpaAuditing 여기에서 사용
public class JpaConfig {
    
}
