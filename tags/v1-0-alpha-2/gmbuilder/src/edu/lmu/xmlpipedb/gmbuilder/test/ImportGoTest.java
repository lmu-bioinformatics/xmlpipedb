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
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.ImportEngine;
import edu.lmu.xmlpipedb.util.engines.QueryEngine;

public class ImportGoTest extends TestCase {

	/* **** class vars **** */
    private String _jaxbContextPath;
    private Configuration _hibernateConfiguration;
	private ConfigurationEngine _configEng;
	private File _xmlFile;
	
	/**
	 * 
	 */
	public ImportGoTest(String name){
		super(name);
	}
	
	
	protected void setUp() throws FileNotFoundException{
		// this is the same value as GenMAPPBuilder passes
		// see line 145
		_jaxbContextPath = "generated";
		// declare method vars
		ImportEngine importEngine;
		InputStream in;
		
		// path to test xml file
		// test_03 contains 5 "entry" records
		_xmlFile = new File("./src/edu/lmu/xmlpipedb/gmbuilder/test/go_obo_short.xml");
		if( !(_xmlFile.exists()) )
			throw new FileNotFoundException( "Could not find the xml file for testing.");
			
	
		try {
			// first param is hardcoded to a path -- not good (Dondi??)
			// second param does not need a real value, for our purposes here
		    _configEng = new ConfigurationEngine("./src/edu/lmu/xmlpipedb/gmbuilder/test/hibernate.properties", "");
		    _hibernateConfiguration = _configEng.getHibernateConfiguration();
			// copied from GenMAPPBuilder line 261
		    _hibernateConfiguration.addJar(new File("lib/godb.jar"));
		    
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

	
	public void testImportCounts(){
		// initialize expected test results
		int acccount = 6;
		int altidcount = 0;
		int autogeneratedbycount = 1;
		int commentcount = 2;
		int datecount = 1;
		int dbnamecount = 6;
		int defaultnamespacecount = 1;
		int defstrcount = 5;
		int domaincount = 0;
		int formatversioncount = 1;
		int idcount = 10;
		int inverseofcount = 0;
		int isacount = 4;
		int isanonymouscount = 0;
		int isantisymmetriccount = 0;
		int isobsoletecount = 0;
		int isreflexivecount = 0;
		int isrootcount = 1;
		int issymmetriccount = 0;
		int istransitivecount = 0;
		int lexicalcategorycount = 0;
		int namecount = 10;
		int namespacecount = 5;
		int rangecount = 0;
		int remarkcount = 1;
		int savedbycount = 1;
		int sourcemd5count = 1;
		int sourcemtimecount = 1;
		int sourcepathcount = 1;
		int sourcetypecount = 1;
		int subsetcount = 13;
		int synonymtextcount = 2;
		int to_count = 1;
		int typecount = 1;
		int unionofcount = 0;
		int dbxref_contentcount = 12;
		int dbxrefcount = 6;
		int def_contentcount = 11;
		int defcount = 5;
		int header_contentcount = 11;
		int headercount = 1;
		int intersectionof_contentcount = 0;
		int intersectionofcount = 0;
		int obo_contentcount = 7;
		int obocount = 1;
		int relationship_contentcount = 2;
		int relationshipcount = 1;
		int source_contentcount = 4;
		int sourcecount = 1;
		int subsetdef_contentcount = 10;
		int subsetdefcount = 5;
		int synonymcategory_contentcount = 0;
		int synonymcategorycount = 0;
		int synonym_contentcount = 2;
		int synonymcount = 2;
		int term_contentcount = 43;
		int termcount = 5;
		int typedef_contentcount = 0;
		int typedefcount = 0;
		int xrefanalog_contentcount = 0;
		int xrefanalogcount = 0;
		int xrefunknown_contentcount = 0;
		int xrefunknowncount = 0;

		QueryEngine qe = new QueryEngine(_hibernateConfiguration);
        Connection conn = qe.currentSession().connection();
        PreparedStatement query = null;
        ResultSet results = null;

        try {
        	query = conn.prepareStatement("select count(*) from acc");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(acccount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from altid");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(altidcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from autogeneratedby");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(autogeneratedbycount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from comment");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(commentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from date");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(datecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from dbname");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(dbnamecount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from defaultnamespace");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(defaultnamespacecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from defstr");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(defstrcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from domain");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(domaincount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from formatversion");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(formatversioncount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from id");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(idcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from inverseof");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(inverseofcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from isa");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(isacount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from isanonymous");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(isanonymouscount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from isantisymmetric");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(isantisymmetriccount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from isobsolete");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(isobsoletecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from isreflexive");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(isreflexivecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from isroot");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(isrootcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from issymmetric");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(issymmetriccount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from istransitive");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(istransitivecount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from lexicalcategory");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(lexicalcategorycount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from name");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(namecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from namespace");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(namespacecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from range");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(rangecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from remark");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(remarkcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from savedby");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(savedbycount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from sourcemd5");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(sourcemd5count, results.getInt(1));
			query = conn.prepareStatement("select count(*) from sourcemtime");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(sourcemtimecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from sourcepath");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(sourcepathcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from sourcetype");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(sourcetypecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from subset");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(subsetcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from synonymtext");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(synonymtextcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from to_");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(to_count, results.getInt(1));
			query = conn.prepareStatement("select count(*) from type");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(typecount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from unionof");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(unionofcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from dbxref_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(dbxref_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from dbxref");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(dbxrefcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from def_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(def_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from def");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(defcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from header_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(header_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from header");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(headercount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from intersectionof_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(intersectionof_contentcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from intersectionof");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(intersectionofcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from obo_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(obo_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from obo");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(obocount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from relationship_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(relationship_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from relationship");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(relationshipcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from source_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(source_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from source");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(sourcecount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from subsetdef_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(subsetdef_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from subsetdef");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(subsetdefcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from synonymcategory_content");
			results = query.executeQuery();
			results.next();
			Assert
					.assertEquals(synonymcategory_contentcount, results
							.getInt(1));
			query = conn
					.prepareStatement("select count(*) from synonymcategory");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(synonymcategorycount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from synonym_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(synonym_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from synonym");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(synonymcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from term_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(term_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from term");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(termcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from typedef_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(typedef_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from typedef");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(typedefcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from xrefanalog_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(xrefanalog_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from xrefanalog");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(xrefanalogcount, results.getInt(1));
			query = conn
					.prepareStatement("select count(*) from xrefunknown_content");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(xrefunknown_contentcount, results.getInt(1));
			query = conn.prepareStatement("select count(*) from xrefunknown");
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(xrefunknowncount, results.getInt(1));

        	
        } catch(SQLException sqle) {
            // JOptionPane.showMessageDialog(this, sqle.getMessage());
        } catch(Exception e) {
            // reportException(e);
        } finally {
            try {
                results.close();
                query.close();
                 conn.close();
                // HibernateUtil.closeSession();
            } catch(Exception e) {
                // reportException(e);
            } // Ignore the errors here, nothing we can do anyways.
        }
	} // end testImportCounts

	
	
}