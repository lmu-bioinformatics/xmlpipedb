/********************************************************
 * Filename: UniProtDatabaseProfile.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: This class is a sub-class of
 * DatabaseProfile and defines the UniProt centric
 * customizations associated with the databse.
 *
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.util.CaseInsensitiveStringComparator;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities.SystemTablePair;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author Joey J. Barrett Class: UniProtDatabaseProfile
 * @author Jeffrey Nicholas
 */
public class UniProtDatabaseProfile extends DatabaseProfile {

	private List<String> produceLastRelationshipTables = new ArrayList<String>();

	/**
	 * Constuctor.
	 */
	public UniProtDatabaseProfile() {

		// Why are we creating all objects up front, instead of
		// waiting for the user to choose one? We need to abstract just the
		// profile name out and access it later.
		super("org.uniprot.uniprot.Uniprot",
				"This profile defines the requirements "
						+ "for any UniProt-centric Gene Database.",
				new SpeciesProfile[] {
						new EscherichiaColiUniProtSpeciesProfile(),
						new ArabidopsisThalianaUniProtSpeciesProfile(),
						new PlasmodiumFalciparumUniProtSpeciesProfile(),
						new VibrioCholeraeUniprotSpeciesProfile(),
						new SaccharomycesCerevisiaeUniProtSpeciesProfile(),
						new MycobacteriumTuberculosisUniProtSpeciesProfile(),
						new PseudomonasAeruginosaUniProtSpeciesProfile(), 
						new StaphylococcusAureusMRSA252UniProtSpeciesProfile(),
						new MycobacteriumSmegmatisUniProtSpeciesProfile(),
						new HelicobacterPyloriUniProtSpeciesProfile(),
						new SalmonellaTyphimuriumUniProtSpeciesProfile()});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#
	 * isAvailable(java.sql.Connection)
	 */
	@Override
	public boolean isAvailable(Connection connection) throws SQLException {

		PreparedStatement ps = connection
				.prepareStatement("select hjtype from uniprottype");
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			if (result.getString("hjtype").equals(getName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#
	 * checkRequirements(java.sql.Connection)
	 */
	@Override
	public void checkRequirements(Connection connection) throws SQLException {

		// Get the species(s) contained in the database.
		PreparedStatement ps = connection
		        .prepareStatement("select distinct(id) from dbreferencetype where type = 'NCBI Taxonomy'");
		ResultSet result = ps.executeQuery();

		speciesProfilesFound.clear();
		while (result.next()) {

			int speciesTaxon = result.getInt("id");
			boolean speciesProfileFound = false;

			// Add the species found to the available species profiles.
			for (SpeciesProfile speciesProfile : speciesProfilesAvailable) {
				if (speciesTaxon == speciesProfile.getTaxon()) {
					speciesProfilesFound.add(speciesProfile);
					speciesProfileFound = true;
					break;
				}
			}

			if (!speciesProfileFound) {
				speciesProfilesFound
						.add(new UniProtSpeciesProfile(
								speciesTaxon,
								"This profile defines the requirements for "
										+ "a custom species profile within a UniProt database."));
			}
		}

		// Get the system(s) contained in the database.
		ps = connection
				.prepareStatement("select distinct(type) from dbreferencetype");
		result = ps.executeQuery();
		while (result.next()) {
			systemTablesFound.add(result.getString("type"));
		}

		result.close();
		ps.close();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#
	 * getMODSystem()
	 */
	@Override
	public String getMODSystem() {
		return "UniProt";
	}

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getDefaultDisplayOrder()
	 */
	@Override
	public String getDefaultDisplayOrder() {
		List<String> systemCodes = new ArrayList<String>();
		_Log.debug("System Codes: [" + systemCodes + "]");
		Map<String, SystemType> uniprotSpecificSystemTables = getDatabaseSpecificSystemTables();
		Map<String, SystemType> speciesSpecificSystemTables = speciesProfile
				.getSpeciesSpecificSystemTables();
		for (Entry<String, SystemType> systemTable : systemTables.entrySet()) {
			if (!uniprotSpecificSystemTables.containsKey(systemTable.getKey())
					&& !speciesSpecificSystemTables.containsKey(systemTable
							.getKey())) {
				_Log.info("System Table: ["
						+ systemTable.getKey()
						+ "] System Code: ["
						+ templateDefinedSystemToSystemCode.get(systemTable
								.getKey()) + "]");
				_Log.debug("System Codes: [" + systemCodes + "]");
				systemCodes.add(templateDefinedSystemToSystemCode
						.get(systemTable.getKey()));
				_Log.debug("System Codes: [" + systemCodes + "]");
			}
		}

		switch (displayOrderPreset) {
		case alphabetical:
			Collections
					.sort(systemCodes, new CaseInsensitiveStringComparator());
			_Log.debug("System Codes: [" + systemCodes + "]");
			break;
		}

		systemCodes.add(0, templateDefinedSystemToSystemCode.get("UniProt"));
		_Log.debug("System Codes: [" + systemCodes + "]");
		systemCodes.add(1, templateDefinedSystemToSystemCode
				.get("GeneOntology"));
		_Log.debug("System Codes: [" + systemCodes + "]");

		systemCodes = speciesProfile.getSpeciesSpecificSystemCode(systemCodes,
				templateDefinedSystemToSystemCode);
		_Log.info("System Codes: [" + systemCodes + "]");

		StringBuffer defaultDisplayOrder = new StringBuffer();
		for (String systemCode : systemCodes) {
			defaultDisplayOrder.append("|").append(systemCode);
			_Log.debug("Default Display Order: [" + defaultDisplayOrder + "]");
		}
		defaultDisplayOrder.append("|");
		_Log.info("Default Display Order: [" + defaultDisplayOrder + "]");
		return defaultDisplayOrder.toString();
	}

	/**
	 * This method adds Uniprot and GeneOntology to the list of "Uniprot"
	 * tables. It also adds whatever has been defined as a species specific
	 * table in the SpeciesProfile (e.g. TAIR)
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#
	 * getDatabaseSpecificSystemTables()
	 */
	@Override
	public Map<String, SystemType> getDatabaseSpecificSystemTables() {
		Map<String, SystemType> uniprotSpecificSystemTables = new HashMap<String, SystemType>();
		uniprotSpecificSystemTables.put("UniProt", SystemType.Primary);
		uniprotSpecificSystemTables.put("GeneOntology", SystemType.Improper);
		// This puts any species System tables (e.g. TAIR for A. thaliana) into
		// the list
		uniprotSpecificSystemTables.putAll(speciesProfile
				.getSpeciesSpecificSystemTables());

		return uniprotSpecificSystemTables;
	}

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getSystemsTableManager()
	 */
	public @Override
	TableManager getSystemsTableManager() {
		TableManager tableManager;

		tableManager = new TableManager(null, new String[] { "SystemCode" });

		/*
		 * What this is doing is creating a number of update queries that will
		 * update the Systems table for each entry in the systemTables Map. It
		 * will update the entry with a date/time. GenMAPP uses the fact that
		 * the record has a date/time to know that there is a table for that
		 * system in the database (yes this is stupid, but we're not here to
		 * change GenMAPP).
		 *
		 * Note: in the original, this actually only does this for systems that
		 * are not species specific. (hence the "!").
		 *
		 * With the if-block commented out the date update will be applied to
		 * all entries. Additional processing is done below.
		 */
		for (Entry<String, SystemType> systemTable : systemTables.entrySet()) {
			_Log.debug("Adding system table for " + systemTable.getKey());
			if (templateDefinedSystemToSystemCode.get(systemTable.getKey()) == null) {
				_Log.warn("No system code found for " + systemTable.getKey());
			}
			tableManager.submit("Systems", QueryType.update, new String[][] {
					{
							"SystemCode",
							templateDefinedSystemToSystemCode.get(systemTable
									.getKey()) },
					{
							"\"Date\"",
							GenMAPPBuilderUtilities
									.getSystemsDateString(version) } });
			// }
		}

		// The "Other" table also needs a date: the date of export.
		tableManager
				.submit(
						"Systems",
						QueryType.update,
						new String[][] {
								{
										"SystemCode",
										templateDefinedSystemToSystemCode
												.get("Other") },
								{
										"\"Date\"",
										GenMAPPBuilderUtilities
												.getSystemsDateString(new Date()) } });

		/*
		 * Next we want to ensure that this record get it's "Columns" column
		 * updated with the value here.
		 */
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode",
						templateDefinedSystemToSystemCode.get("InterPro") },
				{ "Columns", "ID|" } });

		// RB - need to make aware of List<SpeciesProfile>
		// tableManager = speciesProfile.getSystemsTableManagerCustomizations( tableManager, this );
		for( SpeciesProfile speciesProfile : selectedSpeciesProfiles ) {
		    tableManager = speciesProfile.getSystemsTableManagerCustomizations( tableManager, this );
		}
		return tableManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#
	 * getPrimarySystemTableManager()
	 */
	public @Override
	TableManager getPrimarySystemTableManager() throws SQLException {
		if (primarySystemTableManager != null) {
			return primarySystemTableManager;
		}
		TableManager tableManager = null;
		// First try calling the species profile's version (this accomodates
		// A.thaliana for now, perhaps others later).
		// If it returns a TableManger, we're done. Otherwise, keep chugging
		// along.
		
		// RB - using first SpeciesProfile in the List of SpeciesProfiles: selectedSpeciesProfiles.get(0) 
		tableManager = selectedSpeciesProfiles.get(0)
				.getPrimarySystemTableManagerCustomizations(version);
		//tableManager = speciesProfile.getPrimarySystemTableManagerCustomizations(version);
		if (tableManager != null) {
			primarySystemTableManager = tableManager;
			return tableManager;
		}

		int recordCounter = 0;
		String accessionSQL = "SELECT entrytype_accession.entrytype_accession_hjid, entrytype_accession.hjvalue " +
			"FROM entrytype_accession " + "INNER JOIN entrytype ON (entrytype_accession.entrytype_accession_hjid = entrytype.hjid) " +
			"INNER JOIN organismtype ON (entrytype.organism = organismtype.hjid) " +
			"INNER JOIN dbreferencetype ON (organismtype.hjid = dbreferencetype.organismtype_dbreference_hjid) " +
			"WHERE entrytype_accession_hjindex = 0 " +
			"AND dbreferencetype.type LIKE '%NCBI Taxonomy%' " +
			"AND dbreferencetype.id = ?";
		String nameSQL = "SELECT hjvalue FROM entrytype_name WHERE entrytype_name_hjid = ?";
		String geneSQL = "SELECT value, type FROM genenametype INNER JOIN entrytype_genetype ON (entrytype_genetype_name_hjid = entrytype_genetype.hjid) WHERE entrytype_gene_hjid = ?";

		tableManager = new TableManager(new String[][] {
				{ "ID", "VARCHAR(50) NOT NULL" },
				{ "EntryName", "VARCHAR(50) NOT NULL" },
				{ "GeneName", "VARCHAR(50)" }, { "ProteinName", "MEMO" },
				{ "Function", "MEMO" }, { "Species", "MEMO" },
				{ "\"Date\"", "DATE" }, { "Remarks", "MEMO" } },
				new String[] { "UID" });
		PreparedStatement ps = ConnectionManager.getRelationalDBConnection()
				.prepareStatement(accessionSQL);
		// RB - using first SpeciesProfile in the List of SpeciesProfiles: selectedSpeciesProfiles.get(0)
		ps.setString(1, "" + selectedSpeciesProfiles.get(0).getTaxon());
		//ps.setString(1, "" + speciesProfile.getTaxon());
		ResultSet result = ps.executeQuery();

		// Step 1 - populate the TableManager with records from the
		// entrytype_accession table
		_Log.info("\nSQL Query: [" + accessionSQL + "]");
		while (result.next()) {
			_Log.debug("Record: [" + ++recordCounter + "]");
			_Log.debug("entrytype_accession_hjid: ["
					+ result.getString("entrytype_accession_hjid")
					+ "], hjvalue: [" + result.getString("hjvalue") + "]");
			tableManager.submit("UniProt", QueryType.insert, new String[][] {
					{ "UID", result.getString("entrytype_accession_hjid") },
					{ "ID", result.getString("hjvalue") } });
		}
		_Log.info("Total Records: [" + recordCounter + "]");
		Row[] rows = tableManager.getRows();
		_Log.info("Step 1 - Number of rows in TM: [" + rows.length + "]");

		// Step 2 - if the record exists in the entrytype_name table,
		// get the entrytype_name value.
		_Log.info("\nSQL Query: [" + nameSQL + "]");
		recordCounter = 0;
		for (Row row : tableManager.getRows()) {
			_Log.debug("UID (entrytype_accession_hjid): ["
					+ row.getValue("UID") + "]");
			ps = ConnectionManager.getRelationalDBConnection()
					.prepareStatement(nameSQL);
			ps.setInt(1, Integer.parseInt(row.getValue("UID")));
			_Log.debug("Species: [" + row.getValue("Species") + "]");
			_Log.debug(ps.toString());
			result = ps.executeQuery();
			while (result.next()) {
				_Log.debug("Record: [" + ++recordCounter + "]");
				_Log.debug("hjvalue: [" + result.getString("hjvalue") + "]");
				tableManager.submit("UniProt", QueryType.insert,
						new String[][] { { "UID", row.getValue("UID") },
								{ "EntryName", result.getString("hjvalue") } });
			}

			// Step 3 - GeneName
			_Log.debug("\nGeneName\nSQL Query: [" + geneSQL + "]");
			ps = ConnectionManager.getRelationalDBConnection()
					.prepareStatement(geneSQL);
			ps.setInt(1, Integer.parseInt(row.getValue("UID")));
			result = ps.executeQuery();
			Map<String, String> typeToValue = new HashMap<String, String>();
			while (result.next()) {
				_Log.debug("type: [" + result.getString("type") + "] value: ["
						+ result.getString("value") + "]");
				typeToValue.put(result.getString("type"), result
						.getString("value"));
			}
			String geneName;
			String geneNameType;
			if (typeToValue.get("primary") != null) {
				geneName = typeToValue.get("primary");
				geneNameType = "primary";
			} else if (typeToValue.get("ordered locus") != null) {
				geneName = typeToValue.get("ordered locus");
				geneNameType = "ordered locus";
			} else {
				geneName = typeToValue.get("synonym");
				geneNameType = "synonym";
			}
			_Log.debug("Type: [" + geneNameType + "] GeneName: [" + geneName
					+ "]");
			tableManager.submit("UniProt", QueryType.insert, new String[][] {
					{ "UID", row.getValue("UID") }, { "GeneName", geneName } });

			// Step 4 -- Add the ProteinName
			ps = ConnectionManager
					.getRelationalDBConnection()
					.prepareStatement(
						"select evidencedstringtype.value "
							+ "from entrytype inner join proteintype "
							+ "on(entrytype.protein = proteintype.hjid) "
							+ "inner join proteinnamegrouprecommendednametype "
							+ "on (proteintype.recommendedname = proteinnamegrouprecommendednametype.hjid) "
							+ "inner join evidencedstringtype on (proteinnamegrouprecommendednametype.fullname = evidencedstringtype.hjid) "
							+ "where entrytype.hjid = ? order by evidencedstringtype.value;");
			ps.setInt(1, Integer.parseInt(row.getValue("UID")));
			result = ps.executeQuery();
			while (result.next()) {
				tableManager.submit("UniProt", QueryType.insert,
						new String[][] { { "UID", row.getValue("UID") },
								{ "ProteinName", result.getString("value") } });
			}

			// Step 5 -- add the function from entrytype_comment
			ps = ConnectionManager
					.getRelationalDBConnection()
					.prepareStatement(
							"select evidencedstringtype.value "
									+ "from commenttype inner join entrytype_comment "
									+ "on (entrytype_comment_hjchildid = commenttype.hjid) "
									+ "inner join evidencedstringtype "
									+ "on (text = evidencedstringtype.hjid) "
									+ "where type = 'function' "
									+ "and entrytype_comment_hjid = ?");
			ps.setInt(1, Integer.parseInt(row.getValue("UID")));
			result = ps.executeQuery();
			while (result.next()) {
				tableManager.submit("UniProt", QueryType.insert,
						new String[][] { { "UID", row.getValue("UID") },
								{ "Function", result.getString("value") } });
			}

			// Step 6 -- Finally, add the species name and the date
			tableManager.submit("UniProt", QueryType.insert, new String[][] {
					{ "UID", row.getValue("UID") },
					// RB - using first SpeciesProfile in the List of SpeciesProfiles: 
					//         selectedSpeciesProfiles.get(0) 
					{ "Species", "|" + selectedSpeciesProfiles.get(0).getSpeciesName() + "|" },
					//{ "Species", "|" + speciesProfile.getSpeciesName() + "|" },
					{ "\"Date\"", GenMAPPBuilderUtilities.getSystemsDateString(version) }
				}
			);
		}
		ps.close();

		Row[] tmrows = tableManager.getRows();
		_Log.info("End of Method - Number of rows in TM: [" + tmrows.length
				+ "]");

		primarySystemTableManager = tableManager;
		return tableManager;
	}

	/**
	 * @throws InvalidParameterException
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getSystemTableManager()
	 */
	public @Override
	TableManager getSystemTableManager() throws SQLException,
			InvalidParameterException {
		// If this work has already been done (and a non-null object exists)
		// just return it.
		if (systemTableManager != null) {
			return systemTableManager;
		}

		// create a new TableManager, which will define the columns and key for
		// the table.
		TableManager tableManager 
		    = new TableManager(new String[][] 
		        { { "ID", "VARCHAR(50) NOT NULL" },
		          { "Species", "MEMO" },
		          { "\"Date\"", "DATE" },
		          { "Remarks", "MEMO" } },
		        new String[] { "ID" } );

		PreparedStatement ps;
		ResultSet result;

		// loop through the list of System tables and for each one, ... do some
		// evaluation then processing if necessary.
		
		// RB - SQL should return the results ALREADY processed in query, 
		// to be fixed later.
		for (Entry<String, SystemType> systemTable : systemTables.entrySet()) {
			_Log.info("systemTable.getKey(): " + systemTable.getKey());
			/*
			 * JN 2/15/2007 I think the second part of this if is redundant,
			 * since the species specific tables are already included in the Map
			 * returned by getDatabaseSpecificSystemTables()
			 */
			_Log.info("getSystemTableManager(): for loop: systemTable.getKey() = "
				+ systemTable.getKey());
			
			if (( !getDatabaseSpecificSystemTables().containsKey( systemTable.getKey() ) ) ) {
				_Log.info("getSystemTableManager(): for loop: "
						+ systemTable.getKey()
						+ " is not in the list of DatabaseSpecificSystemTables or SpeciesSpecificSystemTables.");
				
				// RB - Programatically created SQL query string which returned
				// id, species name for all species from a species List. Multiple species
				// need (id = ?) for each and the end the query with (type = ?).
				
				// Dondi - This first part is actually OK.  The issue occurs when setting the values
				// of the ? parameters and in invoking the query (see below).
				StringBuilder basePrepareStatement = new StringBuilder
					( "SELECT distinct id, species_entry.value " +
					  "FROM dbreferencetype INNER JOIN " +
					  "(SELECT entrytype.hjid, organismnametype.value " +
				         "FROM entrytype INNER JOIN organismtype " +
				            "ON (entrytype.organism = organismtype.hjid)" +
				         "INNER JOIN organismnametype " +
				            "ON (organismtype.hjid = organismnametype.organismtype_name_hjid)" +
				         "INNER JOIN dbreferencetype " +
				            "ON (dbreferencetype.organismtype_dbreference_hjid = organismtype.hjid) " +
				         "WHERE dbreferencetype.type = 'NCBI Taxonomy' ");
				
		        // Dondi - You are not actually using the elements here; just their count.
		        // So, the old-school for loop is more appropriate.
		        for (int i = 0; i < selectedSpeciesProfiles.size(); i++) {
		        	basePrepareStatement
		        	    .append((i == 0) ? "AND (" : " OR ")
		                .append("id = ?");
		        }
		        basePrepareStatement.append(
		        		")) AS species_entry " +
		        		   "ON dbreferencetype.entrytype_dbreference_hjid = species_entry.hjid " +
		        		   "WHERE type = ?");
		    	
		        // RB - added query statement logging
		        _Log.info("getSystemTableManager(): query used: " + basePrepareStatement);
		        
				ps = ConnectionManager.getRelationalDBConnection()
						.prepareStatement( basePrepareStatement.toString() );
				
				// RB - Programmatically create the set string for variable number of
				// species then cap it with the current system type.
				
				for ( int i = 0; i < selectedSpeciesProfiles.size(); i++ ) {
	
					ps.setString( i + 1, Integer.toString( selectedSpeciesProfiles.get( i ).getTaxon() ) );
				}	
				ps.setString( selectedSpeciesProfiles.size() + 1, systemTable.getKey());
				
				// Dondi - Upon examining the schema, the id column turns out to be a string.
				// So, setString does turn out to be the right method for the id = ? clauses.
				// ie. ps.setString(1, Integer.toString(selected.getTaxon()));
								
				result = ps.executeQuery();

				while (result.next()) {
					
					// RB - Modified logging to bring in line with above changes.
					_Log.debug("getSystemTableManager(): while loop: ID:: "
						// RB - from SQL query result: get string "id" 
					    // + species name from column 2.
						+ result.getString("id") + "  Species:: "
						+ result.getString(2));
					    
					tableManager
						.submit
						   (
							systemTable.getKey(),
							QueryType.insert,
							new String[][] 
							{
							  { "ID",
								GenMAPPBuilderUtilities
								   .checkAndPruneVersionSuffix
								      (systemTable.getKey(),
								result.getString("id"))
							  },
									   
							  { "Species",
								// RB - get string from column 2 from
								// SQL query result.
								"|" + result.getString(2) + "|"
							  },
							  										
							  { "\"Date\"",
								GenMAPPBuilderUtilities
								   .getSystemsDateString(version)
							  }
							}
							);
				}
			}
		}

		// This goes off and gets the species specific system table(s) e.g.
		// TAIR, etc.

        // RB - get customizations for each species in selectedSpeciesProfiles.
		for ( SpeciesProfile selected : selectedSpeciesProfiles ) {
			tableManager = selected.getSystemTableManagerCustomizations(
				             	tableManager,
				             	getPrimarySystemTableManager(),
				             	version
				          		);
		}
		
		systemTableManager = tableManager;
		return tableManager;
	}


	/**
	 * @throws InvalidParameterException
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getRelationshipTableManager()
	 */
	public @Override
	List<TableManager> getRelationshipTableManager() throws SQLException,
			InvalidParameterException {
		List<TableManager> tableManagers = new ArrayList<TableManager>();
		TableManager tableManager;
		for (String relationshipTable : relationshipTables) {
			SystemTablePair stp = GenMAPPBuilderUtilities
					.parseRelationshipTableName(relationshipTable);

			// Skip the tables that we will do later (or have already been done,
			// like UniProt-GeneOntology).
			if (stp.systemTable1.equals(getPrimarySystemTable())
					&& stp.systemTable2.equals("GeneOntology")) {
				_Log.info("Relationship table manager skipping "
						+ relationshipTable);
				continue;
			} else if (stp.systemTable2.equals("GeneOntology")) {
				// produce last X-geneontology anything
				_Log.info("Relationship table manager saving "
						+ relationshipTable + " for second pass");
				produceLastRelationshipTables.add(relationshipTable);
				continue;
			}

			// FIXME: This must be done non-statically with a check to see if
			// the object is null OR not done here at all.
			// ExportWizard.updateExportProgress(65, "Preparing tables - " +
			// "Relationship table - " + relationshipTable + "...");

			tableManager = new TableManager(
					new String[][] { { "\"Primary\"", "VARCHAR(50) NOT NULL" },
					                 { "Related", "VARCHAR(50) NOT NULL" },
					                 { "Bridge", "VARCHAR(3)" } },
					new String[] { "\"Primary\"", "Related" } );
			
			tableManager.getTableNames().add(relationshipTable);

			if ("UniProt".equals(stp.systemTable1)
					&& !getDatabaseSpecificSystemTables().containsKey(
							stp.systemTable2)) {
				
				// UniProt-X conditional
				
				// RB - Added logging
				_Log.info("getRelationshipTable(): if (UniProt - X) {}");
				
				// RB - Programmatically create the SQL string for
				// variable number of species.
				StringBuilder basePrepareStatement = new StringBuilder
				   ("SELECT hjvalue, dbreferencetype.id " +
					   "FROM (SELECT entrytype.hjid FROM entrytype " +
					      "INNER JOIN organismtype " +
					         "ON (entrytype.organism = organismtype.hjid) " +
					      "INNER JOIN dbreferencetype " +
					         "ON (organismtype.hjid = dbreferencetype.organismtype_dbreference_hjid) " +
					      "WHERE dbreferencetype.type LIKE '%NCBI Taxonomy%' ");
				
				for (int i = 0; i < selectedSpeciesProfiles.size(); i++) {
		        	basePrepareStatement
		        	    .append((i == 0) ? "AND (" : " OR ")
		                .append("id = ?");
		        }
				
				basePrepareStatement.append(
					")) AS species_entry " +
						"INNER JOIN dbreferencetype " +
						   "ON (dbreferencetype.entrytype_dbreference_hjid = species_entry.hjid) "+
						"INNER JOIN entrytype_accession " +
						   "ON (entrytype_dbreference_hjid = entrytype_accession_hjid) " +
						"WHERE type = ?");
		    	
		        // RB - added query statement logging
		        _Log.info("getRelationshipTableManager(): query used: " + basePrepareStatement);
				
		        PreparedStatement ps = ConnectionManager.getRelationalDBConnection()
				.prepareStatement( basePrepareStatement.toString() );
		        
		        // RB - Programmatically create the set string for variable number of
				// species then cap it with the systemTable2.
				
				for ( int i = 0; i < selectedSpeciesProfiles.size(); i++ ) {
	
					ps.setString( i + 1, Integer.toString( selectedSpeciesProfiles.get( i ).getTaxon() ) );
				}	
				ps.setString(selectedSpeciesProfiles.size() + 1, stp.systemTable2);
				
				// RB - old set strings from single species
				// ps.setString(1, "" + speciesProfile.getTaxon());
        		// ps.setString(2, stp.systemTable2);

				ResultSet result = ps.executeQuery();

				String primary = "";
				String related = "";
				while (result.next()) {
					primary = result.getString("hjvalue");
					related = GenMAPPBuilderUtilities
							.checkAndPruneVersionSuffix(stp.systemTable2,
									result.getString("id"));

					tableManager.submit(
							relationshipTable,
							QueryType.insert,
							new String[][] {
									{ "\"Primary\"", primary != null ? primary : "" },
									{ "Related", related != null ? related : "" },
									// TODO This is hard-coded. Fix it.
									{ "Bridge", "S" }
									       }
									    );
				}
				ps.close();
			} else if (!getDatabaseSpecificSystemTables().containsKey(
							stp.systemTable1) &&
					   !getDatabaseSpecificSystemTables().containsKey(
							stp.systemTable2)) {
				
				// X-X conditional
				
				// RB - added logging
				_Log.info("getRelationshipTable(): else if (X - X) {}");
				
				
				// RB - Programmatically create the SQL string for
				// variable number of species.
				StringBuilder basePrepareStatement = new StringBuilder
				("SELECT id1, id2 FROM " +
				      "(SELECT dbref1.id " +
					     "AS id1, dbref2.id " +
					     "AS id2, dbref1.entrytype_dbreference_hjid " +
					     "AS dbrefhjid " +
					     "FROM dbreferencetype AS dbref1 " +
					     "INNER JOIN dbreferencetype AS dbref2 " +
					     "USING (entrytype_dbreference_hjid) " +
					     "WHERE dbref1.type <> dbref2.type " +
					     "AND dbref1.type = ? AND dbref2.type = ?) " +
				 "AS dbrefcomp INNER JOIN " +
				      "(SELECT entrytype.hjid FROM entrytype " +
					     "INNER JOIN organismtype " +
					     "ON (entrytype.organism = organismtype.hjid) " +
					     "INNER JOIN dbreferencetype " +
					     "ON (dbreferencetype.organismtype_dbreference_hjid = organismtype.hjid) " +
					     "WHERE dbreferencetype.type = 'NCBI Taxonomy'");
					     
				for (int i = 0; i < selectedSpeciesProfiles.size(); i++) {
		        	basePrepareStatement
		        	    .append((i == 0) ? "AND (" : " OR ")
		                .append("id = ?");
		        }
				basePrepareStatement.append(
					")) AS species_entry ON (dbrefcomp.dbrefhjid = species_entry.hjid)");
				
		        // RB - added query statement logging
		        _Log.info("getRelationshipTableManager(): query used: " + basePrepareStatement);
				
				PreparedStatement ps = ConnectionManager
						.getRelationalDBConnection()
						.prepareStatement( basePrepareStatement.toString() );

							
				ps.setString(1, stp.systemTable1);
				ps.setString(2, stp.systemTable2);

				// RB - Programmatically create the set string for variable number of
				// species then cap it with the systemTable2.
				
				for ( int i = 0; i < selectedSpeciesProfiles.size(); i++ ) {
	
					ps.setString( i + 3, Integer.toString( selectedSpeciesProfiles.get( i ).getTaxon() ) );
				}	
								
				// RB - old set strings from single species
				// ps.setString(3, "" + speciesProfile.getTaxon());
				
				
				ResultSet result = ps.executeQuery();
				while (result.next()) {
					String primary = GenMAPPBuilderUtilities
							.checkAndPruneVersionSuffix(stp.systemTable1,
									result.getString("id1"));
					String related = GenMAPPBuilderUtilities
							.checkAndPruneVersionSuffix(stp.systemTable2,
									result.getString("id2"));

					tableManager
							.submit(
									relationshipTable,
									QueryType.insert,
									new String[][] {
											{ "\"Primary\"", primary != null ? primary : "" },
											{ "Related", related != null ? related : "" },
											// FIXME This is hard-coded. Fix it.
											{ "Bridge", "S" } 
											       }
									);
				}
				ps.close();
			

			} else {
				
				boolean relationshipTableWasHandled = false;

				// Species-X or X-Species conditional, excluding GeneOntology
				
				//int counter = selectedSpeciesProfiles.size();
				for(SpeciesProfile species : selectedSpeciesProfiles) {
				
					if( (species.getSpeciesSpecificSystemTables().containsKey(stp.systemTable1) || 
						 species.getSpeciesSpecificSystemTables().containsKey(stp.systemTable2)) &&
						 !stp.systemTable2.equals("GeneOntology") ) {
					
						// RB - added logging
						_Log.info("getRelationshipTable(): else (Species - X or X - Species) {} " +
							" excluding GeneOntology");							

						_Log.info("( species.getSpeciesSpecificSystemTables().containsKey(" +
							stp.systemTable1 + ") || species.getSpeciesSpecificSystemTables()" +
							".containsKey(" + stp.systemTable2 + " )) && !" + stp.systemTable2 + 
							" equals GeneOnotology.");						
						
						tableManager = species
						   .getSpeciesSpecificRelationshipTable(
						      relationshipTable,
							  getPrimarySystemTableManager(),
							  getSystemTableManager(),
							  tableManager);							
						
						relationshipTableWasHandled = true;
					}
				}
				if ( !relationshipTableWasHandled ) {
				// No way currently of producing these
				
				// RB - added logging
				_Log.info("getRelationshipTable(): else - No way of currently producing these.");
				
				tableManager.submit(relationshipTable, QueryType.insert,
						new String[][] { { "\"Primary\"", "" },
								{ "Related", "" },
								// FIXME: This is hard-coded. Fix it.
								{ "Bridge", "" } });
				}
			}

		tableManagers.add(tableManager);
		}

		return tableManagers;
	
	} // END getRelationshipTableManager()

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getSecondPassTableManagers()
	 */
	@Override
	public TableManager[] getSecondPassTableManagers() throws SQLException {
		List<TableManager> tableManagers = new ArrayList<TableManager>();
		// RB - using first SpeciesProfile in the List of SpeciesProfiles: 
		//         selectedSpeciesProfiles.get(0)
		tableManagers.addAll(selectedSpeciesProfiles.get(0).getSpeciesSpecificCustomTables(version));
		// tableManagers.addAll(speciesProfile.getSpeciesSpecificCustomTables(version));
		tableManagers.add(getSecondPassRelationshipTables());
		return tableManagers.toArray(new TableManager[0]);
	}

	/**
	 * A helper method for getting the second pass TableManagers, specifically
	 * the relationship TableManagers.
	 *
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private TableManager getSecondPassRelationshipTables() {
		TableManager tableManager = new TableManager(new String[][] {
				{ "\"Primary\"", "VARCHAR(50) NOT NULL" },
				{ "Related", "VARCHAR(50) NOT NULL" },
				{ "Bridge", "VARCHAR(3)" } }, new String[] { "\"Primary\"",
				"Related" });

		PreparedStatement ps = null;
		ResultSet result = null;
		String sqlStatement = null;

		try {
			for (String relationshipTable : produceLastRelationshipTables) {
				SystemTablePair stp = GenMAPPBuilderUtilities
						.parseRelationshipTableName(relationshipTable);

				sqlStatement = "SELECT [UniProt-" + stp.systemTable1
						+ "].Related as id1, "
						+ "[UniProt-GeneOntology].Related as id2 "
						+ "FROM [UniProt-" + stp.systemTable1 + "] "
						+ "INNER JOIN [UniProt-GeneOntology] " + "ON [UniProt-"
						+ stp.systemTable1
						+ "].Primary = [UniProt-GeneOntology].Primary";

				// Alternative query when using a database other than Access.
				// String sqlStatement = "SELECT \"" + tableName +
				// "\".Related, " +
				// "\"UniProt-GeneOntology\".Related " +
				// "FROM \"" + tableName + "\" " +
				// "INNER JOIN \"UniProt-GeneOntology\" " +
				// "ON \"" + tableName + "\".\"Primary\" =
				// \"UniProt-GeneOntology\".\"Primary\"";
				_Log.info("Second-pass query: " + sqlStatement);
				ps = getExportConnection().prepareStatement(sqlStatement);

				result = ps.executeQuery();
				while (result.next()) {
					tableManager.submit(relationshipTable, QueryType.insert,
							new String[][] {
									{ "\"Primary\"", result.getString("id1") },
									{ "Related", result.getString("id2") },
									// TODO This is hard-coded. Fix it.
									{ "Bridge", "S" } });
				}
			}
		} catch (SQLException e) {
			StringBuffer errText = new StringBuffer(
					"An SQLException occurred in getSecondPassRelationshipTables() while reading from the database. ");
			errText.append(" Error Code: " + e.getErrorCode());
			errText.append(" Message: " + e.getMessage());
			_Log.error(errText);
			_Log.error(sqlStatement);
		} finally {
			try {
				ps.close();
			} catch (Exception exc) {
				_Log.warn("Problem closing PreparedStatement");
			}
		}
		return tableManager;
	}

	/**
	 * The log object for UniProtDatabaseProfile.
	 */
	private static final Log _Log = LogFactory
			.getLog(UniProtDatabaseProfile.class);
}
