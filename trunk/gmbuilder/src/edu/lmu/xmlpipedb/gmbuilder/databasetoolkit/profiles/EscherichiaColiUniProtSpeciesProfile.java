/********************************************************
 * Filename: UniProtSpeciesProfile.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: This an instance of a Escherichia Coli
 * species for the UniProt centric database.  This object
 * defines all properties for this species.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities.SystemTablePair;

/**
 * @author Joey J. Barrett Class: EscherichiaColiUniProtSpeciesProfile
 */
public class EscherichiaColiUniProtSpeciesProfile extends UniProtSpeciesProfile {
	/**
	 * Creates the UniProt E.coli species profile. This profile defines the requirements for 
	 * an Escherichia Coli species within a UniProt database.
	 */
	public EscherichiaColiUniProtSpeciesProfile() {
		super("Escherichia coli", "This profile defines the requirements for "
				+ "an Escherichia Coli species within a UniProt database.");
	}

	/**
	 * This method is overridden from the super class, because E.coli
	 * requires Blattner tables. This method is called by the Export
	 * Dialog when populating the list of Proper System Tables.
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemTables()
	 */
	@Override
	protected Map<String, SystemType> getSpeciesSpecificSystemTables() {
		Map<String, SystemType> speciesSpecificAvailableSystemTables = new HashMap<String, SystemType>();

		speciesSpecificAvailableSystemTables.put("Blattner", SystemType.Proper);
		return speciesSpecificAvailableSystemTables;
	}

	/**
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
	@Override
	public TableManager getRelationsTableManagerCustomizations(
			String systemTable1, String systemTable2,
			Map<String, String> templateDefinedSystemToSystemCode,
			TableManager tableManager) {
		
		
// ### create some local vars and set them
		String systemCode = null;
		String relatedCode = null;

//		 if SystemTable1 is NOT blattner, then use it :: if SystemTable1 IS blattner, call it OrderedLocusNames
		if( !systemTable1.equals("Blattner") ){
			systemCode = systemTable1;
		} else {
			systemCode = "OrderedLocusNames";
		}
		
//		 If SystemTable2 is NOT blattner, then use it :: if SystemTable2 IS blattner, call it OrderedLocusNames
		if( !systemTable2.equals("Blattner") ){
			relatedCode = systemTable2;
		} else {
			relatedCode = "OrderedLocusNames";
		}
// ### local vars finished
		
		// Call the super class's method, now that blattner specific normalization
		// has been done
		tableManager = super.getRelationsTableManagerCustomizations(systemCode, relatedCode, templateDefinedSystemToSystemCode, tableManager);

		
		return tableManager;
	}

	/**
	 * Add 2 Blattner specific items to the tableManager, then call the
	 * super class to add the items all species will need.
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemsTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      DatabaseProfile)
	 */
	@Override
	public TableManager getSystemsTableManagerCustomizations(
			TableManager tableManager, DatabaseProfile dbProfile) {
				
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", "Ln" }, { "System", "Blattner" } });
		
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", "Ln" }, { "SystemName", "Blattner" } });
		
		// JN - the only reason this is last is that it was like that from
		// the start. Order may not matter, but I chose not to mess with it.
		tableManager = super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
		
		return tableManager;
	}

