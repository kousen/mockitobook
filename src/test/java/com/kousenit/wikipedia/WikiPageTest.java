package com.kousenit.wikipedia;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WikiPageTest {

    @Test
    void testGettersAndSetters() {
        WikiPage page = new WikiPage();
        
        // Test pageId
        page.setPageid(123);
        assertEquals(123, page.getPageid());
        
        // Test namespace
        page.setNs(0);
        assertEquals(0, page.getNs());
        
        // Test title
        page.setTitle("Grace Hopper");
        assertEquals("Grace Hopper", page.getTitle());
        
        // Test extract
        String extract = "Grace Brewster Hopper was an American computer scientist, " +
                "mathematician, and United States Navy rear admiral.";
        page.setExtract(extract);
        assertEquals(extract, page.getExtract());
        
        // Test missing flag
        page.setMissing(true);
        assertTrue(page.getMissing());
        
        page.setMissing(false);
        assertFalse(page.getMissing());
    }
    
    @Test
    void testToString() {
        WikiPage page = new WikiPage();
        page.setPageid(123);
        page.setNs(0);
        page.setTitle("Ada Lovelace");
        page.setExtract("Ada Lovelace was an English mathematician and writer.");
        page.setMissing(false);
        
        String expected = "WikiPage{" +
                "pageid=123" +
                ", ns=0" +
                ", title='Ada Lovelace'" +
                ", extract='Ada Lovelace was an English mathematician and writer.'" +
                ", missing=false" +
                '}';
        
        assertEquals(expected, page.toString());
    }
}