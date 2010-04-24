// Created for xmlpipedb, Oct 29, 2006.
package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import edu.lmu.xmlpipedb.gmbuilder.GenMAPPBuilder;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author   Jeffrey Nicholas
 * @version  $Revision$ $Date$
 */
public class GenMAPPBuilderEscherichiaColiSpeciesProfileTest {

	
	/* **** class vars **** */
	private static final Log _Log = LogFactory.getLog(ExportToGenMAPP.class);
//    private String _jaxbContextPath;
    private Configuration _hibernateConfiguration = GenMAPPBuilder.createHibernateConfiguration();
//	private ConfigurationEngine _configEng;
//	private File _xmlFile;

    
    static final String SPECIES = "Escherichia coli";
    static final String[] PROPER = {"UniProt", "EchoBASE", "Blattner", "EcoGene"};
    static final String[] IMPROPER =  {"GeneOntology", "EMBL", "PDB", "Pfam", "InterPro"};
    static final String DISPLAY_ORDER = "|S|T|Ln|Ec|Eg|Em|I|Pd|Pf|";
    
    // A. thaliana test
//    static final String SPECIES = "Arabidopsis thaliana";
//    static final String[] PROPER = {"UniProt", "UniGene", "TAIR"};
//    static final String[] IMPROPER =  {"GeneOntology", "EMBL", "Pfam", "InterPro"};
//    static final String DISPLAY_ORDER = "|S|T|Ln|Em|I|Pf|U|";
    static final int systemsTableChangesCount = 7;
    ArrayList<String> systemsEntries = new ArrayList<String>(7);
    {
	    systemsEntries.add("T");
	    systemsEntries.add("I");
	    systemsEntries.add("S");
	    systemsEntries.add("A");
	    systemsEntries.add("U");
	    systemsEntries.add("Em");
    	systemsEntries.add("Pf");
    }
    
    Hashtable<String,String> relationCodes;
    Hashtable<String,String> relationTables;

