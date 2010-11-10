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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * Generic species profile regardless of data source. This class must
 *  be extended by data source, for example UniProt or TIGR. That class
 *  must then be extended by species,if any species specific processing
 *  needs to occur.
 *
 * @author Joey J. Barrett Class: SpeciesProfile
 * @author Jeffrey Nicholas
 */
public abstract class SpeciesProfile extends Profile {

	private String customizedName = null;
	private int taxonID;

	/**
	 * Constructor.
	 *
	 * @param name
	 * @param description
	 */
	public SpeciesProfile(String name, String description) {
		super(name, description);
	}

	/**
	 * Alternate constructor using name, taxonID, and description
	 *
	 * @param name
	 * @param taxonID
	 * @param description
	 */

	public SpeciesProfile(String name, int taxonID, String description) {
		super(name, description);
		this.taxonID = taxonID;
	}

	/**
	 * Returns a customized species name. Customizable in the export wizard.
	 *
	 * @param speciesCustomizedName
	 */
	public void setCustomizedName(String speciesCustomizedName) {
		customizedName = speciesCustomizedName;
	}

	/**
	 * Returns the species name.
	 *
	 * @return
	 */
	public String getSpeciesName() {
		if (customizedName == null) {
			return getName();
		}
		return customizedName;
	}

	/**
	 * Returns the species specific system tables.
	 *
	 * @return
	 */
	protected abstract Map<String, SystemType> getSpeciesSpecificSystemTables();

	/**
	 * Returns a table manager with the species specific relations table
	 * customizations.
	 *
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
			TableManager tableManager);

	/**
	 * Returns a TableManager with the systems table species specific changes.
	 *
	 * @param tableManager
	 * @param dbProfile
	 *            TODO
	 * @return
	 * @throws Exception
	 */
	public abstract TableManager getSystemsTableManagerCustomizations(
			TableManager tableManager, DatabaseProfile dbProfile);

	/**
	 * Returns a TableManager with the species specific system tables.
	 *
	 * @param tableManager
	 * @param primarySystemTableManager
	 * @param version
	 * @return
	 * @throws InvalidParameterException
	 * @throws Exception
	 */
	public abstract TableManager getSystemTableManagerCustomizations(
			TableManager tableManager, TableManager primarySystemTableManager,
			Date version) throws SQLException, InvalidParameterException;

	/**
	 * Returns a TableManager with the species specific primary table(s).
	 * This is needed, since data for some species is not normalized. This
	 * method allows for overriding the behavior of the data source
	 * DatabaseProfile's (i.e. UniProtDatabaseProfile)
	 * getPrimarySystemTableManger method.
	 * @param version
	 *
	 * @return
	 * @throws SQLException
	 */
	public abstract TableManager getPrimarySystemTableManagerCustomizations(Date version)
			throws SQLException;

	/**
	 * Returns a TableManager with a species specific relationship table added.
	 *
	 * @param relationshipTable
	 * @param uniprotTableManager
	 * @param blattnerTableManager
	 * @param tableManager
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public abstract TableManager getSpeciesSpecificRelationshipTable(
			String relationshipTable, TableManager uniprotTableManager,
			TableManager blattnerTableManager, TableManager tableManager)
			throws SQLException;
//FIXME: In this class it should not take a blattnerTableManager or a uniprotTableManager, since that is not generic

	/**
	 * Returns the species specific system code.
	 *
	 * @param systemCodes
	 * @param templateDefinedSystemToSystemCode
	 * @return
	 */
	public abstract List<String> getSpeciesSpecificSystemCode(
			List<String> systemCodes,
			Map<String, String> templateDefinedSystemToSystemCode);

	/**
	 * Returns any other species-specific tables that fit neither the mold of
	 * a standard system table, the primary system table, or any other prior
	 * tables.
	 */
	public List<TableManager> getSpeciesSpecificCustomTables(Date version) throws SQLException {
	    return new ArrayList<TableManager>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return taxonID + " " + this.getName();
	}
	
	public int getTaxon() {
		return taxonID;
	}

}
