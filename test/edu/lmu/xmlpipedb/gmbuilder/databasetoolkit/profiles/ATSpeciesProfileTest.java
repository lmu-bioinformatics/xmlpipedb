// Created for xmlpipedb, Oct 29, 2006.
package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.TableCoordinator;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author   Jeffrey Nicholas
 * @version  $Revision$ $Date$
 */
public class ATSpeciesProfileTest {

	
	/* **** class vars **** */
	private static final Log _Log = LogFactory.getLog(ATSpeciesProfileTest.class);
//    private String _jaxbContextPath;
    private Configuration _hibernateConfiguration = GenMAPPBuilder.createHibernateConfiguration();
//	private ConfigurationEngine _configEng;
//	private File _xmlFile;
    private DatabaseProfile dp = null;
    private final int TAIR_ID_COUNT = 17739;

    
//    static final String SPECIES = "Escherichia coli";
//    static final String[] PROPER = {"UniProt", "EchoBASE", "Blattner", "EcoGene"};
//    static final String[] IMPROPER =  {"GeneOntology", "EMBL", "PDB", "Pfam", "InterPro"};
//    static final String DISPLAY_ORDER = "|S|T|Ln|Ec|Eg|Em|I|Pd|Pf|";
    
    // A. thaliana test
    static final String SPECIES = "Arabidopsis thaliana";
    static final String[] PROPER = {"UniProt", "UniGene", "TAIR"};
    static final String[] IMPROPER =  {"GeneOntology", "EMBL", "Pfam", "InterPro"};
    static final String DISPLAY_ORDER = "|S|T|A|Em|I|Pf|U|";
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
    
    ArrayList<String> relationsEntries = new ArrayList<String>(15);
    {
    	relationsEntries.add("S,A");
    	relationsEntries.add("S,I");
    	relationsEntries.add("S,Pf");
    	relationsEntries.add("S,U");
    	relationsEntries.add("S,Em");
    	relationsEntries.add("S,T");
    	relationsEntries.add("U,T");
    	relationsEntries.add("U,Em");
    	relationsEntries.add("U,Pf");
    	relationsEntries.add("U,I");
    	relationsEntries.add("U,A");
    	relationsEntries.add("A,T");
    	relationsEntries.add("A,Em");
    	relationsEntries.add("A,Pf");
    	relationsEntries.add("A,I");
    }
    
    Map<String, Integer> relationships = new HashMap<String, Integer>(12);{
    	relationships.put("UniProt-TAIR", new Integer(45)); 
    	relationships.put("UniProt-InterPro", new Integer(97));
    	relationships.put("UniProt-Pfam", new Integer(45)); 
    	relationships.put("UniProt-UniGene", new Integer(44));
    	relationships.put("UniProt-EMBL", new Integer(240)); 
    	relationships.put("UniGene-EMBL", new Integer(123)); 
    	relationships.put("UniGene-Pfam", new Integer(24)); 
    	relationships.put("UniGene-InterPro", new Integer(53));
    	relationships.put("UniGene-TAIR", new Integer(24)); 
    	relationships.put("TAIR-EMBL", new Integer(128));    
    	relationships.put("TAIR-Pfam", new Integer(26));    
    	relationships.put("TAIR-InterPro", new Integer(53));
    }
	/**
		 * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.EscherichiaColiUniProtSpeciesProfile#getRelationsTableManagerCustomizations(String, String, Map, TableManager)}.
		 * Ensure that the number and type of records returned are correct.
		 * 
		 * Compared to what??? Not sure.
		 * Based on what input??? Not sure.
		 * @throws InvalidParameterException 
		 */
	
