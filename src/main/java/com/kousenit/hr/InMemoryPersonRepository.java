package com.kousenit.hr;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryPersonRepository implements PersonRepository {
    private final List<Person> people = new ArrayList<>();

    @Override
    public final Person save(Person person) {
        synchronized (people) {
            people.add(person);
        }
        return person;
    }

    @Override
    public Optional<Person> findById(int id) {
        Map<Integer,Person> peopleMap =
                people.stream().collect(Collectors.toMap(Person::getId, p -> p));
        return Optional.ofNullable(peopleMap.get(id));
    }

    @Override
    public List<Person> findAll() {
        return people;
    }

    @Override
    public long count() {
        return people.size();
    }

    @Override
    public final void delete(Person person) {
        synchronized (people) {
            people.remove(person);
        }
    }
}
