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
 * SpeciesProfile implementation for A.thaliana.
 * 
 * @author Jeffrey Nicholas Class: ArabidopsisThalianaUniProtSpeciesProfile
 * @author Joey J. Barrett
 */
public class ArabidopsisThalianaUniProtSpeciesProfile extends UniProtSpeciesProfile {
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
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemTables()
	 */
	@Override
	protected Map<String, SystemType> getSpeciesSpecificSystemTables() {
		Map<String, SystemType> speciesSpecificAvailableSystemTables = new HashMap<String, SystemType>();

		speciesSpecificAvailableSystemTables.put("Blattner", SystemType.Proper);
		return speciesSpecificAvailableSystemTables;
	}

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getRelationsTableManagerCustomizations(java.lang.String,
	 *      java.lang.String, java.util.Map,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
	 */
	@Override
	public TableManager getRelationsTableManagerCustomizations(
			String systemTable1, String systemTable2,
			Map<String, String> templateDefinedSystemToSystemCode,
			TableManager tableManager) {
		tableManager.submit("Relations", QueryType.insert, new String[][] {
				{
						"SystemCode",
						templateDefinedSystemToSystemCode.get(!systemTable1
								.equals("Blattner") ? systemTable1
								: "OrderedLocusNames") },
				{
						"RelatedCode",
						templateDefinedSystemToSystemCode.get(!systemTable2
								.equals("Blattner") ? systemTable2
								: "OrderedLocusNames") },
				{ "Relation", systemTable1 + "-" + systemTable2 },
				{
						"Type",
						systemTable1.equals("UniProt")
								|| systemTable2.equals("UniProt") ? "Direct"
								: "Inferred" }, { "Source", "" } });
		return tableManager;
	}

	/**
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
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", "Ln" },
				{ "Species", "|" + getSpeciesName() + "|" } });
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", "Ln" },
				{
						"\"Date\"",
						GenMAPPBuilderUtilities.getSystemsDateString(dbProfile
								.getVersion()) } });
		return tableManager;
	}

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      java.util.Date)
	 */
	@Override
	public TableManager getSystemTableManagerCustomizations(
			TableManager tableManager, TableManager primarySystemTableManager,
			Date version) throws SQLException {
		PreparedStatement ps = ConnectionManager
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
		}

		return tableManager;
	}

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificRelationshipTable(java.lang.String,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
	 */
	@Override
	public TableManager getSpeciesSpecificRelationshipTable(
			String relationshipTable, TableManager primarySystemTableManager,
			TableManager systemTableManager, TableManager tableManager)
			throws SQLException {
		SystemTablePair stp = GenMAPPBuilderUtilities
				.parseRelationshipTableName(relationshipTable);
		if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable1)) {
			// Blattner-X
			PreparedStatement ps = ConnectionManager
					.getRelationalDBConnection().prepareStatement(
							"SELECT id " + "FROM dbreferencetype "
									+ "WHERE type = ? and "
									+ "entrytype_dbreference_hjid = ?");
			ps.setString(1, stp.systemTable2);
			for (Row row : systemTableManager.getRows()) {
				if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals(
						"Blattner")) {
					ps.setString(2, row.getValue("UID"));
					ResultSet result = ps.executeQuery();
					while (result.next()) {
						// Fix blattner IDs of the form xxxx/yyyy
						for (String Blattner_ID : row.getValue("ID").split("/")) {
							tableManager
									.submit(
											relationshipTable,
											QueryType.insert,
											new String[][] {
													{ "\"Primary\"",
															Blattner_ID },
													{
															"Related",
															result
																	.getString("id") },
													// TODO This is hard-coded.
													// Fix it.
													{ "Bridge", "S" } });
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

		return tableManager;
	}

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemCode(java.util.List,
	 *      java.util.Map)
	 */
	@Override
	public List<String> getSpeciesSpecificSystemCode(List<String> systemCodes,
			Map<String, String> templateDefinedSystemToSystemCode) {
		systemCodes.add(2, templateDefinedSystemToSystemCode
				.get("OrderedLocusNames"));
		return systemCodes;
	}
}
