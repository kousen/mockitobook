package com.kousenit.wikipedia;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BioServiceTest {
    private final BioService service = new BioService();

    @Test
    void checkBios() {
        List<String> bios = service.getBios();
        assertEquals(4, bios.size());
        bios.forEach(bio -> {
            String[] strings = bio.split(":");
            String[] bioStrings = strings[1].split("\\n");
            System.out.println("Title: " + strings[0]);
            Arrays.stream(bioStrings).forEach(System.out::println);
            System.out.println("-------------------");
        });
    }

    @Test
    void testBioServiceWithMocks() {

    }
}