package com.kousenit.astro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.abort;

class AstroGatewayHttpClientTest {
    private final Gateway<AstroResponse> gateway = new AstroGatewayHttpClient();

    @Test
    void testDeserializeToRecords() {
        AstroResponse result;
        try {
            result = gateway.getResponse();
        } catch (RuntimeException e) {
            // The open-notify service is no longer reliable; skip rather than fail
            abort("open-notify service unavailable: " + e.getMessage());
            return;
        }
        result.getPeople().forEach(System.out::println);
        assertAll(
                () -> assertTrue(result.getNumber() >= 0),
                () -> assertEquals(result.getPeople().size(), result.getNumber())
        );
    }
}
