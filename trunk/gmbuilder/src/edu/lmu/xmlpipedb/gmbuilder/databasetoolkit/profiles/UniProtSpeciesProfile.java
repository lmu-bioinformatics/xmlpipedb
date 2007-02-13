/********************************************************
 * Filename: UniProtSpeciesProfile.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: This an instance of a custom species
 * definition for a UniProt centric database.
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

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities.SystemTablePair;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author Joey J. Barrett
 * Class: UniProtSpeciesProfile
 */
public class UniProtSpeciesProfile extends SpeciesProfile {

	/**
	 * Creates a custom species profile for a UniProt centric database.
	 * @param name
	 * @param description
	 */
	public UniProtSpeciesProfile(String name, String description) {
		super(name, description);
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemTables()
	 */
	@Override
	protected Map<String, SystemType> getSpeciesSpecificSystemTables() {
		Map<String, SystemType> speciesSpecificAvailableSystemTables = 
			new HashMap<String, SystemType>();
	
		speciesSpecificAvailableSystemTables.put("OrderedLocusNames", SystemType.Proper);
		return speciesSpecificAvailableSystemTables;
	}

	/**
     * @throws InvalidParameterException 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      java.util.Date)
     */
    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        // TODO This is virtually identical to the e. coli version; find a way to unify.
        // TODO (for that matter, find a way to unify the whole thing)
    	
    	ArrayList<String> comparisonList = new ArrayList<String>(2);
    	comparisonList.add("ordered locus");
    	comparisonList.add("ORF");
    	
    	tableManager = systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, "OrderedLocusNames", comparisonList );
    	
/*        PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT value, type " + "FROM genenametype INNER JOIN entrytype_genetype " + "ON (entrytype_genetype_name_hjid = entrytype_genetype.hjid) " + "WHERE entrytype_gene_hjid = ?");
        ResultSet result;

        for (Row row : primarySystemTableManager.getRows()) {
            ps.setString(1, row.getValue("UID"));
            result = ps.executeQuery();

            // We actually want to keep the case where multiple ordered locus names appear.
            while (result.next()) {
                String type = result.getString("type");
                if ("ordered locus".equals(type) || "ORF".equals(type)) {
                    // We want this name to appear in the OrderedLocusNames system table.
                    for (String id : result.getString("value").split("/")) {
                        tableManager.submit("OrderedLocusNames", QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", GenMAPPBuilderUtilities.getSystemsDateString(version) }, { "UID", row.getValue("UID") } });
                    }
                }
            }
        }*/

