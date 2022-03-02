package com.kousenit.wikipedia;

import java.util.Arrays;
import java.util.List;

public class BioService {
    private final List<String> pageNames;

    public BioService(String... pageNames) {
        this.pageNames = Arrays.stream(pageNames).toList();
    }

    public List<String> getBios() {
        return pageNames.stream()
                .map(WikiUtil::getWikipediaExtract)
                .toList();
    }
}
