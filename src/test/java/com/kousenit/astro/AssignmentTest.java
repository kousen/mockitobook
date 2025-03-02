package com.kousenit.astro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String name = "Ellen Ripley";
        String craft = "Nostromo";
        
        // Act
        Assignment assignment = new Assignment(name, craft);
        
        // Assert
        assertEquals(name, assignment.getName());
        assertEquals(craft, assignment.getCraft());
    }
    
    @Test
    void testToString() {
        // Arrange
        String name = "Ellen Ripley";
        String craft = "Nostromo";
        Assignment assignment = new Assignment(name, craft);
        
        // Act
        String result = assignment.toString();
        
        // Assert
        assertTrue(result.contains(name));
        assertTrue(result.contains(craft));
    }
}