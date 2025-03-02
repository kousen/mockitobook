package com.kousenit.wikipedia;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WikiResponseTest {

    @Test
    void testGettersAndSetters() {
        WikiResponse response = new WikiResponse();
        
        // Test batchcomplete
        response.setBatchcomplete("true");
        assertEquals("true", response.getBatchcomplete());
        
        // Test query and pages
        List<WikiPage> pages = new ArrayList<>();
        WikiPage page = new WikiPage();
        page.setTitle("Ada Lovelace");
        page.setExtract("Ada Lovelace was an English mathematician and writer.");
        pages.add(page);
        
        WikiQuery query = new WikiQuery();
        query.setPages(pages);
        
        response.setQuery(query);
        assertEquals(query, response.getQuery());
        assertEquals(pages, response.getQuery().getPages());
    }
    
    @Test
    void testToString() {
        WikiResponse response = new WikiResponse();
        response.setBatchcomplete("true");
        
        List<WikiPage> pages = new ArrayList<>();
        WikiPage page = new WikiPage();
        page.setTitle("Ada Lovelace");
        page.setExtract("Ada Lovelace was an English mathematician and writer.");
        pages.add(page);
        
        WikiQuery query = new WikiQuery();
        query.setPages(pages);
        
        response.setQuery(query);
        
        String result = response.toString();
        assertNotNull(result);
        assertTrue(result.contains("batchcomplete=true"));
    }
}