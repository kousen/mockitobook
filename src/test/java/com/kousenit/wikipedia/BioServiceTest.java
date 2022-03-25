package com.kousenit.wikipedia;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class BioServiceTest {
    @Test // Integration test
    void checkBios() {
        BioService service = new BioService("Anita Borg", "Ada Lovelace",
                "Grace Hopper", "Barbara Liskov");
        List<String> bios = service.getBios();
        assertEquals(4, bios.size());
        bios.forEach(bio -> {
            String[] strings = bio.split(":");
            String[] bioStrings = strings[1].split("\\n");
            System.out.println("Title: " + strings[0]);
            Arrays.stream(bioStrings)
                    .forEach(System.out::println);
            System.out.println("-------------------");
        });
    }

    @Test
    void testBioServiceWithMocks() {
        BioService service = new BioService("Anita Borg", "Ada Lovelace",
                "Grace Hopper", "Barbara Liskov");
        try (MockedStatic<WikiUtil> mocked = mockStatic(WikiUtil.class)) {
            mocked.when(() -> WikiUtil.getWikipediaExtract(anyString()))
                    .thenAnswer(invocation -> "Bio for " + invocation.getArgument(0));
            List<String> bios = service.getBios();
            System.out.println(bios);
            mocked.verify(() -> WikiUtil.getWikipediaExtract(anyString()),
                    times(4));
        }
    }
}