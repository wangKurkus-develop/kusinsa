package com.kurkus.kusinsa;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class DockerComposeContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final DockerComposeContainer dockerComposeContainer;
    private static final String REDIS = "redis";
    private static final int REDIS_PORT = 6379;
    private static final String MYSQL = "mysql";
    private static final int MYSQL_PORT = 3307;
    private Map<String, String> registry = new HashMap<>();


    static {
        dockerComposeContainer = new DockerComposeContainer(new File("docker-compose-test.yml"))
                .withExposedService(REDIS, REDIS_PORT, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
                .withExposedService(MYSQL, MYSQL_PORT, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(50)));

        dockerComposeContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        addOverrideProperties();

        TestPropertyValues.of(registry).applyTo(applicationContext.getEnvironment());
    }

    private void addOverrideProperties() {
        String redisHost = dockerComposeContainer.getServiceHost(REDIS, REDIS_PORT);
        changeProperties("spring.redis.session.host", redisHost);
        changeProperties("spring.redis.set.host", redisHost);
        changeProperties("spring.redis.cache.host", redisHost);


        String redisPort = String.valueOf(dockerComposeContainer.getServicePort(REDIS, REDIS_PORT));
        changeProperties("spring.redis.session.port", redisPort);
        changeProperties("spring.redis.set.port", redisPort);
        changeProperties("spring.redis.cache.port", redisPort);

        changeProperties("spring.datasource.url", "jdbc:mysql://" +
                dockerComposeContainer.getServiceHost(MYSQL, MYSQL_PORT) +
                ":" +
                dockerComposeContainer.getServicePort(MYSQL, MYSQL_PORT) +
                "/kusinsa_test?allowMultiQueries=true");
    }

    private void changeProperties(String key, String dockerComposeContainer) {
        registry.put(key, dockerComposeContainer);
    }



}
