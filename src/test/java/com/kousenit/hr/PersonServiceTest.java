package com.kousenit.hr;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @Captor
    private ArgumentCaptor<Person> personArg;

    private final List<Person> people = List.of(
            new Person(1, "Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9)),
            new Person(2, "Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10)),
            new Person(3, "Adele", "Goldberg", LocalDate.of(1945, Month.JULY, 7)),
            new Person(14, "Anita", "Borg", LocalDate.of(1949, Month.JANUARY, 17)),
            new Person(5, "Barbara", "Liskov", LocalDate.of(1939, Month.NOVEMBER, 7)));

    private final Map<Integer,Person> peopleMap = people.stream()
            .collect(Collectors.toMap(Person::getId, p -> p));

    // Can't be done because JUnit 5 extension is _strict_ and
    // many of these tests don't call repository.findAll()
//    @BeforeEach
//    void setUp() {
//        when(repository.findAll()).thenReturn(people);
//    }

    @Test
    public void findMaxId() {
        when(repository.findAll()).thenReturn(people);

        assertThat(service.getHighestId()).isEqualTo(14);

        verify(repository).findAll();
    }

    @Test
    public void findMaxId_BDD() {
        given(repository.findAll()).willReturn(people);

        assertThat(service.getHighestId()).isEqualTo(14);

        then(repository).should().findAll();
        // then(repository).should(times(1)).findAll();
    }

    @Test
    void defaultImplementations() {
        PersonRepository mockRepo = mock(PersonRepository.class);
        assertAll(
                () -> assertNull(mockRepo.save(any(Person.class))),
                () -> assertTrue(mockRepo.findById(anyInt()).isEmpty()),
                () -> assertTrue(mockRepo.findAll().isEmpty()),
                () -> assertEquals(0, mockRepo.count())
        );
    }

    @Test
    void getLastNames_usingMockMethod() {
        PersonRepository mockRepo = mock(PersonRepository.class);
        when(mockRepo.findAll()).thenReturn(people);

        PersonService personService = new PersonService(mockRepo);

        List<String> lastNames = personService.getLastNames();

        assertThat(lastNames)
                .contains("Borg", "Goldberg", "Hopper", "Liskov", "Lovelace");
        verify(mockRepo).findAll();
    }

    @Test
    public void getLastNames_usingAnnotations() {
        when(repository.findAll()).thenReturn(people);

        assertThat(service.getLastNames())
                .contains("Borg", "Goldberg", "Hopper", "Liskov", "Lovelace");

        verify(repository).findAll();
    }

    @Test
    public void getTotalPeople() {
        when(repository.count())
                .thenReturn((long) people.size());
        assertEquals(people.size(), service.getTotalPeople());
    }

    @Test
    public void saveAllPeople() {
        when(repository.save(any(Person.class)))
                .thenReturn(people.get(0),
                        people.get(1),
                        people.get(2),
                        people.get(3),
                        people.get(4));

        // test the service (which uses the mock)
        List<Integer> ids = service.savePeople(people.toArray(Person[]::new));
        List<Integer> actuals = new ArrayList<>(peopleMap.keySet());
        assertThat(ids).containsExactlyInAnyOrderElementsOf(actuals);

        // verify the interaction between the service and the mock
        verify(repository, times(people.size())).save(any(Person.class));
        verify(repository, never()).delete(any(Person.class));
    }

    @Test
    public void saveAllPeople_usingAnswer() {
        // Anonymous inner class
//        when(repository.save(any(Person.class)))
//                .thenAnswer(new Answer<Person>() {
//                    @Override
//                    public Person answer(InvocationOnMock invocation) throws Throwable {
//                        return invocation.getArgument(0);
//                    }
//                });

        // Lambda expression implementation of Answer<Person>
        when(repository.save(any(Person.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<Integer> ids = service.savePeople(people.toArray(Person[]::new));

        List<Integer> actuals = new ArrayList<>(peopleMap.keySet());
        assertThat(ids).containsExactlyInAnyOrderElementsOf(actuals);
    }

    @Test
    public void saveAllPeople_usingAdditionalAnswers() {
        when(repository.save(any(Person.class))).thenAnswer(returnsFirstArg());

        List<Integer> ids = service.savePeople(people.toArray(Person[]::new));

        List<Integer> actuals = new ArrayList<>(peopleMap.keySet());
        assertThat(ids).containsExactlyInAnyOrderElementsOf(actuals);
    }

    @Test
    public void savePersonThrowsException() {
        when(repository.save(any(Person.class)))
                .thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.savePeople(people.get(0)));
    }

    @Test
    public void createPerson() {
        when(repository.save(any(Person.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Person hopper = people.get(0);
        Person person = service.createPerson(
                hopper.getId(),
                hopper.getFirst(),
                hopper.getLast(),
                hopper.getDob());

        verify(repository).save(personArg.capture());

        assertEquals(personArg.getValue(), hopper);
        assertEquals(hopper, person);
    }

    @Test
    public void createPersonUsingDateString() {
        Person hopper = people.get(0);

        when(repository.save(hopper)).thenReturn(hopper); // generalize this with an answer
        Person actual = service.createPerson(1, "Grace", "Hopper", "1906-12-09");

        verify(repository).save(personArg.capture());
        assertThat(personArg.getValue()).isEqualTo(hopper);
        assertThat(actual).isEqualTo(hopper);
    }

    @Test
    public void deleteAll() {
        when(repository.findAll()).thenReturn(people);

        doNothing().when(repository)
                .delete(any(Person.class));

        service.deleteAll();

        verify(repository, times(5)).delete(any(Person.class));
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void deleteAllWithNulls() {
        when(repository.findAll()).thenReturn(
                Arrays.asList((Person) null));

        //when(repository.delete(null)).thenThrow(RuntimeException.class);
        doThrow(RuntimeException.class).when(repository)
                .delete(null);

        assertThrows(RuntimeException.class, () -> service.deleteAll());

        verify(repository).delete(null);
    }

    @Test @Disabled("Do not use argThat with integers")
    public void findByIdThatDoesNotExist_argThat() {
        // More specific, custom matcher
        when(repository.findById(argThat(id -> id > 14)))
                .thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(999);
        assertTrue(personList.isEmpty());

        verify(repository).findById(anyInt());
    }


    @Test
    public void findByIdsThatDoNotExist_intThat() {
        // More specific, custom matcher
        when(repository.findById(intThat(id -> id > 14)))
                .thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(15, 42, 78, 999);
        assertTrue(personList.isEmpty());

        verify(repository, times(4)).findById(anyInt());
    }

    @Test
    public void findByIdsThatDoExist() {
        int maxId = peopleMap.keySet().stream()
                .max(Integer::compareTo).orElse(0);
        when(repository.findById(intThat(id -> id <= maxId)))
                .thenAnswer(invocation -> {
                    int id = invocation.getArgument(0);
                    return Optional.ofNullable(peopleMap.get(id));
                });

        List<Person> personList = service.findByIds(1, 3, 5, maxId);
        assertThat(personList).containsExactlyElementsOf(
                List.of(peopleMap.get(1), peopleMap.get(3),
                        peopleMap.get(5), peopleMap.get(maxId)));
    }

    @Test
    void findByIds_explicitWhens() {
        when(repository.findById(0))
                .thenReturn(Optional.of(people.get(0)));
        when(repository.findById(1))
                .thenReturn(Optional.of(people.get(1)));
        when(repository.findById(2))
                .thenReturn(Optional.of(people.get(2)));
        when(repository.findById(3))
                .thenReturn(Optional.of(people.get(3)));
        when(repository.findById(4))
                .thenReturn(Optional.of(people.get(4)));
        when(repository.findById(5))
                .thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(0, 1, 2, 3, 4, 5);
        assertThat(personList).containsExactlyElementsOf(people);
    }

    @SuppressWarnings("unchecked")
    @Test
    void findByIds_thenReturnWithMultipleArgs() {
        when(repository.findById(anyInt())).thenReturn(
                Optional.of(people.get(0)),
                Optional.of(people.get(1)),
                Optional.of(people.get(2)),
                Optional.of(people.get(3)),
                Optional.of(people.get(4)),
                Optional.empty());

        List<Person> personList = service.findByIds(0, 1, 2, 3, 4, 5);
        assertThat(personList).isEqualTo(people);
    }

    @Test
    void testInMemoryPersonRepository() {
        PersonRepository personRepo = new InMemoryPersonRepository();
        PersonService personService = new PersonService(personRepo);

        personService.savePeople(people.toArray(Person[]::new));
        assertThat(personRepo.findAll()).isEqualTo(people);
    }

    @Test
    void testMockOfFinalMethod() {
        PersonRepository personRepo = mock(InMemoryPersonRepository.class);

        // Set expectations on the mock
        when(personRepo.save(any(Person.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Inject the mock into the class under test
        PersonService personService = new PersonService(personRepo);

        // Test the savePeople method
        List<Integer> ids = personService.savePeople(people.toArray(Person[]::new));
        assertThat(ids).containsExactly(1, 2, 3, 14, 5);

        // Verify the save method in the mock was called as expected
        verify(personRepo, times(5)).save(any(Person.class));
    }

    @Test
    void spyOnRepository() {
        PersonRepository personRepo = spy(new InMemoryPersonRepository());
        PersonService personService = new PersonService(personRepo);

        personService.savePeople(people.toArray(Person[]::new));
        assertThat(personRepo.findAll()).isEqualTo(people);

        verify(personRepo, times(people.size())).save(any(Person.class));
    }

    @Test
    void asyncSavePerson() {
        Person firstPerson = people.get(0);
        long delay = 100;

        // Call the method under test
        service.asyncSavePerson(firstPerson, delay);

        // Verify that the save method was called on the repo with the person
        verify(repository, timeout(2 * delay)).save(firstPerson);
    }

}