package com.kousenit.wikipedia;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class WikiUtilTest {

    @Test
    void getStringResponse() {
        String graceHopper = WikiUtil.getWikipediaExtract("Grace Hopper");
        System.out.println(graceHopper);
        assertThat(graceHopper).contains("Grace", "Hopper");
    }

    @Test
    void getResponseNotFound() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> WikiUtil.getWikipediaExtract("Not a real page"))
                .havingCause()
                .withMessageContaining("Not a real page");
    }
}