package com.kousenit.wikipedia;

import java.util.List;

@SuppressWarnings("unused")
public class WikiQuery {
    private List<WikiPage> pages;

    public List<WikiPage> getPages() {
        return pages;
    }

    public void setPages(List<WikiPage> pages) {
        this.pages = pages;
    }
    
    @Override
    public String toString() {
        return "WikiQuery{" +
                "pages=" + pages +
                '}';
    }
}
