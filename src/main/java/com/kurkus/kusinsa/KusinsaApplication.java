package com.kurkus.kusinsa;

import javax.persistence.EntityListeners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
public class KusinsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KusinsaApplication.class, args);
    }

}
