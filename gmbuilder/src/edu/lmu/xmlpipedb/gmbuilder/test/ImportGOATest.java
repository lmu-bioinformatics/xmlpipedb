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
import java.sql.Date;

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

		// For GOA_GAF_10.goa
		int rows = 10;
		int[] keys = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		String db = "UniProtKB/Swiss-Prot";
		String dbObjectID = "P38903";
		String dbObjectSymbol = "RTS1";
		String[] qualifier = { "", "", "", "", "", "", "", "contributes_to", "", ""};
		String[] goID = { "GO:0005634", "GO:0005737", "GO:0000159", "GO:0005737",
				          "GO:0006470", "GO:0051754", "GO:0005515", "GO:0004722",
				          "GO:0005737", "GO:0005515"};
		String[] goReference = { "GO_REF:0000023", "GO_REF:0000023",
				                 "GO_REF:0000002", "PMID:11914276",
				                 "PMID:19605686", "PMID:16541024",
				                 "PMID:11283351", "PMID:11038366",
				                 "GO_REF:0000004", "PMID:11283351"};
		String[] evidenceCode = { "IEA", "IEA", "IEA", "IDA", "IMP", "IMP", "IPI",
				                  "IPI", "IEA", "IPI" };
		String[] withOrFrom = {"SP_SL:SL-0191", "SP_SL:SL-0086", "InterPro:IPR002554",
                               "", "", "", "UniProtKB:P47135", "", "SP_KW:KW-0963",
                               "UniProtKB:P53958" };
		String[] aspect = {"C", "C", "C", "C", "P", "P", "F", "F", "C", "F" };
		String dbObjectName = "Serine/threonine-protein phosphatase 2A 56 kDa regulatory subunit delta isoform";
		String dbObjectSynonym = "RTS1|SCS1|YOR014W|OR26.04|2A5D_YEAST";
		String taxon = "taxon:4932";
		Date[] date = {new Date(2009, 10, 1),
				       new Date(2009, 10, 1),
				       new Date(2009, 10, 1),
				       new Date(2002, 5, 7),
				       new Date(2009, 9, 23),
				       new Date(2006, 5, 25),
				       new Date(2009, 10, 1),
				       new Date(2009, 9, 22),
				       new Date(2009, 10, 1),
				       new Date(2009, 10, 1)};
		String[] assignedBy = {"UniProtKB", "UniProtKB", "UniProtKB", "SGD", "SGD", "SGD", "IntAct",
				               "SGD", "UniProtKB", "IntAct" };
		String annotationExtension = "";
		String geneProductFormID = "";

		QueryEngine qe = new QueryEngine(_hibernateConfiguration);
        Connection conn = qe.currentSession().connection();
        PreparedStatement query = null;
        ResultSet results = null;

        try {
        	query = conn.prepareStatement("select count(*) from goa");
        	results = null;
			results = query.executeQuery();
			results.next();
			Assert.assertEquals(rows, results.getInt(1));
			query = conn.prepareStatement("select * from goa order by primdbkey");
			for (int i = 0; i < rows; i++) {
				results.next();
				Assert.assertEquals(keys[i], results.getInt(1));
				Assert.assertEquals(db, results.getString(2));
				Assert.assertEquals(dbObjectID, results.getString(3));
				Assert.assertEquals(dbObjectSymbol, results.getString(4));
				Assert.assertEquals(qualifier[i], results.getString(5));
				Assert.assertEquals(goID[i], results.getString(6));
				Assert.assertEquals(goReference[i], results.getString(7));
				Assert.assertEquals(evidenceCode[i], results.getString(8));
				Assert.assertEquals(withOrFrom[i], results.getString(9));
				Assert.assertEquals(aspect[i], results.getString(10));
				Assert.assertEquals(dbObjectName, results.getString(11));
				Assert.assertEquals(dbObjectSynonym, results.getString(12));
				Assert.assertEquals(taxon, results.getString(13));
				Assert.assertEquals(date[i], results.getDate(14));
				Assert.assertEquals(assignedBy[i], results.getString(15));
				Assert.assertEquals(annotationExtension, results.getString(16));
				Assert.assertEquals(geneProductFormID, results.getString(17));
			}

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