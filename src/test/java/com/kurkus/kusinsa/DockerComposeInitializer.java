//package com.kurkus.kusinsa;
//
//import java.io.File;
//import java.time.Duration;
//
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.testcontainers.containers.DockerComposeContainer;
//import org.testcontainers.containers.wait.strategy.Wait;
//
//public class DockerComposeInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//    @RegisterExtension
//    static DockerComposeContainer composeContainer =
//            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
//                    .withExposedService("redis-session", 6379,
//                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));
//
//    @Override
//    public void initialize(ConfigurableApplicationContext applicationContext) {
//        composeContainer.start();
//    }
//}
