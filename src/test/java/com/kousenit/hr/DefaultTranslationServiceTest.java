package com.kousenit.hr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DefaultTranslationServiceTest {

    @InjectMocks
    private DefaultTranslationService service;

    @Test
    void testTranslateWithoutParameters() {
        // Arrange
        String text = "Hello, World!";
        
        // Act
        String result = service.translate(text);
        
        // Assert
        assertEquals(text, result, "Should return the original text unchanged");
    }
    
    @Test
    void testTranslateWithParameters() {
        // Arrange
        String text = "Hello, World!";
        String sourceLanguage = "en";
        String targetLanguage = "en";
        
        // Act
        String result = service.translate(text, sourceLanguage, targetLanguage);
        
        // Assert
        assertEquals(text, result, "Should return the original text unchanged");
    }
    
    @Test
    void testTranslateWithDifferentLanguages() {
        // Arrange
        String text = "Hello, World!";
        String sourceLanguage = "en";
        String targetLanguage = "fr";
        
        // Act
        String result = service.translate(text, sourceLanguage, targetLanguage);
        
        // Assert
        // The implementation always passes "en" to both source and target
        assertEquals(text, result, "Should return the original text unchanged regardless of requested languages");
    }
}