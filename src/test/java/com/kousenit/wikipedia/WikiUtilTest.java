package com.kousenit.wikipedia;

import org.junit.jupiter.api.Test;

class WikiUtilTest {

    @Test
    void getStringResponse() {
        System.out.println(WikiUtil.getWikipediaExtract("Grace Hopper"));
    }
}