	/**
	 * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.EscherichiaColiUniProtSpeciesProfile#getRelationsTableManagerCustomizations(String, String, Map, TableManager)}.
	 * Ensure that the number and type of records returned are correct.
	 * 
	 * Compared to what??? Not sure.
	 * Based on what input??? Not sure.
	 * @throws InvalidParameterException 
	 */
    @Test
	public void testGetRelationsTableManager() throws FileNotFoundException, InvalidParameterException {
    	//getComparisonData();
        Row[] rows = null;
		
		// setup environment
		DatabaseProfile dp = doSetupOfExportEnvironment();
		
		// do tests
//      This uses SpeciesProfile
        TableManager tmB = dp.getRelationsTableManager();
        rows = tmB.getRows();
        
        // first check: did we get the right number of records
        assertEquals(relationCodes.size(), rows.length);
        
        int count=0;
        for( Row r: rows){
        	System.out.println("\nrow #: " + ++count);
        	Map rowMap = r.getRowAsMap();
        	
        	Iterator i = rowMap.keySet().iterator();
        	while(i.hasNext()){
        		String s = (String)i.next();
        		System.out.print(s + "     " + r.getValue(s) + "  |x|   ");
        	}

        }
        
        TableManager tmD = dp.getSystemsTableManager();
        rows = tmD.getRows();
        assertEquals(9, rows.length);
        count=0;
        for( Row r: rows){
        	System.out.println("\nrow #: " + ++count);
        	Map rowMap = r.getRowAsMap();
        	
        	Iterator i = rowMap.keySet().iterator();
        	while(i.hasNext()){
        		String s = (String)i.next();
        		System.out.print(s + "     " + r.getValue(s) + "  |x|   ");
        	}
        }
        
        try {
			TableManager tmF = dp.getSystemTableManager();
			rows = tmF.getRows();
			assertEquals(184, rows.length);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        try {
			List<TableManager> tmG = dp.getRelationshipTableManager();
			assertEquals(22, tmG.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String defaultDisplayOrder = dp.getDefaultDisplayOrder();
        assertEquals( DISPLAY_ORDER, defaultDisplayOrder );
        
	}// end testGetReleations...

	
	
	/**
	 * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.EscherichiaColiUniProtSpeciesProfile#getRelationsTableManagerCustomizations(String, String, Map, TableManager)}.
	 * Ensure that the number and type of records returned are correct.
	 * 
	 * Compared to what??? Not sure.
	 * Based on what input??? Not sure.
	 * 
	 * This is setup to run for A. thaliana.
	 * 
	 * @throws InvalidParameterException 
	 */
	//Test
	public void testSystemsTableOutput() throws FileNotFoundException, InvalidParameterException {
        Row[] rows = null;
        int count=0;		
		// setup environment
		DatabaseProfile dp = doSetupOfExportEnvironment();
		
		// do tests
        
        TableManager tmD = dp.getSystemsTableManager();
        rows = tmD.getRows();
        assertEquals(systemsTableChangesCount, rows.length);
        count=0;
        for( Row r: rows){
        	boolean hasDate = false; // indicates if the row has a non-empty date field
        	_Log.debug("\nrow #: " + ++count);
        	Map rowMap = r.getRowAsMap();
        	Iterator i = rowMap.keySet().iterator();

        	/* Here's the general logic of the next bit: 
        	 * 1. Find the "SystemCode" entry for each record
        	 * 2. If it is in the list of "systemEntries" then:
        	 * 	a. remove it (so it can't be used again if there is a repeat
        	 *  b. break out of the while (so we move on to the next record
        	 * 3. If NOT in the list, then assertTrue(false) -- basically throw an error
        	 */
        	while(i.hasNext()){
        		String s = (String)i.next();
        		_Log.debug(s + "     " + r.getValue(s) + "  |x|   ");
        		if(s.equalsIgnoreCase("\"Date\"")){
        			if(r.getValue(s)!= null && !r.getValue(s).equals(""))
        				hasDate = true;
        		}
        		
        		if(s.equals("SystemCode")){
        			if( systemsEntries.contains(r.getValue(s)) && hasDate ){
        				systemsEntries.remove(r.getValue(s));
        				break;
        			}
        			else
        				assertTrue(false);
        		}
        	}
        }
        // check that the list "systemsEntries" is NOT empty and fail the test, cuz we missed something
        if( !systemsEntries.isEmpty() )
        	assertTrue(false);
        
//TODO: Figure-out what this value is supposed to be / what are we measuring here        
//        try {
//			List<TableManager> tmG = dp.getRelationshipTableManager();
//			assertEquals(22, tmG.size());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        String defaultDisplayOrder = dp.getDefaultDisplayOrder();
        assertEquals( DISPLAY_ORDER, defaultDisplayOrder );
        
        try {
			ConnectionManager.closeRelationalDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}// end testSystemsTableOutput...

	//Test
	public void testRelationsTableOutput() throws FileNotFoundException, InvalidParameterException {
        Row[] rows = null;
		
		// setup environment
		DatabaseProfile dp = doSetupOfExportEnvironment();
		
		// do tests
//      This uses SpeciesProfile
        TableManager tmB = dp.getRelationsTableManager();
        rows = tmB.getRows();
        //FIXME: correct size of assertion
        assertEquals(relationCodes.size(), rows.length);
        int count=0;
        for( Row r: rows){
        	System.out.println("\nrow #: " + ++count);
        	Map rowMap = r.getRowAsMap();
        	
        	Iterator i = rowMap.keySet().iterator();
        	while(i.hasNext()){
        		String s = (String)i.next();
        		_Log.debug(s + "     " + r.getValue(s) + "  |x|   ");
//        		if(s.equalsIgnoreCase("\"Date\"")){
//        			//if(r.getValue(s)!= null && !r.getValue(s).equals(""))
//        				//hasDate = true;
//        		}
//        		
//        		if(s.equals("SystemCode")){
//        			if( systemsEntries.contains(r.getValue(s))  ){
//        				systemsEntries.remove(r.getValue(s));
//        				break;
//        			}
//        			else
//        				assertTrue(false);
//        		}
        	}

        }
        
        TableManager tmD = dp.getSystemsTableManager();
        rows = tmD.getRows();
        assertEquals(9, rows.length);
        count=0;
        for( Row r: rows){
        	System.out.println("\nrow #: " + ++count);
        	Map rowMap = r.getRowAsMap();
        	
        	Iterator i = rowMap.keySet().iterator();
        	while(i.hasNext()){
        		String s = (String)i.next();
        		System.out.print(s + "     " + r.getValue(s) + "  |x|   ");
        	}
        }
        
        try {
			TableManager tmF = dp.getSystemTableManager();
			rows = tmF.getRows();
			assertEquals(184, rows.length);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        try {
			List<TableManager> tmG = dp.getRelationshipTableManager();
			assertEquals(22, tmG.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String defaultDisplayOrder = dp.getDefaultDisplayOrder();
        assertEquals( "|S|T|Ln|Ec|Eg|Em|I|Pd|Pf|", defaultDisplayOrder );
        
	}// end testGetReleations...

	/**
	 * The purpose of this method is to create and pre-populate a DbProfile
	 * so that any subsequent tests can use this without duplicating the code.
	 * 
	 * Steps:
	 * 1. Get DatabaseProfile
	 * 2. Get SpeciesProfile
	 * 3. Put SpeciesProfile into DatabaseProfile
	 * 
	 * 
	 * @return DatabaseProfile
	 */
	private DatabaseProfile doSetupOfExportEnvironment(){
		if(_hibernateConfiguration == null) getHibernateConfig();
		DatabaseProfile result = null;
		// do stuff
			
			try {
//				Copied from GenMAPPBuilder.java
				ExportToGenMAPP.init(_hibernateConfiguration);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			// Copied from ExportPanel1.init()
			// The method ExportToGenMAPP.getAvailableDatabaseProfiles() is STATIC
		 	for (DatabaseProfile profile : ExportToGenMAPP.getAvailableDatabaseProfiles()) {
	            // Find the first occurance that is UniProt, grab it, and jump out
		 		if( profile.toString().equalsIgnoreCase("org.uniprot.uniprot.Uniprot")){
		 			// store the uniprot db profile in the result
		 			result = profile;
		 			
		 			for (SpeciesProfile speciesProfile : profile.getSpeciesProfilesFound()) {
		 				// find the species we want and put it in the result db profile
		                if( speciesProfile.getName().equals(SPECIES)){
		                	result.setSelectedSpeciesProfile(speciesProfile);
		                	break;
		                } // end if
		            } // end inner for
	            	
	            	break;
	            } // end if
	        } // end outer for
		 	
		 	// populate dbProfile with everything it needs
		       try {
		    	    // from ExportPanel1
					result.setOwner("Unit Test -- JN");
		
					result.setVersion(new SimpleDateFormat("MM/dd/yyyy")
							.parse("1/1/1776"));
		
					result.setMODSystem("UniProt");
					//// set the species name
					//!! not needed since the species was set above
					/// result.setSpeciesName("Escherichia Coli");
					result.setModify(new SimpleDateFormat("MM/dd/yyyy")
							.parse("1/1/1888"));
					result.setNotes("This test better work");
		
					// from ExportPanel2
					result.setDatabaseProperties(
							"./unitTest.gdb",
							null); // we are not doing GO, so no need for a GO file
					
					// from ExportPanel3
					result.setTableProperties(PROPER, IMPROPER);
					
					// from ExportPanel4
					List<String> relationshipTables = new ArrayList<String>();
					for (String relationshipTable : result.getAvailableRelationshipTables()) {
			            relationshipTables.add(relationshipTable);
			        }
					result.setRelationshipTableProperties(relationshipTables.toArray(new String[0]));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
		       
		       // Note To Self: This call will move up to the calling method...
		       //ExportToGenMAPP.setDatabaseProfile(result);
		
		return result;
	}// end doSetup...
	
	private void getHibernateConfig(){
	       try {
	    	    ConfigurationEngine ce = new ConfigurationEngine("./test/edu/lmu/xmlpipedb/gmbuilder/databasetoolkit/profiles/hibernate.properties", "");
	            _hibernateConfiguration = ce.getHibernateConfiguration();
	            
	            // TODO: Find a way to make this independent of the current
	            // directory, or at least ensure that the working directory of this
	            // program is indeed the top-level directory of the distribution.
	            _hibernateConfiguration.addJar(new File("lib/uniprotdb.jar"));
	            _hibernateConfiguration.addJar(new File("lib/godb.jar"));
	        } catch(Exception exc) {
	            // This may be a normal occurrence (particularly when starting up
	            // for the first time), so we don't do anything in this case.
	        }
	}
	
	private void getComparisonData(){
		// initialize the hashes that hold the data
		relationCodes  = new Hashtable<String,String>();
		relationTables = new Hashtable<String,String>();
				
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			// connect to the access database with our known-good data
		    StringBuffer databaseConnectionString = new StringBuffer("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=");
		    databaseConnectionString.append("test/edu/lmu/xmlpipedb/gmbuilder/resource/dbfiles/GeneDBTmpl.mdb");
		    databaseConnectionString.append(";DriverID=22;READONLY=false}"); 
		    Connection compDataConn = DriverManager.getConnection(databaseConnectionString.toString(), "", "");
		    
		    // execute the query
            PreparedStatement ps = compDataConn.prepareStatement("select SystemCode, RelatedCode, Relation, Type from Relations");
            ResultSet result = ps.executeQuery();

            // put the results into the hashes
            while (result.next()) {
                String systemCode = result.getString("SystemCode").trim();
                String relatedCode = result.getString("RelatedCode").trim();
                String relation = result.getString("Relation");
                String type =  result.getString("Type");
                relationCodes.put(systemCode, relatedCode);
                relationTables.put(relation, type );
            }
            
            // cleanup after ourselves
            result.close();
            ps.close();
            compDataConn.close();
        } catch(SQLException unhandled) {
            unhandled.printStackTrace();
        } catch(Exception unhandled) {
            unhandled.printStackTrace();
        }
	}
	
    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities#getDefaultGDBFilename(java.lang.String, java.util.Date)}.
     * Ensure that the GMBUtilities getDefaultGDBFilename method works
     * correctly. Specifically:
     * - given a species and date, a correct file name is returned
     * - null is not accepted for the species name
     * - null is not accepted for the date
     */
/*    @Test
    public void testGetDefaultGDBFilename() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1999);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DATE, 14);
        assertEquals("Ec-Std_19990214.gdb", GenMAPPBuilderUtilities.getDefaultGDBFilename("e. coli", c.getTime()));
        c.set(Calendar.YEAR, 2006);
        c.set(Calendar.MONTH, Calendar.JUNE);
        c.set(Calendar.DATE, 6);
        assertEquals("Pp-Std_20060606.gdb", GenMAPPBuilderUtilities.getDefaultGDBFilename("pseudomonas putida", c.getTime()));
        assertEquals("-Std_20060606.gdb", GenMAPPBuilderUtilities.getDefaultGDBFilename("", c.getTime()));
        try {
            GenMAPPBuilderUtilities.getDefaultGDBFilename(null, c.getTime());
            fail("getDefaultGDBFilename() should not accept nulls");
        } catch(NullPointerException npexc) {
            // This is what we expect.
        }
        try {
            GenMAPPBuilderUtilities.getDefaultGDBFilename("escherichia coli", null);
            fail("getDefaultGDBFilename() should not accept nulls");
        } catch(NullPointerException npexc) {
            // This is what we expect.
        }
    }*/

    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities#getGenusName(java.lang.String)}.
     * Ensure that the GMBUtilities getGenusName method works
     * correctly. Specifically:
     * - empty string returns empty string (not a failure)
     * - given a full genus and species name, the correct genus name is returned
     * - null is not accepted as input
     */
/*    @Test
    public void testGetGenusName() {
        assertEquals("", GenMAPPBuilderUtilities.getGenusName(""));
        assertEquals("escherichia", GenMAPPBuilderUtilities.getGenusName("escherichia coli"));
        assertEquals("homo", GenMAPPBuilderUtilities.getGenusName("homo sapiens superior"));
        try {
            GenMAPPBuilderUtilities.getGenusName(null);
            fail("getGenusName() doesn't accept nulls");
        } catch(NullPointerException npexc) {
            // This is what we expect.
        }
    }*/

    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities#getSpeciesName(java.lang.String)}.
     * Ensure that the GMBUtilities getSpeciesName method works
     * correctly. Specifically:
     * - empty string returns empty string (not a failure)
     * - given a genus only, empty string is returned for the species
     * - given a full genus and species name, the correct species name is returned
     * - null is not accepted as input
     */
/*    @Test
    public void testGetSpeciesName() {
        assertEquals("", GenMAPPBuilderUtilities.getSpeciesName(""));
        assertEquals("", GenMAPPBuilderUtilities.getSpeciesName("thisOneJustHasAGenus"));
        assertEquals("coli", GenMAPPBuilderUtilities.getSpeciesName("escherichia coli"));
        assertEquals("sapiens", GenMAPPBuilderUtilities.getSpeciesName("homo sapiens superior"));
        try {
            GenMAPPBuilderUtilities.getSpeciesName(null);
            fail("getSpeciesName() doesn't accept nulls");
        } catch(NullPointerException npexc) {
            // This is what we expect.
        }
    }*/

    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities#parseRelationshipTableName(java.lang.String)}.
     * 
     * I'll leave the details to Jeffrey  :)
     */
/*    @Test
    public void testParseRelationshipTableName() {
        SystemTablePair stp = GenMAPPBuilderUtilities.parseRelationshipTableName("UniProt-EMBL");
        assertEquals("UniProt", stp.systemTable1);
        assertEquals("EMBL", stp.systemTable2);
        
        try {
            stp = GenMAPPBuilderUtilities.parseRelationshipTableName("NotAValidRelationshipTableName");
            fail("parseRelationshipTableName() assumes a valid relationship table name");
        } catch(ArrayIndexOutOfBoundsException aioobexc) {
            // This is what we expect.
        }
        
        try {
            stp = GenMAPPBuilderUtilities.parseRelationshipTableName("");
            fail("parseRelationshipTableName() assumes a valid relationship table name");
        } catch(ArrayIndexOutOfBoundsException aioobexc) {
            // This is what we expect.
        }
        
        try {
            stp = GenMAPPBuilderUtilities.parseRelationshipTableName(null);
            fail("parseRelationshipTableName() assumes a valid relationship table name");
        } catch(NullPointerException npexc) {
            // This is what we expect.
        }
    }*/
    
    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities#getSystemsDateString(java.util.Date)}.
     */
/*    @Test
    public void testGetSystemsDateString() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1999);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DATE, 14);
        assertEquals("02/14/1999", GenMAPPBuilderUtilities.getSystemsDateString(c.getTime()));
        c.set(Calendar.YEAR, 2006);
        c.set(Calendar.MONTH, Calendar.JUNE);
        c.set(Calendar.DATE, 6);
        assertEquals("06/06/2006", GenMAPPBuilderUtilities.getSystemsDateString(c.getTime()));
    }*/

    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities#straightToCurly(String)}.
     */
/*    @Test
    public void testGetStraightToCurly() {
        assertNull(GenMAPPBuilderUtilities.straightToCurly(null));
        assertEquals("no apostrophes here", GenMAPPBuilderUtilities.straightToCurly("no apostrophes here"));
        assertEquals("\u2019apostrophe in the beginning", GenMAPPBuilderUtilities.straightToCurly("'apostrophe in the beginning"));
        assertEquals("two \u2019apostrophes\u2019 here", GenMAPPBuilderUtilities.straightToCurly("two 'apostrophes' here"));
        assertEquals("more than \u2019two\u2019 apos\u2019tro\u2019phe\u2019s", GenMAPPBuilderUtilities.straightToCurly("more than 'two' apos'tro'phe's"));
    }*/
} // end class
