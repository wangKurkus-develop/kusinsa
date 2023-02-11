package com.kurkus.kusinsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
public class KusinsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KusinsaApplication.class, args);
    }

}
