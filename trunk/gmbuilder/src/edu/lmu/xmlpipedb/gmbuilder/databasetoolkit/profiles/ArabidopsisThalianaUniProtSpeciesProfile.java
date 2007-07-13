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
 * SpeciesProfile implementation for A.thaliana.
 * 
 * @author Jeffrey Nicholas Class: ArabidopsisThalianaUniProtSpeciesProfile
 * @author Joey J. Barrett
 */
public class ArabidopsisThalianaUniProtSpeciesProfile extends UniProtSpeciesProfile {
	
	private final String SPECIES_TABLE = "TAIR";
	private final String SPECIES_SYSTEM_CODE = "A";
	private static final Log _Log = LogFactory.getLog(ArabidopsisThalianaUniProtSpeciesProfile.class);
	
	/**
	 * Creates the UniProt A.thaliana species profile. This profile defines the requirements for 
	 * an Arabidopsis thaliana species within a UniProt database.
	 */
	public ArabidopsisThalianaUniProtSpeciesProfile() {
		super("Arabidopsis thaliana", "This profile defines the requirements for "
				+ "an Arabidopsis thaliana species within a UniProt database.");
	}


	/**
	 * This method is overridden from the super class, because this species
	 * requires a species specific SPECIES_TABLE. This method is called by the Export
	 * Dialog when populating the list of Proper System Tables.
	 * 
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemTables()
	 */
	@Override
	protected Map<String, SystemType> getSpeciesSpecificSystemTables() {
		Map<String, SystemType> speciesSpecificAvailableSystemTables = new HashMap<String, SystemType>();

		//JN - I think this is where I made  the fix for the value that was going into
		// the systems table -- it looks like this method is being used by ExportWiz and 
		// elsewhere with different desired results.
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
	
/*		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", SPECIES_SYSTEM_CODE }, { "System", SPECIES_TABLE },
				{ "\"Date\"", GenMAPPBuilderUtilities
							.getSystemsDateString(dbProfile.version) }
				});*/
		/*
		 * This entry is different from E. coli, in that |GeneName\\sBF| was 
		 * changed to |GeneName\\BF|, removing the "s". This makes GeneName a 
		 * non-searchable field. This is necessary because A. thaliana data doesn't
		 * have GeneNames for all entries (it has nulls). If (when) this is ever
		 * corrected (i.e. all GeneNames have a value) the "s" can be added back in. 
		 */
		tableManager.submit("Systems", QueryType.update, new String[][] { { "SystemCode", DatabaseProfile.templateDefinedSystemToSystemCode.get("UniProt") }, { "Columns", "ID|EntryName\\sBF|GeneName\\BF|ProteinName\\BF|Function\\BF|" } });
		
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", SPECIES_SYSTEM_CODE },
				{ "Species", "|" + getSpeciesName() + "|" } });

		//The columns field of the TAIR entry need to just be ID| instead of 
		// ID|Name\BF| (because we don't have a Name field in our TAIR table).
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{ "SystemCode", SPECIES_SYSTEM_CODE },
				{ "Columns", "ID|" } });
		
		
		// JN - the only reason this is last is that it was like that from
		// the start. Order may not matter, but I chose not to mess with it.
		// JN - 2/12/2007 - This method does nothing (all code is commented)
		// Regardless, nothing it did do was pertinant to A. thalian. -- goodbye
		//deleteme// tableManager = super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
		
		return tableManager;
	}

	/**
	 * This method calls a helper method in the super class, passing the species
	 * specific table name to be used.
	 * 
	 * This method controls what data is put into the species specific table.
	 * In this case, what goes in the TAIR table. For A. thaliana, we only
	 * want 'ordered locus' records since the 'ORF' records are not complete.
	 * So, we have only added 'ordered locus' to the "comparisionList" below. 
	 * 
	 * JN 2/15/2007
	 * In our case, in fact, we don't want this method to do anything, since
	 * id's in the TAIR table cannot come from Gene. This method just
	 * returns null.
	 * 
	 * @throws InvalidParameterException 
	 * 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
	 *      java.util.Date)
	 */
	@Override
	public TableManager getSystemTableManagerCustomizations(
			TableManager tableManager, TableManager primarySystemTableManager,
			Date version) {
	    // All TAIR IDs follow this pattern.
	    final String tairID = "[Aa][Tt][0-9][CcMmGg][0-9][0-9][0-9][0-9][0-9]";

        PreparedStatement ps;
        ResultSet result;
        String dateToday = GenMAPPBuilderUtilities.getSystemsDateString(version);
        int splits = -1;
        String sqlQuery;
        
		//step 1 - put the dbreference values for TAIR into a temp table, at the
        // same time "cleaning out" any prefixes and suffixes (via the substring
        // function)
		sqlQuery = 
		"create temporary table temp_tair as " +
		"select a.entrytype_dbreference_hjid as hjid , substring(a.id from ?) from dbreferencetype a " +
		"a.id SIMILAR TO ? " +
		"group by a.id, a.entrytype_dbreference_hjid";
        try {
	    	ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
            ps.setString(1, tairID);
	    	ps.setString(2, "%" + tairID + "%");
	    	ps.executeUpdate();
		} catch (SQLException e) {
            logSQLException(e, sqlQuery);
		}
		
		// step 1a - TAIR IDs can also be found in propertytype
		sqlQuery = "insert into temp_tair " +
		    "select d.entrytype_dbreference_hjid as hjid, p.value " +
		    "from propertytype p inner join dbreferencetype d on (p.dbreferencetype_property_hjid = d.hjid) " +
		    "where p.value similar to ? " +
		    "group by d.entrytype_dbreference_hjid, p.value";
        try {
            ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
            // We don't accommodate prefixes nor suffixes in propertytype.
            ps.setString(1, tairID);
            ps.executeUpdate();
        } catch (SQLException e) {
            logSQLException(e, sqlQuery);
        }

		//step 2 - put the genename values that match the TAIR pattern exactly* into the temp table
		//* some genename records have more than one TAIR ID per cell and they 
		//   must have their values split out in step 3 below.
		sqlQuery =
        "insert into temp_tair " +
        "select d.entrytype_gene_hjid as hjid, c.value " +
        "from genenametype c INNER JOIN entrytype_genetype d ON (c.entrytype_genetype_name_hjid = d.hjid) " +
        "where c.value SIMILAR TO ? " +
        "group by d.entrytype_gene_hjid, c.value";
        try {
	    	ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
	    	ps.setString(1, tairID);
	        ps.executeUpdate();
		} catch (SQLException e) {
            logSQLException(e, sqlQuery);
		}
			
		//step 3 - get the genename values for the records with multiple TAIR IDs
		
		/* JD: I see what Jeffrey is trying to do here, but the logic breaks
		 * somewhat when we consider that:
		 * 
		 * (a) the "separator" isn't just a slash; it may also be a colon,
		 *     a semicolon, or a space
		 *
		 * (b) many composite IDs consist of more than just TAIR IDs; i.e., they
		 *     may use other formats that aren't necessarily 9 characters long
		 * 
		 * (c) see below for the proposed alternative
		 */
		// 3a. - determine the largest number of values in one cell
		sqlQuery =
    	" select max(char_length(c.value)) as maxLength " +
		" from genenametype c " + 
		" where c.value SIMILAR TO ?";
        try {
        	ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
        	ps.setString(1, "%/" + tairID + "/*A%");
	        result = ps.executeQuery();
			// get result MOD 9 (number of characters in ID) = number of "/" characters in longest value
	        result.next();
	        int maxLength = result.getInt("maxLength");
	        splits = maxLength / 9; // 9 is the length of the TAIR ID (At1g12345)
		} catch (SQLException e) {
            logSQLException(e, sqlQuery);
		}
		
			//step 4 - generate the query that will put values from each column of splits into the temp table
	        for( int i = 1; i <= splits; i++){
	            try {
	            	sqlQuery = 
	            	"insert into temp_tair " +
	            	"select hjid, value from ( " +
	            	"select d.entrytype_gene_hjid as hjid, split_part(c.value, '/', " + i + ") as value " +
	            	"from genenametype c INNER JOIN entrytype_genetype d ON (c.entrytype_genetype_name_hjid = d.hjid) " +
	            	"where c.value SIMILAR TO ?) as split_gene " +
	            	"where value SIMILAR TO ? ";
	                ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
	                ps.setString(1, "%" + tairID + "/%");
	                ps.setString(2, tairID);
	                ps.executeUpdate();
	            } catch (SQLException e) {
	                logSQLException(e, sqlQuery);
	            }
	        }

		// Step 3: extract the genenametype IDs that are likely to be lists of
		// further IDs, with at least one TAIR ID among them.  For each of these
		// potential lists, extract the TAIR IDs, then insert a new individual
		// record for each of them.
		//
		// FIXME Yes, this is nasty slow.  But more accurate and general.
//        sqlQuery =
//            "select d.entrytype_gene_hjid as hjid, c.value " +
//            "from genenametype c INNER JOIN entrytype_genetype d ON (c.entrytype_genetype_name_hjid = d.hjid) " +
//            "where c.value SIMILAR TO ? and char_length(c.value) > ? " +
//            "group by d.entrytype_gene_hjid, c.value";

			//step 5 - extract the complete list of TAIR IDs into a resultset
	        sqlQuery = 
	        " select id from temp_tair where id SIMILAR TO ? group by id"; 
	        try {
			    ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
			    ps.setString(1, tairID);
		        result = ps.executeQuery();
		        int i = 0;
				//step 5 - put the tair ids into the tablemanager
		        for(; result.next(); i++) {
		            //tableManager.submit(substituteTable, QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", dateToday }, { "UID", row.getValue("UID") } });
		        	_Log.debug("getSystemTableManagerCustomizations(): TAIR ID" + result.getString("id") );
		            _Log.debug("Row #: [" + i + "]");
		            tableManager.submit("TAIR", QueryType.insert, new String[][] { { "ID", result.getString("id") }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", dateToday } });
		        }
		        _Log.info("TAIR Records Written to TableManger: [" + i + "]");
			} catch (SQLException e) {
			    logSQLException(e, sqlQuery);
			}

		return tableManager;
	}

	/**
	 * Helper method for logging an SQL exception.
	 */
	private void logSQLException(SQLException sqlexc, String sqlQuery) {
        _Log.error("Exception trying to execute query: " + sqlQuery);
        while (sqlexc != null) {
            _Log.error("Error code: [" + sqlexc.getErrorCode() + "]");
            _Log.error("Error message: [" + sqlexc.getMessage() + "]");
            _Log.error("Error SQL State: [" + sqlexc.getSQLState() + "]");
            sqlexc = sqlexc.getNextException();
        }
    }

	/**
	 * Need to get the TAIR System code 
	 */
	@Override
	public List<String> getSpeciesSpecificSystemCode(List<String> systemCodes, Map<String, String> templateDefinedSystemToSystemCode) {
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
		
		//tableManager = super.speciesSpecificRelationshipTableHelper(relationshipTable, primarySystemTableManager, systemTableManager, tableManager, SPECIES_TABLE, "Bridge", "S");
		String sqlQuery;
		String finalColumnName = "Bridge";
		String finalColumnValue = "S";
		
		//Separate the String relationshipTable into two parts based on the dash ("-") in the String
		// Store the two parts as systemTable1 and systemTable2 in the SystemTablePair object
        SystemTablePair stp = GenMAPPBuilderUtilities.parseRelationshipTableName(relationshipTable);

		// Get a Map, which in the case of A. Thaliana only contains one entry (for TAIR) as a "proper" system table
        
		//   The gist of the following if / elseif / else block is:
		//     if      stp.systemTable1 == TAIR, then do some stuff (this will cover TAIR-EMBL, TAIR-PDB, TAIR-Pfam, and TAIR-InterPro)
		//     elseif  stp.systemTable2 == TAIR AND systemTable1 is NOT UniProt, then do some other stuff (this will cover UniGene-Tair)
		//     else    do some different stuff (this will cover UniProt-TAIR)
        //   NOTE: In the non-E.coli case, e.g. A.thaliana, substitute the term TAIR for Blattner, above.
		

        if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable1)) {
        	sqlQuery = 
        		"SELECT b.id as primary, a.id as related " +
        		"FROM dbreferencetype a " +
        		"INNER JOIN (select hjid, id from temp_tair group by hjid, id) as b " +
        		"on (a.entrytype_dbreference_hjid = b.hjid) " +
        		"WHERE a.type = ? group by b.id, a.id;";  
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
            ps.setString(1, stp.systemTable2);
            ResultSet result = ps.executeQuery();
            
            while (result.next()) {
                tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", result.getString("primary") }, { "Related", result.getString("related") },
                // TODO This is hard-coded. Fix it.
                { finalColumnName, finalColumnValue } });
            }
            ps.close();
        } else if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable2) && !stp.systemTable1.equals("UniProt")) {
        	// the only difference between this an the above case is that the primary here is the UniGene id and TAIR is related.
        	sqlQuery = 
        		"SELECT b.id as related, a.id as primary " +
        		"FROM dbreferencetype a " +
        		"INNER JOIN (select hjid, id from temp_tair group by hjid, id) as b " +
        		"on (a.entrytype_dbreference_hjid = b.hjid) " +
        		"WHERE a.type = ? group by b.id, a.id;";  
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
            ps.setString(1, stp.systemTable1);
            ResultSet result = ps.executeQuery();
            
            while (result.next()) {
                tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", result.getString("primary") }, { "Related", result.getString("related") },
                // TODO This is hard-coded. Fix it.
                { finalColumnName, finalColumnValue } });
            }
            ps.close();
        } else {
    			// Go through each row (AKA row1...) in the systemTableManager that was passed in, checking for "Blattner"
    			//  If we find "Blattner", go through every row (AKA row2) in the primarySystemTableManger (AKA UniProt table)
    			//    check if row1's UID value is the same as row2's UID value,
    			//		if yes, loop some more!! (yippee), this time we are getting the value of the ID field,
    			//			then we'll write out a record.

        		sqlQuery = "SELECT a.accession as primary, b.id as related " +
        				"FROM temp_uniprot a " +
        				"INNER JOIN (select hjid, id from temp_tair group by hjid, id) as b " +
        				"on (b.hjid = a.hjid) " +
        				"group by accession, id;";
                PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sqlQuery);
                ResultSet result = ps.executeQuery();

                while (result.next()) {
                	tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", result.getString("primary") }, { "Related", result.getString("related") },
                    //TODO This is hard-coded.  Fix it. 
                    { finalColumnName, finalColumnValue } });
                }
        }
		
		return tableManager;
	}


	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getPrimarySystemTableManagerCustomizations()
	 */
	@Override
	public TableManager getPrimarySystemTableManagerCustomizations(Date version)
			throws SQLException {
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
		" SELECT a.hjid, b.value " +
		" FROM entrytype a INNER JOIN proteinnametype b ON (a.protein = b.proteintype_name_hjid) " + 
		" WHERE b.proteintype_name_hjindex = 0; ";

		String commentSQL = 
		" create temporary table temp_comment AS " +
		" SELECT entrytype_comment_hjid as hjid, text " + 
		" FROM commenttype INNER JOIN entrytype_comment ON (entrytype_comment_hjchildid = hjid) " + 
		" WHERE type = 'function'; ";

		String createTableSQL = 
		" create temporary table temp_uniprot AS" +
		" select a.entrytype_accession_hjid as hjid, a.hjvalue as accession, b.hjvalue as entryname, c.value as prime, d.value as orderedlocus, e.value as protein, f.text as function " +
		" from " + 
		" entrytype_accession a LEFT OUTER JOIN entrytype_name b ON (a.entrytype_accession_hjid = b.entrytype_name_hjid) " +
		" LEFT OUTER JOIN temp_genename_primary c ON (a.entrytype_accession_hjid = c.hjid) " +
		" LEFT OUTER JOIN temp_genename_orderedlocus d ON (a.entrytype_accession_hjid = d.hjid) " +
		" LEFT OUTER JOIN temp_protein e ON (a.entrytype_accession_hjid = e.hjid) " +
		" LEFT OUTER JOIN temp_comment f ON (a.entrytype_accession_hjid = f.hjid) " + 
		" WHERE entrytype_accession_hjindex = 0; ";
		
		String querySQL = 
				"select hjid, accession, entryname, prime as primary, " +
				"orderedlocus, protein, function " +
				"from temp_uniprot;";
		
		/* JN 2/16/2007 -- 
		 * For A. thaliana to work, I've removed the "NOT NULL" constraint 
		 * on the GeneName field. This may not be appropriate for other
		 * Species and therefore may need to change later. -- let's keep an eye on it.
		 * 
		 */
		tableManager = new TableManager(new String[][] {
				{ "ID", "VARCHAR(50) NOT NULL" },
				{ "EntryName", "VARCHAR(50) NOT NULL" },
				{ "GeneName", "VARCHAR(50)" }, { "ProteinName", "MEMO" },
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
		
		ps = ConnectionManager.getRelationalDBConnection().prepareStatement(createTableSQL);
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

} // end class
