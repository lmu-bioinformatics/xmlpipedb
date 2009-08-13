package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;

/**
 * Unit tests for GOA file processing.
 * 
 * @author   dondi
 * @since JUnit 4.x
 */
public class GOAFileTest {
    /**
     * Tests GOA file ID parsing.
     */
    @Test
    public void testGOAFileIDParsing() throws Exception {
        ExportGoData.populateUniprotGoTable(new BufferedReader(
            new InputStreamReader(
                getClass().getResourceAsStream("/edu/lmu/xmlpipedb/gmbuilder/databasetoolkit/go/test.goa")
            )
        ), null, null);
    }
}
