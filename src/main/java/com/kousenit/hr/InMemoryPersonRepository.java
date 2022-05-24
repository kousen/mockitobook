package com.kousenit.hr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryPersonRepository implements PersonRepository {
    private final List<Person> people =
            Collections.synchronizedList(new ArrayList<>());

    @Override
    public Person save(Person person) {
        synchronized (people) {
            people.add(person);
        }
        return person;
    }

    @Override
    public Optional<Person> findById(int id) {
        return people.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
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
    public void delete(Person person) {
        synchronized (people) {
            people.remove(person);
        }
    }
}