        return tableManager;
    }

	/**
     * @param comparisonList TODO
	 * @throws InvalidParameterException 
	 * @throws SQLException 
	 * @throws Exception 
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      java.util.Date)
     */
    protected TableManager systemTableManagerCustomizationsHelper(TableManager tableManager, TableManager primarySystemTableManager, Date version, String substituteTable, ArrayList<String> comparisonList ) throws InvalidParameterException, SQLException  {
    	if( comparisonList == null ) throw new InvalidParameterException("comparisonList may not be null. Ensure you are passing a valid ArrayList<String>, even if it is empty.");
    	PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT value, type " + "FROM genenametype INNER JOIN entrytype_genetype " + "ON (entrytype_genetype_name_hjid = entrytype_genetype.hjid) " + "WHERE entrytype_gene_hjid = ?");
        ResultSet result;

        for (Row row : primarySystemTableManager.getRows()) {
            ps.setString(1, row.getValue("UID"));
            result = ps.executeQuery();

            // We actually want to keep the case where multiple ordered locus names appear.
            while (result.next()) {
                String type = result.getString("type");
                //if ("ordered locus".equals(type) || "ORF".equals(type)) {
                if (comparisonList.contains(type)) {
                    // We want this name to appear in the OrderedLocusNames system table.
                    for (String id : result.getString("value").split("/")) {
                        tableManager.submit(substituteTable, QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", GenMAPPBuilderUtilities.getSystemsDateString(version) }, { "UID", row.getValue("UID") } });
                    }
                }
            }
        }

        return tableManager;
    }
    
	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getRelationsTableManagerCustomizations(java.lang.String, java.lang.String, java.util.Map, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
	 */
	@Override
	public TableManager getRelationsTableManagerCustomizations(
			String systemTable1, String systemTable2, 
			Map<String, String> templateDefinedSystemToSystemCode, 
			TableManager tableManager) {
		
		//'UniProt-GeneOntology
		
		String relation = systemTable1 + "-" + systemTable2;
		String type = null;

		if( systemTable1.equals("UniProt") || systemTable2.equals("UniProt") ){
			type = "Direct";
		} else {
			type = "Inferred"; 
		}
		
		tableManager.submit("Relations", QueryType.insert, 				
				new String[][] {
					{ "SystemCode", systemTable1 },
					{"RelatedCode", systemTable2 },
					{ "Relation", relation },
					{ "Type", type }, 
					{ "Source", "" }
				}
			);
		
		return tableManager;
	}
	
	/**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificRelationshipTable(java.lang.String,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
     */
    @Override
    public TableManager getSpeciesSpecificRelationshipTable(String relationshipTable, TableManager uniprotTableManager, TableManager systemTableManager, TableManager tableManager) throws SQLException {
    	
    	tableManager = speciesSpecificRelationshipTableHelper(relationshipTable, uniprotTableManager, systemTableManager, tableManager, "OrderedLocusNames", "Bridge", "S");
    	
/*        SystemTablePair stp = GenMAPPBuilderUtilities.parseRelationshipTableName(relationshipTable);
        if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable1)) {
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT id " + "FROM dbreferencetype " + "WHERE type = ? and " + "entrytype_dbreference_hjid = ?");
            ps.setString(1, stp.systemTable2);
            ResultSet result;
            for (Row row : systemTableManager.getRows()) {
                if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals("OrderedLocusNames")) {
                    ps.setString(2, row.getValue("UID"));
                    result = ps.executeQuery();
                    while (result.next()) {
                        tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", row.getValue("ID") }, { "Related", result.getString("id") },
                        // TODO This is hard-coded. Fix it.
                        { "Bridge", "S" } });
                    }
                }
            }
            ps.close();
        } else if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable2) && !stp.systemTable1.equals("UniProt")) {
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT entrytype_dbreference_hjid, id " + "FROM dbreferencetype where type = ?");
            ps.setString(1, stp.systemTable1);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String primary = result.getString("id");
                String related = result.getString("entrytype_dbreference_hjid");

                for (Row row : systemTableManager.getRows()) {
                    if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals("OrderedLocusNames") && row.getValue("UID").equals(related)) {
                        for (String id : row.getValue("ID").split("/")) {
                            tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", primary }, { "Related", id },
                            // TODO This is hard-coded. Fix it.
                            { "Bridge", "S" } });
                        }
                    }
                }
            }
            ps.close();
        } else {
            for (Row row1 : systemTableManager.getRows()) {
                if (row1.getValue(TableManager.TABLE_NAME_COLUMN).equals("OrderedLocusNames")) {
                    for (Row row2 : uniprotTableManager.getRows()) {
                        if (row2.getValue("UID").equals(row1.getValue("UID"))) {
                            tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", row2.getValue("ID") }, { "Related", row1.getValue("ID") },
                            //TODO This is hard-coded.  Fix it. 
                            { "Bridge", "S" } });
                        }
                    }
                }
            }
        }*/

        return tableManager;
    }

	/**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificRelationshipTable(java.lang.String,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
     */
    public TableManager speciesSpecificRelationshipTableHelper(String relationshipTable, TableManager uniprotTableManager, TableManager systemTableManager, TableManager tableManager, String criteria, String finalColumnName, String finalColumnValue) throws SQLException {
        		//Separate the String relationshipTable into two parts based on the dash ("-") in the String
		// Store the two parts as systemTable1 and systemTable2 in the SystemTablePair object
        SystemTablePair stp = GenMAPPBuilderUtilities.parseRelationshipTableName(relationshipTable);

		// Get a Map, which in the case of E.coli only contains one entry (for Blattner) as a "proper" system table
		//   The gist of the following if / elseif / else block is:
		//     if      stp.systemTable1 == Blatter, then do some stuff
		//     elseif  stp.systemTable2 == Blatter AND systemTable1 is NOT UniProt, then do some other stuff
		//     else    do some different stuff
		

        if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable1)) {
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT id " + "FROM dbreferencetype " + "WHERE type = ? and " + "entrytype_dbreference_hjid = ?");
            ps.setString(1, stp.systemTable2);
            ResultSet result;
            for (Row row : systemTableManager.getRows()) {
                if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals(criteria)) {
                    ps.setString(2, row.getValue("UID"));
                    result = ps.executeQuery();
                    while (result.next()) {
                        tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", row.getValue("ID") }, { "Related", result.getString("id") },
                        // TODO This is hard-coded. Fix it.
                        { finalColumnName, finalColumnValue } });
                    }
                }
            }
            ps.close();
        } else if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable2) && !stp.systemTable1.equals("UniProt")) {
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT entrytype_dbreference_hjid, id " + "FROM dbreferencetype where type = ?");
            ps.setString(1, stp.systemTable1);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String primary = result.getString("id");
                String related = result.getString("entrytype_dbreference_hjid");

                for (Row row : systemTableManager.getRows()) {
                    if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals(criteria) && row.getValue("UID").equals(related)) {
                        for (String id : row.getValue("ID").split("/")) {
                            tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", primary }, { "Related", id },
                            // TODO This is hard-coded. Fix it.
                            { finalColumnName, finalColumnValue } });
                        }
                    }
                }
            }
            ps.close();
        } else {
    			// Go through each row (AKA row1) in the systemTableManager that was passed in, checking for "Blattner"
    			//  If we find "Blattner", go through every row (AKA row2) in the primarySystemTableManger
    			//    check if row1's UID value is the same as row2's UID value,
    			//		if yes, loop some more!! (yippee), this time we are getting the value of the ID field,
    			//			then we'll write out a record.

            for (Row row1 : systemTableManager.getRows()) {
                if (row1.getValue(TableManager.TABLE_NAME_COLUMN).equals(criteria)) {
                    for (Row row2 : uniprotTableManager.getRows()) {
                        if (row2.getValue("UID").equals(row1.getValue("UID"))) {
                            tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", row2.getValue("ID") }, { "Related", row1.getValue("ID") },
                            //TODO This is hard-coded.  Fix it. 
                            { finalColumnName, finalColumnValue } });
                        }
                    }
                }
            }
        }

        return tableManager;
    }

    
	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemCode(java.util.List, java.util.Map)
	 */
	@Override
	public List<String> getSpeciesSpecificSystemCode(List<String> systemCodes, Map<String, String> templateDefinedSystemToSystemCode) {
		systemCodes.add(2, templateDefinedSystemToSystemCode.get("OrderedLocusNames"));
		return systemCodes;
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemsTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
	 */
	@Override
	public TableManager getSystemsTableManagerCustomizations(TableManager tableManager, DatabaseProfile dbProfile) {

		// there was some code here that was E. coli specific. It has been moved
		// to the same method in the E. coli SpeciceProfile
		return tableManager;
	}
}
