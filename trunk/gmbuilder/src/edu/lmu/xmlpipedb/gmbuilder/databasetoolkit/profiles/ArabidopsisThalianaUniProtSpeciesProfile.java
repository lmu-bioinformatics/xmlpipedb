/********************************************************
 * Filename: ArabidopsisThalianaUniProtSpeciesProfile.java
 * Author: Jeffrey Nicholas
 * Program: gmBuilder
 * Description: This an instance of a Arabidopsis thaliana
 * species for the UniProt centric database.  This object
 * defines all properties for this species.
 *     
 * Revision History:
 * 20061024: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * SpeciesProfile implementation for A.thaliana.
 * 
 * @author Jeffrey Nicholas Class: ArabidopsisThalianaUniProtSpeciesProfile
 * @author Joey J. Barrett
 */
public class ArabidopsisThalianaUniProtSpeciesProfile extends UniProtSpeciesProfile {
	
	private final String SPECIES_TABLE = "TAIR";
	
	/**
	 * Creates the UniProt A.thaliana species profile. This profile defines the requirements for 
	 * an Arabidopsis thaliana species within a UniProt database.
	 */
	//FIXME: THIS CLASS IS A COPY AND MUST BE UPDATED TO BE VALID
	public ArabidopsisThalianaUniProtSpeciesProfile() {
		super("Arabidopsis thaliana", "This profile defines the requirements for "
				+ "an Arabidopsis thaliana species within a UniProt database.");
	}


	/**
	 * This method is overridden from the super class, because this species
	 * requires a species specific SPECIES_TABLE. This method is called by the Export
	 * Dialog when populating the list of Proper System Tables.
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemTables()
	 */
	@Override
	protected Map<String, SystemType> getSpeciesSpecificSystemTables() {
		Map<String, SystemType> speciesSpecificAvailableSystemTables = new HashMap<String, SystemType>();

		speciesSpecificAvailableSystemTables.put(SPECIES_TABLE, SystemType.Proper);
		return speciesSpecificAvailableSystemTables;
	}

	/**
	 * It seems that getRelationsTableManagerCustomizations is not needed for A.thaliana
	 * based on feedback from Kam.
	 * 
	 * This method will normalize the systemTables input, then pass the 
	 * normalized values to the Super class's method of the same name. This 
	 * elminates redundant logic in parent and child classes and ensures  
	 * that species not needing extra processing can choose to not override
	 * this method secure in the knowledge that their results will be 
	 * correct.
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getRelationsTableManagerCustomizations(java.lang.String,
	 *      java.lang.String, java.util.Map,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
	 */
/*	@Override
	public TableManager getRelationsTableManagerCustomizations(
			String systemTable1, String systemTable2,
			Map<String, String> templateDefinedSystemToSystemCode,
			TableManager tableManager) {
		
		
// ### create some local vars and set them
		String systemCode = null;
		String relatedCode = null;
 !!!!  
 * !!!!
 * Check here for use of OrderedLocusNames -- s/n/b used 
 * !!!!
 * !!!!
 * 
 
//		 if SystemTable1 is NOT SPECIES_TABLE, then use it :: if SystemTable1 IS the SPECIES_TABLE, call it OrderedLocusNames
		if( !systemTable1.equals(SPECIES_TABLE) ){
			systemCode = systemTable1;
		} else {
			systemCode = "OrderedLocusNames";
		}
		
//		 If SystemTable2 is NOT SPECIES_TABLE, then use it :: if SystemTable2 IS the SPECIES_TABLE, call it OrderedLocusNames
		if( !systemTable2.equals(SPECIES_TABLE) ){
			relatedCode = systemTable2;
		} else {
			relatedCode = "OrderedLocusNames";
		}
// ### local vars finished
		
		// Call the super class's method, now that SPECIES_TABLE specific normalization
		// has been done
		tableManager = super.getRelationsTableManagerCustomizations(systemCode, relatedCode, templateDefinedSystemToSystemCode, tableManager);

		
		return tableManager;
	}*/

	/**
	 * Add 2 SPECIES_TABLE specific items to the tableManager, then call the
	 * super class to add the items all species will need.
	 * 
	 * Above is probably wrong, correct is:
	 * Add the TAIR entry that is needed for A. thaliana, along with the 
	 * correct code "A"
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemsTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      DatabaseProfile)
	 */
	@Override
	public TableManager getSystemsTableManagerCustomizations(
			TableManager tableManager, DatabaseProfile dbProfile) {
	
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", "A" }, { "System", SPECIES_TABLE },
				{ "\"Date\"", GenMAPPBuilderUtilities
							.getSystemsDateString(dbProfile.version) }
				});
		
//		tableManager.submit("Systems", QueryType.update, new String[][] {
//				{ "SystemCode", "Ln" }, { "SystemName", SPECIES_TABLE } });
		
		// JN - the only reason this is last is that it was like that from
		// the start. Order may not matter, but I chose not to mess with it.
		tableManager = super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
		
		return tableManager;
	}

	/**
	 * This method calls a helper method in the super class, passing the species
	 * specific table name to be used.
	 * @throws InvalidParameterException 
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      java.util.Date)
	 */
	@Override
	public TableManager getSystemTableManagerCustomizations(
			TableManager tableManager, TableManager primarySystemTableManager,
			Date version) throws SQLException, InvalidParameterException {
		
    	ArrayList<String> comparisonList = new ArrayList<String>(2);
    	comparisonList.add("ORF");
		
		tableManager = super.systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, SPECIES_TABLE, comparisonList);
		

		return tableManager;
	}

	/**
	 * This method contains SPECIES_TABLE specific processing.
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificRelationshipTable(java.lang.String,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
	 */
	@Override
	//FIXME: Change all these signatures to take an ExportObject and only an ExportObject
	public TableManager getSpeciesSpecificRelationshipTable(
			String relationshipTable, TableManager primarySystemTableManager,
			TableManager systemTableManager, TableManager tableManager)
			throws SQLException {
		
		tableManager = super.speciesSpecificRelationshipTableHelper(relationshipTable, primarySystemTableManager, systemTableManager, tableManager, SPECIES_TABLE, "Bridge", "S");
		
		return tableManager;
	}

} // end class

