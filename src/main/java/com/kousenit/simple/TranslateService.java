package com.kousenit.simple;

public class TranslateService {

    // Translate from English to whatever locale is specified
    public String translate(String text, String language) {
        return language + " translation: " + text;
    }
}
