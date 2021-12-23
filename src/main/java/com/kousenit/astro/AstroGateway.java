package com.kousenit.astro;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class AstroGateway implements Gateway<AstroResponse> {
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(2))
            .build();
    @SuppressWarnings("HttpUrlsUsage")
    private final HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://api.open-notify.org/astros.json"))
            .GET() // default (could leave that out)
            .build();
    private final JsonMapper jsonMapper = new JsonMapper();

    @Override
    public Result<AstroResponse> getResponse() {
        try {
            HttpResponse<String> httpResponse =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return new Success<>(
                    jsonMapper.readValue(httpResponse.body(), AstroResponse.class));
        } catch (IOException | InterruptedException e) {
            return new Failure<>(new RuntimeException(e));
        }
    }
}

record Assignment(String name, String craft) {
}

record AstroResponse(int number,
                     String message,
                     List<Assignment> people) {
}

