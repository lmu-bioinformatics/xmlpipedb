package edu.lmu.xmlpipedb.xsd2db.test;

import junit.framework.*;
import edu.lmu.xmlpipedb.xsd2db.Xsd2dbCommandLine;
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


    public void testGetSchemaType() {
        String[] args = {""};
        xsd2dbCommandLine.parse(args);
        Schema expectedReturn = Xsd2dbCommandLine.Schema.XSD;
        Schema actualReturn = xsd2dbCommandLine.getSchemaType();
        assertEquals("return value", expectedReturn, actualReturn);
    }

    public void testParse() {
        String[] args = {"--outputdirectory=gen", 
                         "--bindings=bindings.xml", 
                         "--xsdURL=http://books.xsd",
                         "-dtdSchema"};
        xsd2dbCommandLine.parse(args);
        assertEquals("output directory", xsd2dbCommandLine.getDBSrcDir().getName(), "gen");
        assertEquals("bindings file", xsd2dbCommandLine.getBindingsFile(), "bindings.xml");
        assertEquals("xsd url", xsd2dbCommandLine.getXSDURL(), "http://books.xsd");
        assertEquals("dtd Schema", xsd2dbCommandLine.getSchemaType(), Xsd2dbCommandLine.Schema.XSD);
    }

}
