package com.kousenit.hr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceBDDTest {
    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private final List<Person> people = Arrays.asList(
            new Person(1, "Grace", "Hopper",
                    LocalDate.of(1906, Month.DECEMBER, 9)),
            new Person(2, "Ada", "Lovelace",
                    LocalDate.of(1815, Month.DECEMBER, 10)),
            new Person(3, "Adele", "Goldberg",
                    LocalDate.of(1945, Month.JULY, 7)),
            new Person(14, "Anita", "Borg",
                    LocalDate.of(1949, Month.JANUARY, 17)),
            new Person(5, "Barbara", "Liskov",
                    LocalDate.of(1939, Month.NOVEMBER, 7)));

    @Test
    public void findMaxId() {
        given(repository.findAll()).willReturn(people);
        assertThat(service.getHighestId()).isEqualTo(14);
    }

    @Test
    public void getLastNames() {
        given(repository.findAll()).willReturn(people);
        assertThat(service.getLastNames())
                .containsExactly("Hopper", "Lovelace", "Goldberg", "Borg", "Liskov");
    }

    @Test
    public void getTotalPeople() {
        given(repository.count()).willReturn((long) people.size());
        assertEquals(service.getTotalPeople(), people.size());
    }

    @Test
    public void saveAllPeople() {
        given(repository.save(any(Person.class)))
                .willReturn(people.get(0),
                            people.get(1),
                            people.get(2),
                            people.get(3),
                            people.get(4));

        List<Integer> ids = service.savePeople(
                people.toArray(Person[]::new));
        assertThat(ids).containsExactly(1, 2, 3, 14, 5);

        then(repository)
                .should(times(5))
                .save(any(Person.class));
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void saveAllPeopleUsingAnswer() {
        given(repository.save(any(Person.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        List<Integer> ids = service.savePeople(
                people.toArray(Person[]::new));
        assertThat(ids).containsExactly(1, 2, 3, 14, 5);

        then(repository)
                .should(times(5))
                .save(any(Person.class));
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void useAnswer() {
        given(repository.save(any(Person.class)))
                .will(invocation -> invocation.getArgument(0));

        List<Integer> ids = service.savePeople(people.toArray(Person[]::new));

        assertThat(ids).containsExactly(1, 2, 3, 14, 5);
    }

    @Test
    public void createPersonWithBadDateString() {
        DateTimeParseException exception = assertThrows(DateTimeParseException.class,
                () -> service.createPerson(1, "Grace", "Hopper", "12/09/1906"));
        assertThat(exception.getMessage()).contains("could not be parsed");
    }
}
