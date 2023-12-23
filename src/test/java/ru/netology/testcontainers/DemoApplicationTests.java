package ru.netology.testcontainers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {
    private static final String ENDPOINT = "/profile";

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    public static GenericContainer<?> devapp = new GenericContainer<>("devapp:1.0")
            .withExposedPorts(8080);

    @Container
    public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp:1.0")
            .withExposedPorts(8081);

    @Test
    void contextLoadsDev() {
        final String expected = "Current profile is dev";
        ResponseEntity<String> devResponse = restTemplate.getForEntity("http://127.0.0.1:"
                + devapp.getMappedPort(8080) + ENDPOINT, String.class);
        System.out.println(devResponse.getBody());
        Assertions.assertEquals(expected, devResponse.getBody());
    }

    @Test
    void contextLoadsProd() {
        final String expected = "Current profile is production";
        ResponseEntity<String> prodResponse = restTemplate.getForEntity("http://127.0.0.1:"
                + prodapp.getMappedPort(8081) + ENDPOINT, String.class);
        System.out.println(prodResponse.getBody());
        Assertions.assertEquals(expected, prodResponse.getBody());
    }
}
