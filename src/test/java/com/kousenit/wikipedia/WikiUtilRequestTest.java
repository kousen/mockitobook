package com.kousenit.wikipedia;

import org.junit.jupiter.api.Test;

import java.net.http.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

class WikiUtilRequestTest {

    @Test
    void buildRequestIncludesWikipediaFriendlyHeaders() {
        HttpRequest request = WikiUtil.buildRequest("https://example.com");

        assertThat(request.headers().firstValue("User-Agent")).contains(WikiUtil.USER_AGENT);
        assertThat(request.headers().firstValue("Accept")).contains(WikiUtil.ACCEPT);
    }
}
