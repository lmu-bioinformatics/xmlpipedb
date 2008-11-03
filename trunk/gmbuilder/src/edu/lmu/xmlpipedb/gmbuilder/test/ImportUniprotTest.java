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

// FIXME This test needs to be updated to the latest UniProt XSD.  For example,
// the latest UniProt XSD no longer produces a proteinnametype table.
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
		// test_03 contains 5 "entry" records
		_xmlFile = new File("./src/edu/lmu/xmlpipedb/gmbuilder/test/uniprot_unit_test_03.xml");
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
	
	/* ### truths about Uniprot
	 *  - based on the file uniprot_unit_test_03.xml
	 *  - with entry 5 records
	 *   
	 * table			truth
	 * -----			-----
	 * copyright 		never has any records
	 * entrytype		has 5 records or 1 record per "entry" record in xml file
	 *					dataset="Swiss-Prot"
	 * proteinnametype	must have 13 records 
	 * genenametype		must have 12 records
	 * dbreference		must have 74 records
	 * organismnametype	must have 5 records, 1 for each "entry"
	 * sequencetype		must have 5 records, 1 for each "entry"
	 * 
	 * ----
	 * Organism entries are broken into 5 tables:
	 * organismnametype
	 * organismtype
	 * organismtype_lineagetype
	 * organismtype_lineagetype_taxon
	 * dbreftype
	 * The last table, dbreftype contains the dbreference from the organism
	 *   entry as well as all the dbreference's for the entire entry. The number
	 *   of dbreference's for the entry varies. It also contains dbReference records
	 *   that are listed under "citation", nested under "reference". Duplicate 
	 *   entries are kept.
	 * 
	 */
	
	
	public void testImportCounts(){
		// initialize expected test results
		int copyrightcount = 0;
		int entrytypecount = 5;
		int proteinnametypecount = 13;
		int genenametypecount = 12;
		int dbreferencecount = 74;
		int organismnametypecount = 5;
		int sequencetypecount = 5;
		
		// initialize expected test results
		HashMap<String, String> sequences = new HashMap<String, String>(5);
		sequences.put("571", "MSQPWPAVEIARMILAGFDDYRDHFQRITLGARQRFEQARWQDIQQAAAARINLYEEKVAEVNGWLRQAFAAEVLLDVEQWPLVKNAYIHLIDPRLDDELAETWYNSLFCSLFSHDLISDGCMFIHTTRPSMRGRERAAQTRTYRLDGSLRNLLRAVFADYPFDMPYGDLEGDLARLEEQLRECLPDWVCKDPALAVELFVPVLYRNKGAYLVGRLYNSDEQWPLVIPLLHREGHGIEADALITDEAEVSIIFSFTRSYFMADVPVPAEFVNFLKRILPGKHIAELYTSIGFYKQGKSEFYRALINHLASSDDRFVMAPGVRGMVMSVFTLPGFNTVFKIIKDRFSPSKTVDRATVIDKYRLVKSVDRVGRMADTQEFADFRFPRSKFEPDCLAELLEVAPSTVALEGDTVLIRHCWTERRMTPLNLYLEQATEGQVLEALEDYGLAIKQLAAANIFPGDMLLKNFGVTRHGRVVFYDYDEISFLTEVNFRHIPPPRYPEDEMSGEPWYSIGPHDVFPEEFPPFLFADMGQRRLFSRLHGELYDADYWKGLQAAIREGKVIDVFPYRRKAR");
		sequences.put("78", "MSTIEERVKKIVAEQLGVKEEEVTVEKSFVDDLGADSLDTVELVMALEEEFETEIPDEEAEKITTVQAAIDYVKAHQA");
		sequences.put("653", "MSAAPLYPVRPEVAATTLTDEATYKAMYQQSVINPDGFWREQAQRIDWIKPFTKVKQTSFDDHHVDIKWFADGTLNVSSNCLDRHLEERGDQLAIIWEGDDPSEHRNITYRELHEQVCKFANALRGQDVHRGDVVTIYMPMIPEAVVAMLACARIGAIHSVVFGGFSPEALAGRIIDCKSKVVITADEGVRGGRRTPLKANVDLALTNPETSSVQKIIVCKRTGGDIAWHQHRDIWYEDLMKVASSHCAPKEMGAEEALFILYTSGSTGKPKGVLHTTGGYLVYAALTHERVFDYRPGEVYWCTADVGWVTGHSYIVYGPLANGATTLLFEGVPNYPDITRVSKIVDKHKVNILYTAPTAIRAMMAEGQAAVEGADGSSLRLLGSVGEPINPEAWNWYYKTVGKERCPIVDTWWQTETGGILISPLPGATGLKPGSATRPFFGVVPALVDNLGNLIDGAAEGNLVILDSWPGQSRSLYGDHDRFVDTYFKTFRGMYFTGDGARRDEDGYYWITGRVDDVLNVSGHRMGTAEIESAMVAHSKVAEAAVVGVPHDIKGQGIYVYVTLNAGIEASEQLRLELKNWVRKEIGPIASPDVIQWAPGLPKTRSGKIMRRILRKIATGEYDALGDISTLADPGVVQHLIDTHKAMNLASA");
		sequences.put("644", "MFDIRKYPQALAVSQSAALTPEDYRRLYRQSVEDPDTFWAEQAKRLDWIKPWSSVQQCDLHTGKARWFDGAQLNVSYNCIDRHLAQRGEQTALLWEGDDPKDSKAITYRELHRQVCRLANAMKARGVKKGDRVSIYMPMIPEAAFAMLACTRIGAIHSVVFGGFSPDALRDRILDADCRTVITADEGVRGGKRIPLKQNVDKALASCPAVSSVLVVRRTGGDVAWTEGRDLWYHEATKDAGDDCPPEPMEAEDPLFILYTSGSTGKPKGVLHTTGGYLLQATMTFKVVFDYRDGEVFWCTADVGWVTGHSYIVYGPLANGAISLMFEGVPNYPDTSRFWQVVDKHQVNIFYTAPTALRALMREGSAPLQSTSRKSLRLLGSVGEPINPEAWEWYFEEVGQKRCPIVDTWWQTETGGIMLTPLPGTQSLKPGCATQPMFGVQPVLLDEKGKLIEGPGAGVLAIKASWPGQIRSVYGDHQRMVDTYFKPLPGYYFTGDGARRDADGDYWITGRIDDVINVSGHRIGTAEVESALVLHDSVAEAAVVGYPHDLKGQGVYAFVTTMNGVTPDDTLKAELLALVSKEIGSFAKPELIQWAPALPKTRSGKIMRRILRKIACNELENLGDTSTLADPSVVQGLIDKRLNQ");
		sequences.put("315", "MYDWLNALPKAELHLHLEGSLEPELLFALAERNKIALPWADVETLRGAYAFNNLQEFLDLYYQGADVLRTEQDFYDLTWAYLQRCKAQNVIHTEPFFDPQTHTDRGIAFEVVLNGISQALKDGREQLGISSGLILSFLRHLSEDEAQKTLDQALPFRDAFIAVGLDSSEMGHPPSKFQRVFDRARSEGFVAVAHAGEEGPPEYIWEALDLLKIKRIDHGVRAIEDERLMQRIIDEQIPLTVCPLSNTKLCVFDHMSQHNILDMLERGVKVTVNSDDPAYFGGYVTENFHALHTHLGMTEDQARRLAQNSLDARLV");


		QueryEngine qe = new QueryEngine(_hibernateConfiguration);
        Connection conn = qe.currentSession().connection();
        PreparedStatement query = null;
        ResultSet results = null;

        try {
            query = conn.prepareStatement("select count(*) from copyright;");
            results = query.executeQuery();
            results.next();
            Assert.assertEquals(copyrightcount, results.getInt(1));
            
            query = conn.prepareStatement("select count(*) from entrytype;");
            results = query.executeQuery();
            results.next();
            Assert.assertEquals(entrytypecount, results.getInt(1));
            
            query = conn.prepareStatement("select count(*) from proteinnametype;");
            results = query.executeQuery();
            results.next();
            Assert.assertEquals(proteinnametypecount, results.getInt(1));
            
            query = conn.prepareStatement("select count(*) from genenametype;");
            results = query.executeQuery();
            results.next();
            Assert.assertEquals(genenametypecount, results.getInt(1));
            
            query = conn.prepareStatement("select count(*) from dbreference;");
            results = query.executeQuery();
            results.next();
            Assert.assertEquals(dbreferencecount, results.getInt(1));
            
            query = conn.prepareStatement("select count(*) from organismnametype;");
            results = query.executeQuery();
            results.next();
            Assert.assertEquals(organismnametypecount, results.getInt(1));
            
            query = conn.prepareStatement("select count(*) from sequencetype;");
            results = query.executeQuery();
            results.next();
            Assert.assertEquals(sequencetypecount, results.getInt(1));
            
            query = conn.prepareStatement("select length, value from sequencetype;");
            results = query.executeQuery();
            results.beforeFirst();
            while(results.next()){
            	String testResult = sequences.get(results.getString("length"));
            	Assert.assertEquals(testResult, (String)results.getString("value"));
            }
            
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
	} // end testImportCounts


	
}
