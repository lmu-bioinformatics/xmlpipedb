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
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities.SystemTablePair;
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
		super("Escherichia coli (strain K12)", "This profile defines the requirements for "
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
		speciesSpecificAvailableSystemTables.put("W3110", SystemType.Proper);
		
		return speciesSpecificAvailableSystemTables;
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
		
		
        tableManager = super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", SPECIES_SYSTEM_CODE },
            { "System", SPECIES_TABLE }
        });
        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", SPECIES_SYSTEM_CODE },
            { "SystemName", SPECIES_TABLE }
        });
        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", SPECIES_SYSTEM_CODE },
            { "Species", "|" + getSpeciesName() + "|"}
        });
        
        tableManager.submit("Systems", QueryType.update, new String[][] {
                { "SystemCode", "W3" },
                { "System", "W3110" }
        });
        tableManager.submit("Systems", QueryType.update, new String[][] {
                { "SystemCode", "W3" },
                { "SystemName", "W3110"}
        });
        tableManager.submit("Systems", QueryType.update, new String[][] {
                { "SystemCode", "W3"},
                { "Species", "|" + getSpeciesName() + "|"}
        });
            
        
               
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
    	List<String> comparisonList = new ArrayList<String>(2);
    	comparisonList.add("ordered locus");
    	comparisonList.add("ORF");
    	
    	
    	final String blattnerFilter = "(JW|ECK)[0-9a-zA-Z_.-]*";
        final String w3110Filter = "b[0-9a-zA-Z_.-]*";
       
    	TableManager result = super.systemTableManagerCustomizationsHelper(tableManager, 
    			primarySystemTableManager, version, SPECIES_TABLE, comparisonList, blattnerFilter);
    	result = super.systemTableManagerCustomizationsHelper(result, 
    			primarySystemTableManager, version, "W3110", comparisonList, w3110Filter);
    	
    	
		
		return result;
	}

    /**
     * Need to get the Blattner System code
     */
    @Override
    public List<String> getSpeciesSpecificSystemCode(List<String> systemCodes, Map<String, String> templateDefinedSystemToSystemCode) {
        // JD: FIXME Why is this specifically adding the system code at index #2?
        systemCodes.add(2, SPECIES_SYSTEM_CODE);
        systemCodes.add("W3");
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
		
		// We need to do a little pre-parsing because we have to deal with both species Relationship Tables (Blattner & W3110)
		SystemTablePair stp = GenMAPPBuilderUtilities.parseRelationshipTableName(relationshipTable);
		
		String speciesCriteria = "";
		
		// Blattner-W3110
		if(stp.systemTable1.equals(SPECIES_TABLE) &&
				stp.systemTable2.equals("W3110")) {
			
			// Because they are both species specfic system tables, the TableHelper
			// should take care of it (it doesn't matter who gets assigned speciesCriteria)
			speciesCriteria = "Blattner";
			
		// W3110-Blatnner
		} else if(stp.systemTable1.equals("W3110") &&
				stp.systemTable2.equals(SPECIES_TABLE)) {
			
			speciesCriteria = "W3110";
			
		// W3110-(non-species specific) || (non-species-specific)-W3110	
		} else if(stp.systemTable1.equals("W3110") || 
				stp.systemTable2.equals("W3110")) {
			
			speciesCriteria = "W3110";

		// Blattner-(non-species specific) || (non-species-specific)-W3110
		} else if(stp.systemTable1.equals(SPECIES_TABLE) || 
				stp.systemTable2.equals(SPECIES_TABLE)) {
			
			speciesCriteria = SPECIES_TABLE;
			
		}
		
		tableManager = super.speciesSpecificRelationshipTableHelper(relationshipTable,
			    primarySystemTableManager,
			    systemTableManager,
			    tableManager,
			    speciesCriteria,
			    "Bridge",
			    "S");
		
		
		return tableManager;
	}

}
