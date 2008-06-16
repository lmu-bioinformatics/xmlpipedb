package edu.lmu.xmlpipedb.xsd2db.test;

import junit.framework.*;
import edu.lmu.xmlpipedb.xsd2db.Xsd2dbCommandLine;
import edu.lmu.xmlpipedb.xsd2db.Xsd2dbCommandLine.*;
import edu.lmu.xmlpipedb.xsd2db.Xsd2db;
import edu.lmu.xmlpipedb.xsd2db.Xsd2db.*;

public class TestXsd2dbCommandLine extends TestCase {
    private Xsd2dbCommandLine xsd2dbCommandLine = null;

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        xsd2dbCommandLine = null;
        super.tearDown();
    }


    public void testParse() {
        String[] args = {"--outputdirectory=gen", 
                         "--bindings=bindings.xml", 
                         "--xsdURL=http://books.xsd",
                         "-dtdSchema"};
        xsd2dbCommandLine.getInstance().parse(args);
        assertEquals("output directory", xsd2dbCommandLine.getDBSrcDir().getName(), "gen");
        assertEquals("bindings file", xsd2dbCommandLine.getBindingsFile(), "bindings.xml");
        assertEquals("xsd url", xsd2dbCommandLine.getXSDURL(), "http://books.xsd");
        assertEquals("dtd Schema", xsd2dbCommandLine.getSchemaType(), Xsd2db.Schema.XSD);
    }

}
