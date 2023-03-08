package com.kousenit.hr;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
    private final Integer id;
    private final String first;
    private final String last;
    private final LocalDate dob;

    Person(Integer id, String first, String last, LocalDate dob) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.dob = dob;
    }

    public Integer getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public LocalDate getDob() {
        return dob;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Person that = (Person) obj;
        return Objects.equals(this.id, that.id) &&
               Objects.equals(this.first, that.first) &&
               Objects.equals(this.last, that.last) &&
               Objects.equals(this.dob, that.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first, last, dob);
    }

    @Override
    public String toString() {
        return "Person[" +
               "id=" + id + ", " +
               "first=" + first + ", " +
               "last=" + last + ", " +
               "dob=" + dob + ']';
    }
}