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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author Joey J. Barrett Class: EscherichiaColiUniProtSpeciesProfile
 * @author Jeffrey Nicholas
 */
public class EscherichiaColiUniProtSpeciesProfile extends UniProtSpeciesProfile {
	private static final Log _Log = LogFactory.getLog(EscherichiaColiUniProtSpeciesProfile.class);
	
	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getPrimarySystemTableManagerCustomizations()
	 */
	@Override
	public TableManager getPrimarySystemTableManagerCustomizations(Date version) throws SQLException {
        // FIXME Too many similarities here between this version and the one for A. thaliana.
        // Some of this needs to go to the superclass, with the subclasses doing only
        // what is specific to that UniProt species.
		TableManager tableManager = null;
		PreparedStatement ps;
		int recordCounter = 0;

		String primarySQL = 
		" create temporary table temp_genename_primary AS " +
		" select b.entrytype_gene_hjid as hjid, a.value " +
		" from genenametype a LEFT OUTER JOIN entrytype_genetype b " + 
		" ON (entrytype_genetype_name_hjid =  b.hjid) " +
		" where a.type = 'primary'; ";

		String orderedLocusSQL = 
		" create temporary table temp_genename_orderedlocus AS " +
		" select b.entrytype_gene_hjid as hjid, a.value " +
		" from genenametype a LEFT OUTER JOIN entrytype_genetype b " + 
		" ON (entrytype_genetype_name_hjid =  b.hjid) " +
		" where a.type = 'ordered locus'; ";

		String proteinSQL = 
		" create temporary table temp_protein AS " +
//		" SELECT a.hjid, b.value " +
//		" FROM entrytype a INNER JOIN proteinnametype b ON (a.protein = b.proteintype_name_hjid) " + 
//		" WHERE b.proteintype_name_hjindex = 0; ";
		"select entrytype.hjid, evidencedstringtype.value from entrytype inner join proteintype on(entrytype.protein = proteintype.hjid) inner join proteinnamegrouprecommendednametype on (proteintype.recommendedname = proteinnamegrouprecommendednametype.hjid) inner join evidencedstringtype on (proteinnamegrouprecommendednametype.fullname = evidencedstringtype.hjid) order by evidencedstringtype.value;";

		String commentSQL = 
		" create temporary table temp_comment AS " +
//		" SELECT entrytype_comment_hjid as hjid, text " + 
//		" FROM commenttype INNER JOIN entrytype_comment ON (entrytype_comment_hjchildid = hjid) " + 
//		" WHERE type = 'function'; ";
        "select entrytype_comment_hjid as hjid, value as text from commenttype inner join entrytype_comment on (entrytype_comment_hjchildid = commenttype.hjid) inner join evidencedstringtype on (text = evidencedstringtype.hjid) where type = 'function'";

		String querySQL = 
		" select a.entrytype_accession_hjid as hjid, a.hjvalue as accession, b.hjvalue as entryname, c.value as primary, d.value as orderedlocus, e.value as protein, f.text as function " +
		" from " + 
		" entrytype_accession a LEFT OUTER JOIN entrytype_name b ON (a.entrytype_accession_hjid = b.entrytype_name_hjid) " +
		" LEFT OUTER JOIN temp_genename_primary c ON (a.entrytype_accession_hjid = c.hjid) " +
		" LEFT OUTER JOIN temp_genename_orderedlocus d ON (a.entrytype_accession_hjid = d.hjid) " +
		" LEFT OUTER JOIN temp_protein e ON (a.entrytype_accession_hjid = e.hjid) " +
		" LEFT OUTER JOIN temp_comment f ON (a.entrytype_accession_hjid = f.hjid) " + 
		" WHERE entrytype_accession_hjindex = 0; ";
		
		tableManager = new TableManager(new String[][] {
				{ "ID", "VARCHAR(50) NOT NULL" },
				{ "EntryName", "VARCHAR(50) NOT NULL" },
				{ "GeneName", "VARCHAR(50) NOT NULL" }, 
				{ "ProteinName", "MEMO" },
				{ "Function", "MEMO" }, { "Species", "MEMO" },
				{ "\"Date\"", "DATE" }, { "Remarks", "MEMO" } },
				new String[] { "UID" });

		ps = ConnectionManager.getRelationalDBConnection().prepareStatement(primarySQL);
		ps.executeUpdate();

		ps = ConnectionManager.getRelationalDBConnection().prepareStatement(orderedLocusSQL);
		ps.executeUpdate();
		
		ps = ConnectionManager.getRelationalDBConnection().prepareStatement(proteinSQL);
		ps.executeUpdate();
		
		ps = ConnectionManager.getRelationalDBConnection().prepareStatement(commentSQL);
		ps.executeUpdate();
		
		ps = ConnectionManager.getRelationalDBConnection().prepareStatement(querySQL);
		ResultSet result = ps.executeQuery();
		
		while (result.next()) {
			_Log.debug("\nRecord: [" + ++recordCounter + "]");
			_Log.debug("hjid, accession, entryname, primary, ordered locus, protein, function\n" +
					result.getString("hjid") + "\n" +
					result.getString("accession") + "\n" +
					result.getString("entryname") + "\n" +
					result.getString("primary") + "\n" +
					result.getString("orderedlocus") + "\n" +
					result.getString("protein") + "\n" +
					result.getString("function")
			);
			
			String geneName = null;
			if( result.getString("primary") != null ){
				geneName = result.getString("primary");
			} else if ( result.getString("orderedlocus") != null ){
				geneName = result.getString("orderedlocus");
			} else {
				// nulls are not allowed in E. Coli
				// write an error record and continue to the next record
				_Log.error("The following record had neither a 'primary' nor an Ordered Locus gene name. The record will be skipped:" +
						"hjid, accession, entryname, primary, ordered locus, protein, function\n" +
						result.getString("hjid") + "\n" +
						result.getString("accession") + "\n" +
						result.getString("entryname") + "\n" +
						result.getString("primary") + "\n" +
						result.getString("orderedlocus") + "\n" +
						result.getString("protein") + "\n" +
						result.getString("function"));
				continue;
			}
			
			tableManager.submit("UniProt", QueryType.insert, new String[][] {
					{ "UID", result.getString("hjid") },
					{ "ID", result.getString("accession") },
					{ "EntryName", result.getString("entryname") },	
					{ "GeneName", geneName } ,
					{ "ProteinName", result.getString("protein") },
					{ "Function", result.getString("function") },
					{ "Species", "|" + getSpeciesName() + "|" },
					{ "\"Date\"", GenMAPPBuilderUtilities
									.getSystemsDateString(version) }
			});
		}
		ps.close();

		Row[] tmrows = tableManager.getRows();
		_Log.info("End of Method - Number of rows in TM: [" + tmrows.length
				+ "]");

		return tableManager;

	}

