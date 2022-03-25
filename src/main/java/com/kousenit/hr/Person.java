package com.kousenit.hr;

import java.time.LocalDate;

public record Person(
        Integer id,
        String first,
        String last,
        LocalDate dob
) {}