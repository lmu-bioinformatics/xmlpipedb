package edu.lmu.xmlpipedb.gmbuilder.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.QueryEngine;
import edu.lmu.xmlpipedb.gmbuilder.util.ImportGOAEngine;

public class ImportGOATest extends TestCase {

	/* **** class vars **** */
    private Configuration _hibernateConfiguration;
	private ConfigurationEngine _configEng;
	private File _goaFile;

	public ImportGOATest(String name){
		super(name);
	}

	protected void setUp() throws FileNotFoundException{
		ImportGOAEngine importGOAEngine;

		_goaFile = new File("./src/edu/lmu/xmlpipedb/gmbuilder/test/GOA_GAF_10.goa");
		//_goaFile = new File("./src/edu/lmu/xmlpipedb/gmbuilder/test/GOA_GAF_20.goa");
		//_goaFile = new File("./src/edu/lmu/xmlpipedb/gmbuilder/test/GOA_bad_format.goa");
		if( !(_goaFile.exists()) )
			throw new FileNotFoundException( "Could not find the goa file for testing.");

		try {
			_configEng = new ConfigurationEngine("./src/edu/lmu/xmlpipedb/gmbuilder/test/hibernate.properties", "");
		    _hibernateConfiguration = _configEng.getHibernateConfiguration();

			importGOAEngine = new ImportGOAEngine(_hibernateConfiguration);
			importGOAEngine.importToSQL(_goaFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testImportCounts(){
		// Initialize expected test results
		int rows = 10;

		QueryEngine qe = new QueryEngine(_hibernateConfiguration);
        Connection conn = qe.currentSession().connection();
        PreparedStatement query = null;
        ResultSet results = null;

        try {
        	query = conn.prepareStatement("select count(*) from goa");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(rows, results.getInt(1));
        } catch(SQLException e) {
            System.out.print("An SQLException was thrown while running this test. A stack trace follows:\n");
            e.printStackTrace();
        } catch(Exception e) {
        	System.out.print("An Unspecified Exception was thrown while running this test. A stack trace follows:\n");
            e.printStackTrace();
        } finally {
            try {
                results.close();
                query.close();
                conn.close();
            } catch(Exception e) {

            }
        }
	}


}