		public void testGetRelationsTableManager() throws FileNotFoundException, InvalidParameterException {
	        Row[] rows = null;
			
			// setup environment
	        if( dp == null )
	        	dp = doSetupOfExportEnvironment();
			
			// do tests
	//      This uses SpeciesProfile
	        TableManager tmB = dp.getRelationsTableManager();
	        rows = tmB.getRows();
	        assertEquals(26, rows.length);
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
	 * @throws InvalidParameterException 
	 */
	////@Test
	public void testGetDefaultDisplayOrder() throws FileNotFoundException, InvalidParameterException {
	
		// setup environment
        if( dp == null )
        	dp = doSetupOfExportEnvironment();
        
        String defaultDisplayOrder = dp.getDefaultDisplayOrder();
        assertEquals( DISPLAY_ORDER, defaultDisplayOrder );

        // Clean-up after yourself! (or myself in this case)
        try {
			ConnectionManager.closeRelationalDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end testGetDefaultDisplayOrder...

	
	
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
	////@Test
	public void testSystemsTableOutput() throws FileNotFoundException, InvalidParameterException {
        Row[] rows = null;
        int count=0;		
		// setup environment
        if( dp == null )
        	dp = doSetupOfExportEnvironment();
		
		// do tests
        
        TableManager tmD = dp.getSystemsTableManager();
        rows = tmD.getRows();
        
        
		// Make sure the lengths match
        assertEquals(systemsTableChangesCount, rows.length);

        count=0;
        for( Row r: rows){
        	boolean hasDate = false; // indicates if the row has a non-empty date field
        	_Log.warn("\nrow #: " + ++count);
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
        		_Log.warn(s + "     " + r.getValue(s) + "  |x|   ");
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
        
        
        try {
			ConnectionManager.closeRelationalDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}// end testSystemsTableOutput...

	////@Test
		public void testRelationsTableOutput() throws FileNotFoundException, InvalidParameterException {
	        Row[] rows = null;
			
			// setup environment
	        if( dp == null)
	        	dp = doSetupOfExportEnvironment();
	        
			
			// do tests
	//      This uses SpeciesProfile
	        TableManager tmB = dp.getRelationsTableManager();
	        rows = tmB.getRows();
	        
	        // TEST 1 - Did we get the correct # of rows?
	        assertEquals(relationsEntries.size(), rows.length);

	        // TEST 2 - Do we have the right stuff in each row?
	        int count=0;
	        for( Row r: rows){
	        	_Log.warn("\nrow #: " + ++count);
	        	_Log.warn("TEST: getRelationsTableManger");
	        	
	        	/*
	        	 * It looks like this might do the trick::
	        	 * 1. Make a string with the same formatting as our relationCodes
	        	 * 		known-good values
	        	 * 2. Compare to the known-good values
	        	 * 	a. if it is there, remove it (well done!)
	        	 *  b. if what we have is not in the Map - throw an error! --assertTrue(false)
	        	 * 3. After the loop, check that relationCodes is empty (if not throw an error) 
	        	 */
	        	String comparitor = r.getValue("SystemCode") + "," + r.getValue("RelatedCode");
	        	_Log.warn("Formated Relationship Codes: [" + comparitor + "]");
	        	if( relationsEntries.contains(comparitor) )
	        		relationsEntries.remove(comparitor);
	        	else{
	        		_Log.error("Error in values returned by getRelationsTableManger.");
	        		_Log.error("The value returned: [" + comparitor + "], is not in the list of known-good values.");
	        		assertTrue(false);
	        	}
	        	
	        	/*
	        	 * This is only here, in case you want to see all the values of
	        	 * the row that is returned. In that case, just uncomment this,
	        	 * run the test and check the log file! :) -- you're welcome JN
	        	 */
//	        	while(i.hasNext()){
//	        		String s = (String)i.next();
//	        		_Log.warn(s + "     " + r.getValue(s) + "  |x|   ");
//	        	}
	
	        } // end for loop

	        // TEST 3 - check that the list "relationCodes" is NOT empty and 
	        //          fail the test, cuz we missed something
	        if( !relationsEntries.isEmpty() )
	        	assertTrue(false);        
       
	        // Clean-up after yourself! (or myself in this case)
	        try {
				ConnectionManager.closeRelationalDB();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		}// end testGetReleations...

	@Test
	public void testGetPrimarySystemTableManager() throws FileNotFoundException, InvalidParameterException {
	    Row[] rows = null;
		
		// setup environment
	    if( dp == null)
	    	dp = doSetupOfExportEnvironment();
	    
		
		// do tests
	    try {
			TableManager uniprotTM = dp.getPrimarySystemTableManager();
			rows = uniprotTM.getRows();
			TableCoordinator.exportTable(dp.getExportConnection(), uniprotTM);
			assertEquals(184, rows.length);

			// Clean-up after yourself! (or myself in this case)			
			ConnectionManager.closeRelationalDB();
		} catch (SQLException e) {
			_Log.error("An SQL Exception occured during processing. See stack trace for details.");
			e.printStackTrace();
		}
		
		
	}// end testGetReleations...


	//@Test
	public void testSystemTableOutput() throws FileNotFoundException, InvalidParameterException {
	    Row[] rows = null;
		
		// setup environment
	    if( dp == null)
	    	dp = doSetupOfExportEnvironment();
	    
		
		// do tests
	    try {
			TableManager tmF = dp.getSystemTableManager();
			rows = tmF.getRows();
			assertEquals(184, rows.length);

			// Clean-up after yourself! (or myself in this case)			
			ConnectionManager.closeRelationalDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}// end testGetReleations...



	////@Test
	public void testDefaultDisplayOrderOutput() throws FileNotFoundException, InvalidParameterException {
		// setup environment
	    if( dp == null)
	    	dp = doSetupOfExportEnvironment();
	    
		
		// do tests
        String defaultDisplayOrder = dp.getDefaultDisplayOrder();
        assertEquals( DISPLAY_ORDER, defaultDisplayOrder );

        // Clean-up after yourself! (or myself in this case)
        try {
			ConnectionManager.closeRelationalDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}// end testGetReleations...



	////@Test
	public void testRelationshipTableOutput() throws FileNotFoundException, InvalidParameterException {
        Row[] rows = null;
		
		// setup environment
        if( dp == null)
        	dp = doSetupOfExportEnvironment();
        
		
		// do tests
        try {
        	int firstCount = 0;
			List<TableManager> tmG = dp.getRelationshipTableManager();
			
			
			_Log.warn("\n" + tmG.size() + " TableManagers in List");
			
			Iterator iter = tmG.iterator();
			while( iter.hasNext() ){
				_Log.warn("\nTableManager #: " + ++firstCount);
				TableManager tm = (TableManager)iter.next();
				rows = tm.getRows();
				int tmRecordCount = rows.length;
		        for( Row r: rows){
		        	String relation = r.getValue("TABLE_NAME_COLUMN");
		        	_Log.warn("Relations in TableManager List: [" + relation + "]");
		        	if( relationships.containsKey(relation)){
		        		if( relationships.get(relation).intValue() == tmRecordCount){
		        			relationships.remove(relation);
		        			break;
		        		} else {
		        			_Log.error("Relation: [" + relation + "]: number of records did not match expected.");
		        			_Log.error("Expected: [" + relationships.get(relation).toString() + "] but found: [" + tmRecordCount + "]");
		        			assertTrue(false);
		        		}
		        	} else {
		        		_Log.error("Relation: [" + relation + "] not in list of expected relationships.");
		        		assertTrue(false);
		        	}
		        	break;
		        }
			}
			

//			iter = tmG.iterator();
//			while( iter.hasNext() ){
//				_Log.warn("\nTableManager #: " + ++firstCount);
//				TableManager tm = (TableManager)iter.next();
//				rows = tm.getRows();
//				
//				int count=0;
//		        for( Row r: rows){
//		        	_Log.warn("\nrow #: " + ++count);
//		        	_Log.warn("TEST: testRelationshipTableOutput");
//		        	Map rowMap = r.getRowAsMap();
//		        	Iterator i = rowMap.keySet().iterator();
		        	/*
		        	 * It looks like this might do the trick::
		        	 * 1. Test that the correct # of TableManagers are in the list (above)
		        	 *   a. Test that all the right relations are there.
		        	 * 2. Test that the correct # of rows are in each TableManager
		        	 * 3. Test that the correct data is in each Row of each TableManager
		        	 */
//		        	String comparitor = r.getValue("SystemCode") + "," + r.getValue("RelatedCode");
//		        	_Log.warn("Formated Relationship Codes: [" + comparitor + "]");
//		        	if( relationCodes.contains(comparitor) )
//		        		relationCodes.remove(comparitor);
//		        	else{
//		        		_Log.error("Error in values returned by getRelationsTableManger.");
//		        		_Log.error("The value returned: [" + comparitor + "], is not in the list of known-good values.");
//		        		assertTrue(false);
//		        	}
		        	
		        	/*
		        	 * This is only here, in case you want to see all the values of
		        	 * the row that is returned. In that case, just uncomment this,
		        	 * run the test and check the log file! :) -- you're welcome JN
		        	 */
//		        	while(i.hasNext()){
//		        		String s = (String)i.next();
//		        		_Log.warn(s + "     " + r.getValue(s) + "  |x|   ");
//		        	}
//
//		        } // end for loop
//			} // end while loop
			
			// Old test -- let's see if we can do better, eh?
			//assertEquals(22, tmG.size());
			
//			 Clean-up after yourself! (or myself in this case)	
			ConnectionManager.closeRelationalDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	    	    ConfigurationEngine ce = new ConfigurationEngine("./test/edu/lmu/xmlpipedb/gmbuilder/databasetoolkit/profiles/at_hibernate.properties", "");
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



	//@Test
	public void testGetSystemTableManagerCustomizations() throws FileNotFoundException, InvalidParameterException {
	    Row[] rows = null;
		
		// setup environment
	    if( dp == null)
	    	dp = doSetupOfExportEnvironment();
	    
		
		// do tests
	    try {
	    	SpeciesProfile sp = dp.getSelectedSpeciesProfile();
	    	TableManager tairTM = new TableManager(new String[][] {
					{ "ID", "VARCHAR(50) NOT NULL" }, { "Species", "MEMO" },
					{ "\"Date\"", "DATE" }, { "Remarks", "MEMO" } },
					new String[] { "ID" });
			sp.getSystemTableManagerCustomizations(tairTM, null, dp.getVersion());
			rows = tairTM.getRows();
			//TableCoordinator.exportTable(dp.getExportConnection(), uniprotTM);
			_Log.info("Number of unique TAIR IDs: " + rows.length + " Expected rows: " + TAIR_ID_COUNT);
			assertEquals(TAIR_ID_COUNT, rows.length);
	
			// Clean-up after yourself! (or myself in this case)			
			ConnectionManager.closeRelationalDB();
		} catch (SQLException e) {
			_Log.error("An SQL Exception occured during processing. See stack trace for details.");
			e.printStackTrace();
		}
		
		
	}// end testGetReleations...
	
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
