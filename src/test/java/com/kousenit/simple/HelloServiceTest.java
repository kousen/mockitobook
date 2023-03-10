package com.kousenit.simple;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloServiceTest {

    @Mock
    private TranslateService translateService;

    @InjectMocks
    private HelloService helloService;

    // Full end-to-end test
    @Test
    void testGreet() {
        HelloService hello = new HelloService(new TranslateService());
        String greeting = hello.greet("Dolly", "en");
        assertEquals("en translation: Hello, Dolly!", greeting);
    }

    @Test
    void testGreetWithFixedValues() {
        when(translateService.translate("Hello, Dolly!", "en"))
                .thenReturn("en translation: Hello, Dolly!");

        String greeting = helloService.greet("Dolly", "en");
        assertEquals("en translation: Hello, Dolly!", greeting);
    }

    // Mock the TranslateService for any string, but works only for "en"
    @Test
    void testGreetWithMock() {
        when(translateService.translate(anyString(), anyString()))
                .thenReturn("en translation: Hello, Dolly!");

        String greeting = helloService.greet("Dolly", "en");
        assertEquals("en translation: Hello, Dolly!", greeting);
    }

    // Mock the TranslateService for "en" only
    @Test
    void testGreetWithMockForEnglish() {
        when(translateService.translate(anyString(), eq("en")))
                .thenReturn("en translation: Hello, Dolly!");

        String greeting = helloService.greet("Dolly", "en");
        assertEquals("en translation: Hello, Dolly!", greeting);
    }

    // Mock the TranslateService for any language, using answer
    @Test
    void testGreetWithMockForEnglishWithAnswer() {
        when(translateService.translate(anyString(), anyString()))
                .thenAnswer(invocation -> invocation.getArgument(1) +
                        " translation: Hello, Dolly!");

        String greeting = helloService.greet("Dolly", "en");
        assertEquals("en translation: Hello, Dolly!", greeting);

        greeting = helloService.greet("Dolly", "fr");
        assertEquals("fr translation: Hello, Dolly!", greeting);
    }

}