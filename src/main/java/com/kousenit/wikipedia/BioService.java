package com.kousenit.wikipedia;

import java.util.List;
import java.util.stream.Collectors;

public class BioService {
    private List<String> pageNames;

    public BioService() {
        pageNames = List.of("Anita Borg", "Ada Lovelace",
                "Grace Hopper", "Barbara Liskov");
    }

    public BioService(List<String> pageNames) {
        this.pageNames = pageNames;
    }

    public void setPageNames(List<String> pageNames) {
        this.pageNames = pageNames;
    }

    public List<String> getPageNames() {
        return pageNames;
    }

    public List<String> getBios() {
        return pageNames.stream()
                .map(WikiUtil::getWikipediaExtract)
                .collect(Collectors.toList());
    }
}