	/**
	 * Contains Blattner in processing criteria.
	 * 
	 * Change to call super.getSystemTableManagerCustomizations()
	 *  and store result in TableManger
	 *  
	 * The way this is implemented, it is prone to side effects
	 * 
	 * idea 1
	 * :: CREATe 2 protected methods in super. let this and the super call those methods as needed to elminate redundant code
	 * 
	 * idea 2
	 * :: Change this method and the super to call another protected method in the super which does all the work.
	 * :::: Create a protected method in the super that takes all the params of this method plus an array of Strings, these are used in the tableManager.submit call(s) during the for loop.
	 * 
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      java.util.Date)
	 */
	@Override
	public TableManager getSystemTableManagerCustomizations(
			TableManager tableManager, TableManager primarySystemTableManager,
			Date version) throws SQLException {
		
		tableManager = super.systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, "Blattner");
		
/*		PreparedStatement ps = ConnectionManager
				.getRelationalDBConnection()
				.prepareStatement(
						"SELECT value, type "
								+ "FROM genenametype INNER JOIN entrytype_genetype "
								+ "ON (entrytype_genetype_name_hjid = entrytype_genetype.hjid) "
								+ "WHERE entrytype_gene_hjid = ?");
		ResultSet result;

		for (Row row : primarySystemTableManager.getRows()) {
			ps.setString(1, row.getValue("UID"));
			result = ps.executeQuery();

			// We actually want to keep the case where multiple ordered locus
			// names appear.
			while (result.next()) {
				String type = result.getString("type");
				if ("ordered locus".equals(type) || "ORF".equals(type)) {
					// We want this name to appear in the Blattner system table.
					for (String id : result.getString("value").split("/")) {
						tableManager
								.submit(
										"Blattner",
										QueryType.insert,
										new String[][] {
												{ "ID", id },
												{
														"Species",
														"|" + getSpeciesName()
																+ "|" },
												{
														"\"Date\"",
														GenMAPPBuilderUtilities
																.getSystemsDateString(version) },
												{ "UID", row.getValue("UID") } });
					}
				}
			}
		}*/

		return tableManager;
	}

	/**
	 * This method is specific to E.coli, since it contains Blattner specific
	 * processing.
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
		
		tableManager = super.speciesSpecificRelationshipTableHelper(relationshipTable, primarySystemTableManager, systemTableManager, tableManager, "Blattner", "Bridge", "S");
		
		/*SystemTablePair stp = GenMAPPBuilderUtilities
				.parseRelationshipTableName(relationshipTable);
		if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable1)) {
			// Blattner-X
			PreparedStatement ps = ConnectionManager
					.getRelationalDBConnection().prepareStatement("SELECT id " + "FROM dbreferencetype " + "WHERE type = ? and " + "entrytype_dbreference_hjid = ?");
			ps.setString(1, stp.systemTable2);
			for (Row row : systemTableManager.getRows()) {
				if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals(
						"Blattner")) {
					ps.setString(2, row.getValue("UID"));
					ResultSet result = ps.executeQuery();
					while (result.next()) {
						// Fix blattner IDs of the form xxxx/yyyy
						for (String Blattner_ID : row.getValue("ID").split("/")) {
							tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", Blattner_ID }, { "Related", result.getString("id") },
							 TODO This is hard-coded. Fix it.{ "Bridge", "S" } });
						}
					}
					result.close();
				}
			}
			ps.close();
		} else if (getSpeciesSpecificSystemTables().containsKey(
				stp.systemTable2)
				&& !stp.systemTable1.equals("UniProt")) {
			// X-Blattner, excluding UniProt-Blattner
			PreparedStatement ps = ConnectionManager
					.getRelationalDBConnection().prepareStatement(
							"SELECT entrytype_dbreference_hjid, id "
									+ "FROM dbreferencetype where type = ?");
			ps.setString(1, stp.systemTable1);
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				String primary = result.getString("id");
				String related = result.getString("entrytype_dbreference_hjid");
				for (Row row : systemTableManager.getRows()) {
					if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals(
							"Blattner")
							&& row.getValue("UID").equals(related)) {
						for (String Blattner_ID : row.getValue("ID").split("/")) {
							tableManager.submit(relationshipTable,
									QueryType.insert, new String[][] {
											{ "\"Primary\"", primary },
											{ "Related", Blattner_ID },
											// TODO This is hard-coded. Fix it.
											{ "Bridge", "S" } });
						}
					}
				}
			}

			result.close();
			ps.close();
		} else {
			for (Row row1 : systemTableManager.getRows()) {
				if (row1.getValue(TableManager.TABLE_NAME_COLUMN).equals(
						"Blattner")) {
					for (Row row2 : primarySystemTableManager.getRows()) {
						if (row1.getValue("UID").equals(row2.getValue("UID"))) {
							for (String Blattner_ID : row1.getValue("ID")
									.split("/")) {
								tableManager.submit(relationshipTable,
										QueryType.insert, new String[][] {
												{ "\"Primary\"",
														row2.getValue("ID") },
												{ "Related", Blattner_ID },
												// TODO This is hard-coded. Fix
												// it.
												{ "Bridge", "S" } });
							}
							break;
						}
					}
				}
			}
		}
*/
		return tableManager;
	}

}
