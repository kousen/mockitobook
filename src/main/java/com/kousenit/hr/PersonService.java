package com.kousenit.hr;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Integer> savePeople(Person... person) {
        return Arrays.stream(person)
                .map(repository::save)
                .map(Person::getId)
                .collect(Collectors.toList());
    }

    public void asyncSavePerson(Person person, long delay) {
        CompletableFuture.runAsync(() -> {
            System.out.println("Running on thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
            repository.save(person);
        });
    }

    public List<String> getLastNames() {
        return repository.findAll().stream()
                .map(Person::getLast)
                .collect(Collectors.toList());
    }

    public List<Person> findByIds(int... ids) {
        return Arrays.stream(ids)
                .mapToObj(repository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Integer getHighestId() {
        return repository.findAll().stream()
                .map(Person::getId)
                .max(Integer::compareTo).orElse(0);
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

}
