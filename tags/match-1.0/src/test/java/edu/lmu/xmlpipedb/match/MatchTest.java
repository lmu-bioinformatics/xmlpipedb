package edu.lmu.xmlpipedb.match;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.Test;

/**
 * MatchTest tests the Match.matchUnique() method against a number of strings.
 * 
 * @author   dondi
 */
public class MatchTest {

    @Test
    public void testNothing() throws IOException {
        Map<String, Integer> result = Match.matchUnique("non-matcher", new StringReader("Nothing matches."));
        assertEquals(0, result.size());
    }
    
    @Test
    public void testSimpleMatch() throws IOException {
        Map<String, Integer> result = Match.matchUnique("hello", new StringReader("hello world hello sun hello moon"));
        assertEquals(1, result.size());
        
        Integer count = result.get("hello");
        assertNotNull(count);
        assertEquals(3, count);
    }
    
    @Test
    public void testMultilineMatch() throws IOException {
        Map<String, Integer> result = Match.matchUnique("hello", new StringReader("hello world\n\nhello sun\rhello moon\r\n"));
        assertEquals(1, result.size());
        
        Integer count = result.get("hello");
        assertNotNull(count);
        assertEquals(3, count);
    }
    
    @Test
    public void testRegexMatch() throws IOException {
        // "Date: ##/##/####"
        Map<String, Integer> result = Match.matchUnique("Date: \\d\\d/\\d{2}/\\d{4}", new StringReader("First Date: 12/20/2005...\nSecond Date: 01/19/1980;\nNon-matching date: 4/2/2000"));
        assertEquals(2, result.size());
        
        Integer count = result.get("date: 12/20/2005");
        assertNotNull(count);
        assertEquals(1, count);
        
        count = result.get("date: 01/19/1980");
        assertNotNull(count);
        assertEquals(1, count);
    }
    
    @Test
    public void testMultiRegexMatch() throws IOException {
        // "ABCD##"
        Map<String, Integer> result = Match.matchUnique("ABCD\\d\\d", new StringReader("blah blah ABCD02 blah blah ABCD1 blah blah\n\nblah blahABCD02blah\n\nblah ABCD03 ABCD03\nABCD04 blah ABCD03\n"));
        assertEquals(3, result.size());
        
        Integer count = result.get("abcd02");
        assertNotNull(count);
        assertEquals(2, count);
        
        count = result.get("abcd03");
        assertNotNull(count);
        assertEquals(3, count);
        
        count = result.get("abcd04");
        assertNotNull(count);
        assertEquals(1, count);
    }
}
