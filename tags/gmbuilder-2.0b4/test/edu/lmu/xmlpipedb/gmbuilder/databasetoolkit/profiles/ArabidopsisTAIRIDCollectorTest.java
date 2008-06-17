package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Standalone unit test for just the TAIR ID collection routines.
 * 
 * @author   dondi
 */
public class ArabidopsisTAIRIDCollectorTest {

    private ArabidopsisTAIRIDCollector _collector;
    private Connection _c;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Class.forName("org.postgresql.Driver");
        _c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/arabidopsis07", "xmlpipedb", null);
        _collector = new ArabidopsisTAIRIDCollector();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        PreparedStatement ps = _c.prepareStatement("drop table temp_tair");
        ps.executeUpdate();
        ps.close();
        _c.close();
        _collector = null;
    }

    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.ArabidopsisTAIRIDCollector#collectTAIRIDs(java.sql.Connection)}.
     */
    @Test
    public void testCollectTAIRIDs() {
        _collector.collectTAIRIDs(_c, false);
    }

    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.ArabidopsisTAIRIDCollector#collectTAIRIDs(java.sql.Connection)}.
     */
    @Test
    public void testCollectTAIRIDsTemporary() {
        _collector.collectTAIRIDs(_c, true);
    }

}
