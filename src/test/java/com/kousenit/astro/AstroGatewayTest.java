package com.kousenit.astro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstroGatewayTest {
    private final Gateway<AstroResponse> gateway = new AstroGateway();

    @Test
    void testDeserializeToRecords() {
        Result<AstroResponse> result = gateway.getResponse();
        System.out.println(result);
        assertTrue(result instanceof Success<AstroResponse>);
        AstroResponse data = ((Success<AstroResponse>) result).data();
        assertAll(
                () -> assertTrue(data.number() >= 0),
                () -> assertEquals(data.people().size(), data.number())
        );
    }
}