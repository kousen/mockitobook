package com.kousenit.hr;

public class DefaultTranslationService implements TranslationService {
    public String translate(String text, String sourceLanguage, String targetLanguage) {
        return TranslationService.super.translate(text, "en", "en");
    }

    public String translate(String text) {
        return text;
    }
}
