package com.kousenit.astro;

import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
public class AstroService {
    private final Gateway<AstroResponse> gateway;

    public AstroService(Gateway<AstroResponse> gateway) {
        this.gateway = gateway;
    }

    public Map<String, Long> getAstroData() {
        var response = gateway.getResponse();
        System.out.println(response);
        if (response instanceof Success<AstroResponse> success) {
            AstroResponse data = success.data();
            return data.people().stream()
                    .collect(Collectors.groupingBy(Assignment::craft, Collectors.counting()));
        } else if (response instanceof Failure<AstroResponse> failure){
            throw failure.exception();
        } else {
            throw new RuntimeException("Should never happen");
        }
    }
}
