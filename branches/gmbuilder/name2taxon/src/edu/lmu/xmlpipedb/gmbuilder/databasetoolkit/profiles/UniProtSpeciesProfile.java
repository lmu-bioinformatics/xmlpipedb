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

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.resource.properties.AppResources;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities.SystemTablePair;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author Joey J. Barrett
 * Class: UniProtSpeciesProfile
 */
public class UniProtSpeciesProfile extends SpeciesProfile {
	private static final Log _Log = LogFactory.getLog(UniProtSpeciesProfile.class);

	/**
	 * Creates a custom species profile for a UniProt centric database.
	 * @param name
	 * @param description
	 */
	public UniProtSpeciesProfile(String name, String description) {
		super(name, description);
	}

	/**
	 * Creates a custom species profile for a UniProt centric database,
	 * including the taxonID in addition to the name and description
	 *
	 * @param name
	 * @param taxonID
	 * @param description
	 */
	public UniProtSpeciesProfile(String name, int taxonID, String description){
		super(name, taxonID, description);
	}
	
	/**
	 * Creates a custom species profile for a UniProt centric database,
	 * including the taxonID in addition to the description but excluding
	 * a species name
	 *
	 * @param taxonID
	 * @param description
	 */
	public UniProtSpeciesProfile(int taxonID, String description){
		super(null, taxonID, description);
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
        /*
         * This method is only called (and therefore this bit 'o logic is only
         * invoked) when the species specific class has not overridden this
         * method.
         */
        List<String> comparisonList = new ArrayList<String>(1);
        comparisonList.add("ordered locus");

        return systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, "OrderedLocusNames", comparisonList);
    }

    /**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.UniProtSpeciesProfile#systemTableManagerCustomizationsHelper
     */
    protected TableManager systemTableManagerCustomizationsHelper(TableManager tableManager, TableManager primarySystemTableManager, Date version, String substituteTable, List<String> comparisonList) throws InvalidParameterException, SQLException {
    	return systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, substituteTable, comparisonList, "");
    }

	/**
	 * Updates the tableManger passed in with values from the dbreferencestype
	 * table (which holds the <dbreferences type='XXX'> information. The exact
	 * system table that is being generated is specified by the substituteTable
	 * parameter. Uses a filter to not allow certain ids into the table.  The filter
	 * must be a Java compatable regex.
	 *
     * @param comparisonList
     * @param filter
	 * @throws InvalidParameterException
	 * @throws SQLException
	 * @throws Exception
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      java.util.Date)
     */
   	protected TableManager systemTableManagerCustomizationsHelper(TableManager tableManager, TableManager primarySystemTableManager, Date version, String substituteTable, List<String> comparisonList, String filter) throws InvalidParameterException, SQLException {
    	if (comparisonList == null) {
            throw new InvalidParameterException("comparisonList may not be null. Ensure you are passing a valid ArrayList<String>, even if it is empty.");
    	}

        PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT value, type " +
            "FROM genenametype INNER JOIN entrytype_genetype " +
            "ON (entrytype_genetype_name_hjid = entrytype_genetype.hjid) " +
            "WHERE entrytype_gene_hjid = ?");
        ResultSet result;

        for (Row row : primarySystemTableManager.getRows()) {
            ps.setInt(1, Integer.parseInt(row.getValue("UID")));
            result = ps.executeQuery();

            // We actually want to keep the case where multiple ordered locus
            // names appear.
            while (result.next()) {
                String type = result.getString("type");
                if (comparisonList.contains(type)) {
                    // We want this name to appear in the system table indicated
                    // by the substituteTable variable.
                    for (String id : result.getString("value").split("/")) {

                    	// Only add the ids that do not match the filter
                    	if (!id.matches(filter)) {
                            tableManager.submit(substituteTable, QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", GenMAPPBuilderUtilities.getSystemsDateString(version) }, { "UID", row.getValue("UID") } });
                    	}
                    }
                }
            }
        }

        return tableManager;
    }

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getRelationsTableManagerCustomizations(java.lang.String, java.lang.String, java.util.Map, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
	 */
	@Override
	public TableManager getRelationsTableManagerCustomizations(
			String systemTable1, String systemTable2,
			Map<String, String> templateDefinedSystemToSystemCode,
			TableManager tableManager) {
	    String relation = systemTable1 + "-" + systemTable2;
        String type = null;

        if ("UniProt".equals(systemTable1) || "UniProt".equals(systemTable2)) {
            type = "Direct";
        } else {
            type = "Inferred";
        }

        tableManager.submit("Relations", QueryType.insert, new String[][] {
            { "SystemCode", templateDefinedSystemToSystemCode.get(systemTable1) },
            { "RelatedCode", templateDefinedSystemToSystemCode.get(systemTable2) },
            { "Relation", relation },
            { "Type", type },
            { "Source", "" }
        });

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
        return tableManager;
    }

	/**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificRelationshipTable(java.lang.String,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
     */
    public TableManager speciesSpecificRelationshipTableHelper(String relationshipTable, TableManager uniprotTableManager, TableManager systemTableManager, TableManager tableManager, String criteria, String finalColumnName, String finalColumnValue) throws SQLException {
        // Separate the String relationshipTable into two parts based on the dash ("-") in the String
        // Store the two parts as systemTable1 and systemTable2 in the SystemTablePair object
        SystemTablePair stp = GenMAPPBuilderUtilities.parseRelationshipTableName(relationshipTable);

		// Get a Map, which in the case of E.coli only contains one entry (for Blattner) as a "proper" system table
		//   The gist of the following if / elseif / else block is:
		//     if      stp.systemTable1 == Blatter, then do some stuff
		//     elseif  stp.systemTable2 == Blatter AND systemTable1 is NOT UniProt, then do some other stuff
        //	   elseif  stp.systemTable1 == Species System Table AND stp.systemTable2 == Species System Table, do the good stuff
		//     else    do some different stuff
        //   NOTE: In the non-E.coli case, e.g. A.thaliana, substitute the term TAIR for Blattner, above.

        _Log.debug("HERE IS THE TABLE: " + relationshipTable);
        if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable1) &&
        		!getSpeciesSpecificSystemTables().containsKey(stp.systemTable2)) {
        	_Log.debug("THIS IS WHERE IT COMES OUT1: " + relationshipTable);
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT id " + "FROM dbreferencetype " + "WHERE type = ? and " + "entrytype_dbreference_hjid = ?");
            ps.setString(1, stp.systemTable2);
            ResultSet result;
            for (Row row : systemTableManager.getRows()) {
                if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals(criteria)) {
                    ps.setInt(2, Integer.parseInt(row.getValue("UID")));
                    result = ps.executeQuery();
                    while (result.next()) {
                        tableManager.submit(relationshipTable,
                            QueryType.insert, new String[][] {
                                { "\"Primary\"", GenMAPPBuilderUtilities.checkAndPruneVersionSuffix(stp.systemTable1, row.getValue("ID")) },
                                { "Related", GenMAPPBuilderUtilities.checkAndPruneVersionSuffix(stp.systemTable2, result.getString("id")) },
                                // TODO This is hard-coded. Fix it.
                                { finalColumnName, finalColumnValue }
                            }
                        );
                    }
                }
            }
            ps.close();

         // Handle the case when it is Species-Species
        } else if(getSpeciesSpecificSystemTables().containsKey(stp.systemTable1) &&
        		getSpeciesSpecificSystemTables().containsKey(stp.systemTable2)) {
        	_Log.debug("THIS IS WHERE IT COMES OUT3: " + relationshipTable);
        	// Maps to contain the primary, related ids of the species specific tables
        	HashMap<String, String> ss1 = new HashMap<String, String>();
        	HashMap<String, String> ss2 = new HashMap<String, String>();

        	for (Row row : systemTableManager.getRows()) {

        		// Load up the proper maps so we can begin searching for matching UIDs
                if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals(stp.systemTable1) &&
                		row.getValue("UID") != null) {
                   	ss1.put(row.getValue("UID"), row.getValue("ID"));
                   	_Log.debug(row.getValue("UID") + " NO " + row.getValue("ID"));
                } else if(row.getValue(TableManager.TABLE_NAME_COLUMN).equals(stp.systemTable2) &&
                		row.getValue("UID") != null) {
                	ss2.put(row.getValue("UID"), row.getValue("ID"));
                	_Log.debug(row.getValue("UID") + " YES " + row.getValue("ID"));
                }

            }


        	// Now we just find the UIDs that are in ss1 and ss2 and load the proper
        	// relationship table
        	Set<String> uids = ss1.keySet();
        	for(String uid : uids) {
        		System.out.println("uid : " + uid);
        		if(ss2.containsKey(uid)) {

        			_Log.debug("Added related id " + ss2.get(uid) + " for primary " + ss1.get(uid) + "to table " + stp);
        			System.out.println("Added related id " + ss2.get(uid) + " for primary " + ss1.get(uid) + "to table " + stp);
        			tableManager.submit(relationshipTable,
                            QueryType.insert, new String[][] {
                                { "\"Primary\"", GenMAPPBuilderUtilities.checkAndPruneVersionSuffix(stp.systemTable1, ss1.get(uid)) },
                                { "Related", GenMAPPBuilderUtilities.checkAndPruneVersionSuffix(stp.systemTable2, ss2.get(uid)) },
                                // TODO This is hard-coded. Fix it.
                                { finalColumnName, finalColumnValue }
                            }
                        );
        		}
        	}

        } else if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable2) && !stp.systemTable1.equals("UniProt")) {
        	_Log.debug("THIS IS WHERE IT COMES OUT2: " + relationshipTable);
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT entrytype_dbreference_hjid, id " + "FROM dbreferencetype where type = ?");
            ps.setString(1, stp.systemTable1);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String primary = result.getString("id");
                String related = result.getString("entrytype_dbreference_hjid");

                for (Row row : systemTableManager.getRows()) {
                	_Log.debug("\nCriteria: [" + criteria + "]" +
                			  "\nRelated: [" + related + "]" +
                			  "\nRow Table Name Column value: [" + row.getValue(TableManager.TABLE_NAME_COLUMN) + "]" +
                			  "\nRow UID value: [" + row.getValue("UID") + "]");
                    if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals(criteria)
                    		&& row.getValue("UID") != null
                    		&& row.getValue("UID").equals(related)) {
                        for (String id : row.getValue("ID").split("/")) {
                            tableManager.submit(relationshipTable,
                                QueryType.insert, new String[][] {
                                    { "\"Primary\"", GenMAPPBuilderUtilities.checkAndPruneVersionSuffix(stp.systemTable1, primary) },
                                    { "Related", GenMAPPBuilderUtilities.checkAndPruneVersionSuffix(stp.systemTable2, id) },
                                    // TODO This is hard-coded. Fix it.
                                    { finalColumnName, finalColumnValue }
                                }
                            );
                        }
                    }
                }
            }
            ps.close();

        } else {
        	_Log.debug("THIS IS WHERE IT COMES OUT4: " + relationshipTable);
			// Go through each row (AKA row1...) in the systemTableManager that was passed in, checking for "Blattner"
			//  If we find "Blattner", go through every row (AKA row2) in the primarySystemTableManger (AKA UniProt table)
			//    check if row1's UID value is the same as row2's UID value,
			//		if yes, loop some more!! (yippee), this time we are getting the value of the ID field,
			//			then we'll write out a record.
            for (Row row1 : systemTableManager.getRows()) {
                if (row1.getValue(TableManager.TABLE_NAME_COLUMN).equals(criteria)) {
                    for (Row row2 : uniprotTableManager.getRows()) {
                        if (row2.getValue("UID").equals(row1.getValue("UID"))) {
                        	_Log.debug("Row 2 value: [" + row2.getValue("UID") + "] equaled Row 1 value: [" + row1.getValue("UID") + "]");

                            tableManager.submit(relationshipTable,
                                QueryType.insert, new String[][] {
                                    { "\"Primary\"", GenMAPPBuilderUtilities.checkAndPruneVersionSuffix(stp.systemTable1, row2.getValue("ID")) },
                                    { "Related", GenMAPPBuilderUtilities.checkAndPruneVersionSuffix(stp.systemTable2, row1.getValue("ID")) },
                                    // TODO This is hard-coded.  Fix it.
                                    { finalColumnName, finalColumnValue }
                                }
                            );
                        }
                    }
                }
            }
        }

        return tableManager;
    }


	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemCode(java.util.List, java.util.Map)
	 */
	@Override
	public List<String> getSpeciesSpecificSystemCode(List<String> systemCodes, Map<String, String> templateDefinedSystemToSystemCode) {
		systemCodes.add(2, templateDefinedSystemToSystemCode.get("OrderedLocusNames"));
		return systemCodes;
	}

	/**
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemsTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile)
	 */
	@Override
	public TableManager getSystemsTableManagerCustomizations(TableManager tableManager, DatabaseProfile dbProfile) {
		return tableManager;
	}

	@Override
	public TableManager getPrimarySystemTableManagerCustomizations(Date version) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Queries the configured database for the name of the species.  If there are more than one species
	 * loaded into the database, the first record will be used.
	 *
	 * @return the name of the species
	 * @throws SQLException
	 */
	public static String getSpeciesNameDB() {

		String name = "";

		try {

			ResultSet result;
			PreparedStatement ps;
			ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
					AppResources.optionString("SpeciesNameLocationQuery"));

			result = ps.executeQuery();
			name = result.getString(0);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // We will only get back on column
		return name;
	}

	public static String getSpeciesNameXML(InputStream inputStream) {
		return null;
	}

}
