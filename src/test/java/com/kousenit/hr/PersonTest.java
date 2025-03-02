package com.kousenit.hr;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    private final Person person1 = new Person(1, "Grace", "Hopper", 
            LocalDate.of(1906, Month.DECEMBER, 9));
    private final Person person1Copy = new Person(1, "Grace", "Hopper", 
            LocalDate.of(1906, Month.DECEMBER, 9));
    private final Person person2 = new Person(2, "Ada", "Lovelace", 
            LocalDate.of(1815, Month.DECEMBER, 10));
    private final Person personNullId = new Person(null, "Grace", "Hopper", 
            LocalDate.of(1906, Month.DECEMBER, 9));
    private final Person personNullFirst = new Person(1, null, "Hopper", 
            LocalDate.of(1906, Month.DECEMBER, 9));
    private final Person personNullLast = new Person(1, "Grace", null, 
            LocalDate.of(1906, Month.DECEMBER, 9));
    private final Person personNullDob = new Person(1, "Grace", "Hopper", null);

    @Test
    void testEqualsWithSameReference() {
        // Test case where object equals itself (same reference)
        assertTrue(person1.equals(person1));
    }

    @Test
    void testEqualsWithNull() {
        // Test case with null
        assertFalse(person1.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Test case with different class
        assertFalse(person1.equals("Not a Person"));
    }

    @Test
    void testEqualsWithEqualObjects() {
        // Test case with equal objects
        assertTrue(person1.equals(person1Copy));
        assertTrue(person1Copy.equals(person1));
    }

    @Test
    void testEqualsWithDifferentObjects() {
        // Test case with different objects
        assertFalse(person1.equals(person2));
        assertFalse(person2.equals(person1));
    }

    @Test
    void testEqualsWithNullFields() {
        // Test cases with null fields
        assertFalse(person1.equals(personNullId));
        assertFalse(personNullId.equals(person1));
        
        assertFalse(person1.equals(personNullFirst));
        assertFalse(personNullFirst.equals(person1));
        
        assertFalse(person1.equals(personNullLast));
        assertFalse(personNullLast.equals(person1));
        
        assertFalse(person1.equals(personNullDob));
        assertFalse(personNullDob.equals(person1));
    }

    @Test
    void testHashCode() {
        // Equal objects should have the same hash code
        assertEquals(person1.hashCode(), person1Copy.hashCode());
        
        // Different objects should have different hash codes
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    void testGetters() {
        assertEquals(1, person1.getId());
        assertEquals("Grace", person1.getFirst());
        assertEquals("Hopper", person1.getLast());
        assertEquals(LocalDate.of(1906, Month.DECEMBER, 9), person1.getDob());
    }

    @Test
    void testToString() {
        String expected = "Person[id=1, first=Grace, last=Hopper, dob=1906-12-09]";
        assertEquals(expected, person1.toString());
    }
}