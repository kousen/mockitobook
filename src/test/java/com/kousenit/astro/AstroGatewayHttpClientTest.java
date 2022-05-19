package com.kousenit.astro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstroGatewayHttpClientTest {
    private final Gateway<AstroResponse> gateway = new AstroGatewayHttpClient();

    @Test
    void testDeserializeToRecords() {
        AstroResponse result = gateway.getResponse();
        result.getPeople().forEach(System.out::println);
        assertAll(
                () -> assertTrue(result.getNumber() >= 0),
                () -> assertEquals(result.getPeople().size(), result.getNumber())
        );
    }
}