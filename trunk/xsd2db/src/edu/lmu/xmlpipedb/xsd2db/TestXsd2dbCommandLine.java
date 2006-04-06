package edu.lmu.xmlpipedb.xsd2db;

import junit.framework.*;
import edu.lmu.xmlpipedb.xsd2db.Xsd2dbCommandLine.*;

public class TestXsd2dbCommandLine extends TestCase {
    private Xsd2dbCommandLine xsd2dbCommandLine = null;

    protected void setUp() throws Exception {
        super.setUp();
        xsd2dbCommandLine = new Xsd2dbCommandLine();
    }

    protected void tearDown() throws Exception {
        xsd2dbCommandLine = null;
        super.tearDown();
    }

    public void testGetAbsolutePath() {
        String dir = "";
        String expectedReturn = null;
        String actualReturn = xsd2dbCommandLine.getAbsolutePath(dir);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetSchemaType() {
        Schema expectedReturn = null;
        Schema actualReturn = xsd2dbCommandLine.getSchemaType();
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testParse() {
        String[] args = null;
        xsd2dbCommandLine.parse(args);
        /**@todo fill in the test code*/
    }

}
