package com.kousenit.hr;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<String> getLastNames() {
        return repository.findAll()
                .stream()
                .map(Person::getLast)
                .toList();
    }

    public Integer getHighestId() {
        return repository.findAll()
                .stream()
                .mapToInt(Person::getId)
                .max().orElse(0);
    }

    public List<Integer> savePeople(Person... person) {
        return Arrays.stream(person)
                .map(repository::save)
                .map(Person::getId)
                .toList();
    }

    public Person createPerson(int id, String first, String last, LocalDate dob) {
        Person person = new Person(id, first, last, dob);
        repository.save(person);
        return person;
    }

    public long getTotalPeople() {
        return repository.count();
    }

    public void deleteAll() {
        repository.findAll()
                .forEach(repository::delete);
    }

    public List<Person> findByIds(Integer... ids) {
        return Arrays.stream(ids)
                .map(repository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
