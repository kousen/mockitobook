package com.kousenit.astro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AstroServiceTest {
    private final AstroResponse mockAstroResponse =
            new AstroResponse(7, "Success", Arrays.asList(
                    new Assignment("John Sheridan", "Babylon 5"),
                    new Assignment("Susan Ivanova", "Babylon 5"),
                    new Assignment("Beckett Mariner", "USS Cerritos"),
                    new Assignment("Brad Boimler", "USS Cerritos"),
                    new Assignment("Sam Rutherford", "USS Cerritos"),
                    new Assignment("D'Vana Tendi", "USS Cerritos"),
                    new Assignment("Ellen Ripley", "Nostromo")
            ));

    @Mock
    private Gateway<AstroResponse> gateway;

    @InjectMocks
    private AstroService service;

    @Test
    void testAstroData_usingInjectedMockGateway() {
        // Mock Gateway created and injected into AstroService using
        //    @Mock and @InjectMock annotations
        //
        // Set the expectations on the mock
        when(gateway.getResponse())
                .thenReturn(mockAstroResponse);

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Check the results from the method under test (AssertJ)
        assertThat(astroData)
                .containsEntry("Babylon 5", 2L)
                .containsEntry("Nostromo", 1L)
                .containsEntry("USS Cerritos", 4L);

        // Verify the stubbed method was called
        verify(gateway).getResponse();
        // verify(gateway, times(1)).getResponse();
    }

    // Unit test with injected mock Gateway (uses annotations)
    @Test
    void testAstroData_usingInjectedMockGatewayBDD() {
        // Mock Gateway created and injected into AstroService using
        //    @Mock and @InjectMock annotations
        //
        // Set the expectations on the mock
        given(gateway.getResponse())
                .willReturn(mockAstroResponse);

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Check the results from the method under test (AssertJ)
        assertThat(astroData)
                .containsEntry("Babylon 5", 2L)
                .containsEntry("Nostromo", 1L)
                .containsEntry("USS Cerritos", 4L);

        // Verify the stubbed method was called
        then(gateway).should().getResponse();
    }

    // Check network failure
    @Test
    void testAstroData_usingFailedGateway() {
        when(gateway.getResponse()).thenThrow(
                new RuntimeException(new IOException("Network problems")));

        // Check the exception (JUnit 5, which isn't bad)
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> service.getAstroData());
        Throwable cause = exception.getCause();
        assertAll(
                () -> assertEquals(IOException.class, cause.getClass()),
                () -> assertEquals("Network problems", cause.getMessage())
        );

        // Check the exception (AssertJ, which is way, way better :)
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> service.getAstroData())
                .withCauseExactlyInstanceOf(IOException.class)
                .withMessageContaining("Network problems");
    }

    // Check network failure
    @Test
    void testAstroData_usingFailedGatewayBDD() {
        // given:
        given(gateway.getResponse()).willThrow(
                new RuntimeException(new IOException("Network problems")));

        // when:
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.getAstroData());

        // then:
        Throwable cause = exception.getCause();
        assertAll(
                () -> assertEquals(IOException.class, cause.getClass()),
                () -> assertEquals("Network problems", cause.getMessage())
        );

        then(gateway).should().getResponse();
    }

    // Integration test -- no mocks
    @Test
    void testAstroData_usingRealGateway_withRetrofit() {
        // Create an instance of AstroService using the real Gateway
        service = new AstroService(new AstroGatewayRetrofit());

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Print the results and check that they are reasonable
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });
    }

    // Integration test -- no mocks
    @Test
    void testAstroData_usingRealGateway_withHttpClient() {
        // Create an instance of AstroService using the real Gateway
        service = new AstroService(new AstroGatewayHttpClient());

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Print the results and check that they are reasonable
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });
    }

    // Own mock class -- FakeGateway
    @Test
    void testAstroData_usingOwnMockGateway() {
        // Create the service using the mock Gateway
        service = new AstroService(new FakeGateway());

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Check the results from the method under test
        assertThat(astroData)
                .containsOnlyKeys("USS Voyager", "Jupiter 2",
                        "Babylon 5", "Rocinante", "Nostromo")
                .containsEntry("USS Voyager", 2L)
                .containsEntry("Jupiter 2", 1L)
                .containsEntry("Babylon 5", 1L)
                .containsEntry("Rocinante", 2L)
                .containsEntry("Nostromo", 1L);
    }

    // Unit test with mock Gateway using mock(Gateway.class)
    @SuppressWarnings("unchecked")
    @Test
    void testAstroData_usingMockGateway() {
        // 1. Create a mock Gateway
        Gateway<AstroResponse> mockGateway = mock(Gateway.class);

        // 2. Set expectations on the mock Gateway
        when(mockGateway.getResponse())
                .thenReturn(mockAstroResponse);

        // 3. Create an instance of AstroService using the mock Gateway
        AstroService service = new AstroService(mockGateway);

        // 4. Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // 5. Check the results from the method under test
        assertThat(astroData)
                .containsEntry("Babylon 5", 2L)
                .containsEntry("USS Cerritos", 4L)
                .containsEntry("Nostromo", 1L)
                .hasSize(3);

        // 6. Verify that the mock Gateway method was called
        verify(mockGateway).getResponse();
    }

}