    private final String SPECIES_TABLE = "Blattner";
    private final String SPECIES_SYSTEM_CODE = "Ln";

	/**
	 * Creates the UniProt E.coli species profile. This profile defines the requirements for 
	 * an Escherichia Coli species within a UniProt database.
	 */
	public EscherichiaColiUniProtSpeciesProfile() {
		super("Escherichia coli", "This profile defines the requirements for "
				+ "an Escherichia Coli species within a UniProt database.");
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

        // if SystemTable1 is NOT SPECIES_TABLE, then use it :: if SystemTable1
        // IS the SPECIES_TABLE, call it OrderedLocusNames
        if (!systemTable1.equals(SPECIES_TABLE)) {
            systemCode = systemTable1;
        } else {
            systemCode = "OrderedLocusNames";
        }

        // If SystemTable2 is NOT SPECIES_TABLE, then use it :: if SystemTable2
        // IS the SPECIES_TABLE, call it OrderedLocusNames
        if (!systemTable2.equals(SPECIES_TABLE)) {
            relatedCode = systemTable2;
        } else {
            relatedCode = "OrderedLocusNames";
        }
        // ### local vars finished

        // Call the super class's method, now that SPECIES_TABLE specific
        // normalization has been done
        tableManager = super.getRelationsTableManagerCustomizations(systemCode, relatedCode, templateDefinedSystemToSystemCode, tableManager);

        return tableManager;
	}

	/**
	 * Add 2 SPECIES_TABLE specific items to the tableManager, then call the
	 * super class to add the items all species will need.
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemsTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      DatabaseProfile)
	 */
	@Override
	public TableManager getSystemsTableManagerCustomizations(
			TableManager tableManager, DatabaseProfile dbProfile) {
				
/*
 * JN - 2/12/2007 -
 * This was an attempt to simplify the code, but it was done before I properly
 * understood what was going on. I may go back to it, but will use the code
 * below for now.
 * 
 * 		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", SPECIES_SYSTEM_CODE }, { "System", SPECIES_TABLE } });
		
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", SPECIES_SYSTEM_CODE }, { "SystemName", SPECIES_TABLE } });
*/		
		super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
		
		/*
		 * What this is doing is creating an update query that will change the
		 * name of the "Species" column in the System table.
		 */
		
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", SPECIES_SYSTEM_CODE },
				{ "Species", "|" + getSpeciesName() + "|" } });
		
		
/*
 * JN - 2/12/2007 - this is not needed here. The date update can be handled
 * by the UniProtDatabaseProfile.getSystemsTableManager() method.
 * 
 * 		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", SPECIES_SYSTEM_CODE },
				{
						"\"Date\"",
						GenMAPPBuilderUtilities.getSystemsDateString(dbProfile
								.getVersion()) } });*/
		
		// JN - the only reason this is last is that it was like that from
		// the start. Order may not matter, but I chose not to mess with it.
//		 JN - 2/12/2007 - This method does nothing (all code is commented)
		// Regardless, nothing it did do was pertinent to A. thaliana. -- goodbye
		//deleteme// tableManager = super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
		
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
    	comparisonList.add("ordered locus");
    	comparisonList.add("ORF");
		
		tableManager = super.systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, SPECIES_TABLE, comparisonList);
		
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
     * Need to get the Blattner System code
     */
    @Override
    public List<String> getSpeciesSpecificSystemCode(List<String> systemCodes, Map<String, String> templateDefinedSystemToSystemCode) {
        // JD: FIXME Why is this specifically adding the system code at index #2?
        systemCodes.add(2, SPECIES_SYSTEM_CODE);
        return systemCodes;
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

} // end class
