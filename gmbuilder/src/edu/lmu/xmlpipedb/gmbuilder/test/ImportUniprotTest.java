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

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitorInputStream;
import javax.xml.bind.JAXBException;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.ImportEngine;
import edu.lmu.xmlpipedb.util.engines.QueryEngine;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ImportUniprotTest extends TestCase {

	/* **** class vars **** */
    private String _jaxbContextPath;
    private Configuration _hibernateConfiguration;
	private ConfigurationEngine _configEng;
	private File _xmlFile;
	
	/**
	 * 
	 */
	public ImportUniprotTest(String name){
		super(name);
	}
	
	
	protected void setUp() throws FileNotFoundException{
		// this is the same value as GenMAPPBuilder passes
		_jaxbContextPath = "org.uniprot.uniprot";
		// declare method vars
		ImportEngine importEngine;
		InputStream in;
		
		// path to test xml file
		_xmlFile = new File("./src/edu/lmu/xmlpipedb/gmbuilder/test/uniprot_unit_test_02.xml");
		if( !(_xmlFile.exists()) )
			throw new FileNotFoundException( "Could not find the xml file for testing.");
			
		// this didn't seem to work...
		//InputStream iStream = getClass().getResourceAsStream("uniprot_unit_test_01.xml");
		
		try {
//			FIXME first param is hardcoded to a path -- not good (Dondi??)
			// second param does not need a real value, for our purposes here
		    _configEng = new ConfigurationEngine("./src/edu/lmu/xmlpipedb/gmbuilder/test/hibernate.properties", "");
		    _hibernateConfiguration = _configEng.getHibernateConfiguration();
			// copied from GenMAPPBuilder line 260
		    _hibernateConfiguration.addJar(new File("./lib/uniprotdb.jar"));
		    
			in = new BufferedInputStream(new FileInputStream(_xmlFile));
			// this is the same call being made by ImportPanel in line 151
			importEngine = new ImportEngine(_jaxbContextPath, _hibernateConfiguration);
			// this is the same call as ImportPanel line 152
			importEngine.loadToDB(in);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e1) {
			// This is where importEngine keeps dropping me
			// don't know why.
			e1.printStackTrace();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testImport(){
		
        QueryEngine qe = new QueryEngine(_hibernateConfiguration);
        Connection conn = qe.currentSession().connection();
        PreparedStatement query = null;
        ResultSet results = null;

        try {
            query = conn.prepareStatement("select count(*) from entrytype;");
            results = query.executeQuery();
            results.next();
            Assert.assertEquals(1, results.getInt(1));
            
        } catch(SQLException sqle) {
            //JOptionPane.showMessageDialog(this, sqle.getMessage());
        } catch(Exception e) {
            //reportException(e);
        } finally {
            try {
                results.close();
                query.close();
                 conn.close();
                // HibernateUtil.closeSession();
            } catch(Exception e) {
                //reportException(e);
            } // Ignore the errors here, nothing we can do anyways.
        }

		
		
		
	}
	
	
}
