package com.kousenit.astro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstroGatewayTest {
    private final Gateway<AstroResponse> gateway = new AstroGateway();

    @Test
    void testDeserializeToRecords() {
        Response<AstroResponse> response = gateway.getResponse();
        System.out.println(response);
        assertTrue(response instanceof Success<AstroResponse>);
        AstroResponse data = ((Success<AstroResponse>) response).data();
        assertAll(
                () -> assertTrue(data.number() >= 0),
                () -> assertEquals(data.people().size(), data.number())
        );
    }
}