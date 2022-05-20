package com.kousenit.hr;

import java.util.List;
import java.util.Optional;

public class MockPersonRepository implements PersonRepository {


    @Override
    public Person save(Person person) {
        return null;
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Person person) {

    }
}
