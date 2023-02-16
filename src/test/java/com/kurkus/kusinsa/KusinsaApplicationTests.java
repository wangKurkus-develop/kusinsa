package com.kurkus.kusinsa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ContextConfiguration(initializers = {DockerComposeContainerInitializer.class})
class KusinsaApplicationTests {

    @Test
    void contextLoads() {
    }

}
