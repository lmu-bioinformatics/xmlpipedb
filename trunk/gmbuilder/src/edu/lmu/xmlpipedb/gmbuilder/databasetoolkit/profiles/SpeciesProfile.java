/********************************************************
 * Filename: SpeciesProfile.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: An instance of a subclass of SpeciesProfile
 * is an object which defines species specific customizations.  
 * SpeciesProfile outlines the required functions 
 * for any subclass of this class.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;

/**
 * @author Joey J. Barrett
 * Class: SpeciesProfile
 */
public abstract class SpeciesProfile extends Profile {

	private String customizedName = null;
	
	/**
	 * Constructor.
	 * @param name
	 * @param description
	 */
	public SpeciesProfile(String name, String description) {
		super(name, description);
	}

	/**
	 * Returns a customized species name.  Customizable
	 * in the export wizard.
	 * @param speciesCustomizedName
	 */
	public void setCustomizedName(String speciesCustomizedName) {
		customizedName = speciesCustomizedName;
	}
	
	/**
	 * Returns the species name.
	 * @return
	 */
	public String getSpeciesName() {
		if(customizedName == null) {
			return getName();
		}
		return customizedName;
	}

	/**
	 * Returns the species specific system tables.
	 * @return
	 */
	protected abstract Map<String, SystemType> getSpeciesSpecificSystemTables();

	/**
	 * Returns a table manager with the species specific
	 * relations table customizations.
	 * @param systemTable1
	 * @param systemTable2
	 * @param templateDefinedSystemToSystemCode
	 * @param tableManager
	 * @return
	 * @throws Exception
	 */
	public abstract TableManager getRelationsTableManagerCustomizations(
			String systemTable1, String systemTable2, 
			Map<String, String> templateDefinedSystemToSystemCode, 
			TableManager tableManager) throws Exception;
	
	/**
	 * Returns a TableManager with the systems table
	 * species specific changes.
	 * @param tableManager
	 * @return
	 * @throws Exception
	 */
	public abstract TableManager getSystemsTableManagerCustomizations(
			TableManager tableManager) throws Exception;
	
	/**
	 * Returns a TableManager with the species specific
	 * system tables.
	 * @param tableManager
	 * @param primarySystemTableManager
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public abstract TableManager getSystemTableManagerCustomizations(
			TableManager tableManager, 
			TableManager primarySystemTableManager, 
			Date version) throws Exception;

	/**
	 * Returns a TableManager with a species specific
	 * relationship table added.
	 * @param relationshipTable
	 * @param uniprotTableManager
	 * @param blattnerTableManager
	 * @param tableManager
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public abstract TableManager getSpeciesSpecificRelationshipTable(String relationshipTable, 
			TableManager uniprotTableManager, TableManager blattnerTableManager, TableManager tableManager) throws SQLException, Exception;


	
	/**
	 * Returns the species specific system code.
	 * @param systemCodes
	 * @param templateDefinedSystemToSystemCode
	 * @return
	 */
	public abstract List<String> getSpeciesSpecificSystemCode(List<String> systemCodes, Map<String, String> templateDefinedSystemToSystemCode);
}