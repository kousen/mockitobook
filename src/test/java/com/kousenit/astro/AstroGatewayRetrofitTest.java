package com.kousenit.astro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstroGatewayRetrofitTest {
    private final Gateway<AstroResponse> gateway = new AstroGatewayRetrofit();

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