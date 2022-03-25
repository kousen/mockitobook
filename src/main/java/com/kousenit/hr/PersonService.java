package com.kousenit.hr;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ClassCanBeRecord")
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<String> getLastNames() {
        return repository.findAll()
                .stream()
                .map(Person::last)
                .toList();
    }

    public Integer getHighestId() {
        return repository.findAll()
                .stream()
                .mapToInt(Person::id)
                .max().orElse(0);
    }

    public List<Integer> savePeople(Person... person) {
        return Arrays.stream(person)
                .map(repository::save)
                .map(Person::id)
                .toList();
    }

    public Person createPerson(int id, String first, String last, LocalDate dob) {
        Person person = new Person(id, first, last, dob);
        return repository.save(person);
    }

    public Person createPerson(int id, String first, String last, String dobString) {
        Person person = new Person(id, first, last, LocalDate.parse(dobString));
        return repository.save(person);
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
