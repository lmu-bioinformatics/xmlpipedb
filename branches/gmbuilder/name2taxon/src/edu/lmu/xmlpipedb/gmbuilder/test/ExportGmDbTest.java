package edu.lmu.xmlpipedb.gmbuilder.test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ExportGmDbTest extends TestCase {


	/**
	 * 
	 */
	public ExportGmDbTest(String name){
		super(name);
	}
	
	
	protected void setUp() throws FileNotFoundException{
        // Make a new database profile object.
        // --- see UniProtDatabaseProfile
        // Set the systems tables to use.
        // Set the relationships tables to use.
        // Tell it which species to use.
//		try {
//			ExportToGenMaPP.exportToGenMaPP(new File("./src/edu/lmu/xmlpipedb/gmbuilder/test/gmb_unit_test.mdb"));
//		} catch (HibernateException e) {
//			ModalDialog.showErrorDialog("HIBERNATE error.");
//			e.printStackTrace();
//		} catch (SAXException e) {
//			ModalDialog.showErrorDialog("SAX error.");
//			e.printStackTrace();
//		} catch (JAXBException e) {
//			ModalDialog.showErrorDialog("JAXB error.");
//			e.printStackTrace();
//		} catch (SQLException e) {			
//            ModalDialog.showErrorDialog("SQL error.");
//            e.printStackTrace();
//		} catch (IOException e) {
//			ModalDialog.showErrorDialog("I/O error.");
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			ModalDialog.showErrorDialog("Database driver error.");
//			e.printStackTrace();
//		} catch (Exception e) {
//			ModalDialog.showErrorDialog(e.toString());
//			e.printStackTrace();
//		}
	}
	

	// TODO Tests to write:
    //     testAvailableSpecies()
    //     testRelationshipTables()
    //     testTableManagers()
	
	public void testExportCounts(){
		// init method vars
		Connection conn = null;
		PreparedStatement query = null;
		ResultSet results = null;
		// initialize expected test results
//		int blattnerCount= 0;
		int blattnerGeneOntologyCount= 0;
		int blattnerInterProCount= 0;
		int blattnerPDBCount= 0;
		int blattnerPfamCount= 0;
		int echoBASECount= 0;
		int ehoBASEBlattnerCount= 0;
		int echoBASEGeneOntologyCount= 0;
		int echoBASEInterProCount= 0;
		int echoBASEPDBCount= 0;
		int echoBASEPfamCount= 0;
		int ecoGeneCount= 0;
		int ecoGeneBlattnerCount= 0;
		int ecoGeneEchoBASECount= 0;
		int ecoGeneGeneOntologyCount= 0;
		int ecoGeneInterProCount= 0;
		int ecoGenePDMCount= 0;
		int ecoGenePfamCount= 0;
		int EMBLCount= 0;
		int EMBLBlattnerCount= 0;
		int EMBLEchoBASECount= 0;
		int EMBLEcoGeneCount= 0;
		int EMBLGeneOntologyCount= 0;
		int EMBLInterProCount= 0;
		int EMBLPDBCount= 0;
		int EMBLPfamCount= 0;
		int geneOntologyCount= 0;
		int geneOntologyCountCount= 0;
//		int geneOntologyStageCount= 0;
		int geneOntologyTreeCount= 0;
		int infoCount= 0;
		int interProCount= 0;
		int originalRowCountsCount= 0;
		int otherCount= 0;
		int PDBCount= 0;
//		int PDBGeneOntologyCount= 0;
//		int PDBInterProCount= 0;
//		int PDBPfamCount= 0;
		int pfamCount= 0;
		int relationsCount= 0;
		int systemsCount= 0;
		int uniProtCount= 0;
		int uniProtBlattnerCount= 0;
		int uniProtEchoBASECount= 0;
		int uniProtEcoGeneCount= 0;
		int uniProtEMBLCount= 0;
		int uniProtGeneOntologyCount= 0;
		int uniProtGOCountCount= 0;
		int uniProtInterProCount= 0;
		int uniProtPDBCount= 0;
		int uniProtPfamCount = 0;

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			conn = DriverManager.getConnection(dbUrl);
			
			// NOTE: in MS Access (and/or ODBC) table names with a dash (-) must
			// placed in [square brackets] or you will get an "Error in FROM clause"
//			query = conn.prepareStatement("select count(*) from Blattner;");
//			 results = query.executeQuery();
//			 results.next();
//			 Assert.assertEquals(blattnerCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [Blattner-GeneOntology];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(blattnerGeneOntologyCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [Blattner-InterPro];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(blattnerInterProCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [Blattner-PDB];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(blattnerPDBCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [Blattner-Pfam];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(blattnerPfamCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from EchoBASE;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(echoBASECount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EchoBASE-Blattner];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(ehoBASEBlattnerCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EchoBASE-GeneOntology];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(echoBASEGeneOntologyCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EchoBASE-InterPro];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(echoBASEInterProCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EchoBASE-PDB];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(echoBASEPDBCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EchoBASE-Pfam];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(echoBASEPfamCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from EcoGene;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(ecoGeneCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EcoGene-Blattner];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(ecoGeneBlattnerCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EcoGene-EchoBASE];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(ecoGeneEchoBASECount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EcoGene-GeneOntology];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(ecoGeneGeneOntologyCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EcoGene-InterPro];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(ecoGeneInterProCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EcoGene-PDM];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(ecoGenePDMCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EcoGene-Pfam];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(ecoGenePfamCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from EMBL;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(EMBLCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EMBL-Blattner];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(EMBLBlattnerCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EMBL-EchoBASE];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(EMBLEchoBASECount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EMBL-EcoGene];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(EMBLEcoGeneCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EMBL-GeneOntology];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(EMBLGeneOntologyCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EMBL-InterPro];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(EMBLInterProCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EMBL-PDB];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(EMBLPDBCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [EMBL-Pfam];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(EMBLPfamCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from GeneOntology;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(geneOntologyCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from GeneOntologyCount;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(geneOntologyCountCount, results.getInt(1));

			/* s/b here or not? not in Kam's list */ 
//			query = conn.prepareStatement("select count(*) from GeneOntologyStage;");
//			 results = query.executeQuery();
//			 results.next();
//			 Assert.assertEquals(geneOntologyStageCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from GeneOntologyTree;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(geneOntologyTreeCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from Info;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(infoCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from InterPro;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(interProCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from OriginalRowCounts;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(originalRowCountsCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from Other;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(otherCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from PDB;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(PDBCount, results.getInt(1));

//			query = conn.prepareStatement("select count(*) from [PDB-GeneOntology];");
//			 results = query.executeQuery();
//			 results.next();
//			 Assert.assertEquals(PDBGeneOntologyCount, results.getInt(1));

//			query = conn.prepareStatement("select count(*) from [PDB-InterPro];");
//			 results = query.executeQuery();
//			 results.next();
//			 Assert.assertEquals(PDBInterProCount, results.getInt(1));

//			query = conn.prepareStatement("select count(*) from [PDB-Pfam];");
//			 results = query.executeQuery();
//			 results.next();
//			 Assert.assertEquals(PDBPfamCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from Pfam;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(pfamCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from Relations;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(relationsCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from Systems;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(systemsCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from UniProt;");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [UniProt-Blattner];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtBlattnerCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [UniProt-EchoBASE];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtEchoBASECount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [UniProt-EcoGene];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtEcoGeneCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [UniProt-EMBL];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtEMBLCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [UniProt-GeneOntology];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtGeneOntologyCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [UniProt-GOCount];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtGOCountCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [UniProt-InterPro];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtInterProCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from [UniProt-PDB];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtPDBCount, results.getInt(1));

			query = conn.prepareStatement("select count(*) from UniProt-Pfam];");
			 results = query.executeQuery();
			 results.next();
			 Assert.assertEquals(uniProtPfamCount, results.getInt(1));
            
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch( Exception e){
        	e.printStackTrace();
        } finally {
            try {
                results.close();
                query.close();
                conn.close();
            } catch(Exception e) {
                //reportException(e);
            } // Ignore the errors here, nothing we can do anyways.
        }
	} // end testImportCounts

	
	
	// declar method vars
	String dbUrl = "jdbc:odbc:GMB_Test";
	String dbUser = "";
	String dbPass = "";

	
}
