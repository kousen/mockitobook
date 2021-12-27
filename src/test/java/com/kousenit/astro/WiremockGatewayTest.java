package com.kousenit.astro;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WireMockTest
public class WiremockGatewayTest {

    @BeforeEach
    void setUp() {
        stubFor(get("/astros.json").willReturn(
                okJson("""
                        {
                            "people": [
                                {
                                    "craft": "Babylon 5",
                                    "name": "John Sheridan"
                                },
                                {
                                    "craft": "Babylon 5",
                                    "name": "Susan Ivanova"
                                },
                                {
                                    "craft": "Babylon 5",
                                    "name": "Michael Garibaldi"
                                }
                            ],
                            "message": "success",
                            "number": 3
                        }
                        """))
        );
    }

    @Test
    void testWithWiremock(WireMockRuntimeInfo info) {
        AstroGateway gateway = new AstroGateway(info.getHttpBaseUrl() + "/astros.json");
        Result<AstroResponse> result = gateway.getResponse();
        switch (result) {
            case Success<AstroResponse> astroSuccess -> {
                AstroResponse data = astroSuccess.data();
                System.out.println(result);
                assertAll(
                        () -> assertTrue(data.number() >= 0),
                        () -> assertEquals(data.people().size(), data.number())
                );
            }
            case Failure<AstroResponse> astroFailure -> assertNotNull(astroFailure.exception());
        }
    }
}
