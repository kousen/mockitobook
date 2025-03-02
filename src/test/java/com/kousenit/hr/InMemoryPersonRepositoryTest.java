package com.kousenit.hr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryPersonRepositoryTest {
    private InMemoryPersonRepository repository;
    private final Person person1 = new Person(1, "Grace", "Hopper", 
            LocalDate.of(1906, Month.DECEMBER, 9));
    private final Person person2 = new Person(2, "Ada", "Lovelace", 
            LocalDate.of(1815, Month.DECEMBER, 10));
    private final Person person3 = new Person(3, "Barbara", "Liskov", 
            LocalDate.of(1939, Month.NOVEMBER, 7));

    @BeforeEach
    void setUp() {
        repository = new InMemoryPersonRepository();
    }

    @Test
    void testSave() {
        Person savedPerson = repository.save(person1);
        assertEquals(person1, savedPerson);
        assertEquals(1, repository.count());
    }

    @Test
    void testFindById_whenExists() {
        repository.save(person1);
        repository.save(person2);
        
        Optional<Person> foundPerson = repository.findById(1);
        assertTrue(foundPerson.isPresent());
        assertEquals(person1, foundPerson.get());
    }

    @Test
    void testFindById_whenDoesNotExist() {
        repository.save(person1);
        
        Optional<Person> foundPerson = repository.findById(999);
        assertFalse(foundPerson.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(person1);
        repository.save(person2);
        repository.save(person3);
        
        List<Person> people = repository.findAll();
        assertEquals(3, people.size());
        assertTrue(people.contains(person1));
        assertTrue(people.contains(person2));
        assertTrue(people.contains(person3));
    }

    @Test
    void testCount() {
        assertEquals(0, repository.count());
        
        repository.save(person1);
        assertEquals(1, repository.count());
        
        repository.save(person2);
        assertEquals(2, repository.count());
    }

    @Test
    void testDelete() {
        repository.save(person1);
        repository.save(person2);
        assertEquals(2, repository.count());
        
        repository.delete(person1);
        assertEquals(1, repository.count());
        assertFalse(repository.findById(person1.getId()).isPresent());
        assertTrue(repository.findById(person2.getId()).isPresent());
    }

    @Test
    void testDelete_whenPersonNotInRepository() {
        repository.save(person1);
        repository.save(person2);
        assertEquals(2, repository.count());
        
        repository.delete(person3);
        assertEquals(2, repository.count());
    }

    @Test 
    void testThreadSafety() throws InterruptedException {
        // This test verifies the synchronized blocks work correctly
        final int personCount = 100;
        
        // Create and start multiple threads that save persons concurrently
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < personCount; i++) {
                repository.save(new Person(i, "Thread1", "Person" + i, LocalDate.now()));
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = personCount; i < personCount * 2; i++) {
                repository.save(new Person(i, "Thread2", "Person" + i, LocalDate.now()));
            }
        });
        
        thread1.start();
        thread2.start();
        
        // Wait for both threads to complete
        thread1.join();
        thread2.join();
        
        // Verify that all persons were saved correctly
        assertEquals(personCount * 2, repository.count());
    }
}