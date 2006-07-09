/********************************************************
 * Filename: CustomUniProtSpeciesProfile.java
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
 * @author Joey J. Barrett
 * Class: EscherichiaColiUniProtSpeciesProfile
 */
public class EscherichiaColiUniProtSpeciesProfile extends SpeciesProfile {

	public EscherichiaColiUniProtSpeciesProfile() {
		super("Escherichia coli", "This profile defines the requirements for " +
				"an Escherichia Coli species within a UniProt database.");
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemTables()
	 */
	@Override
	protected Map<String, SystemType> getSpeciesSpecificSystemTables() {
		Map<String, SystemType> speciesSpecificAvailableSystemTables = 
				new HashMap<String, SystemType>();
		
		speciesSpecificAvailableSystemTables.put("Blattner", SystemType.Proper);
		return speciesSpecificAvailableSystemTables;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getRelationsTableManagerCustomizations(java.lang.String, java.lang.String, java.util.Map, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager)
	 */
	@Override
	public TableManager getRelationsTableManagerCustomizations(
			String systemTable1, String systemTable2, 
			Map<String, String> templateDefinedSystemToSystemCode, 
			TableManager tableManager) throws Exception {
		
			tableManager.submit("Relations", QueryType.insert, new String[][] {
					{"SystemCode", templateDefinedSystemToSystemCode.get(!systemTable1.equals("Blattner") ? systemTable1 : "OrderedLocusNames")},
					{"RelatedCode", templateDefinedSystemToSystemCode.get(!systemTable2.equals("Blattner") ? systemTable2 : "OrderedLocusNames")},
					{"Relation", systemTable1 + "-" + systemTable2},
					{"Type", systemTable1.equals("UniProt") ||
						systemTable2.equals("UniProt") ? "Direct" : "Inferred"},
					{"Source", ""}
			});
		return tableManager;
	}

	/**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemsTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, DatabaseProfile)
     */
    @Override
    public TableManager getSystemsTableManagerCustomizations(TableManager tableManager, DatabaseProfile dbProfile) throws Exception {
        tableManager.submit("Systems", QueryType.update, new String[][] { { "SystemCode", "Ln" }, { "System", "Blattner" } });
        tableManager.submit("Systems", QueryType.update, new String[][] { { "SystemCode", "Ln" }, { "SystemName", "Blattner" } });
        tableManager.submit("Systems", QueryType.update, new String[][] { { "SystemCode", "Ln" }, { "Species", "|" + getSpeciesName() + "|"} });
        tableManager.submit("Systems", QueryType.update, new String[][] { { "SystemCode", "Ln" }, { "\"Date\"", GenMAPPBuilderUtilities.getSystemsDateString(dbProfile.getVersion()) } });
        return tableManager;
    }

	/**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager,
     *      java.util.Date)
     */
    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws Exception {
        PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT value, type " + "FROM genenametype INNER JOIN entrytype_genetype " + "ON (entrytype_genetype_name_hjid = entrytype_genetype.hjid) " + "WHERE entrytype_gene_hjid = ?");
        ResultSet result;

        for (Row row : primarySystemTableManager.getRows()) {
            ps.setString(1, row.getValue("UID"));
            result = ps.executeQuery();
            Map<String, String> typeToValue = new HashMap<String, String>();
            while (result.next()) {
                typeToValue.put(result.getString("type"), result.getString("value"));
            }

            String ids = typeToValue.get("ordered locus") != null ? typeToValue.get("ordered locus") : typeToValue.get("primary") != null ? typeToValue.get("primary") : typeToValue.get("synonym");
            for (String id : ids.split("/")) {
                tableManager.submit("Blattner", QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", GenMAPPBuilderUtilities.getSystemsDateString(version) }, { "UID", row.getValue("UID") } });
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
    public TableManager getSpeciesSpecificRelationshipTable(String relationshipTable, TableManager primarySystemTableManager, TableManager systemTableManager, TableManager tableManager) throws SQLException, Exception {
        SystemTablePair stp = GenMAPPBuilderUtilities.parseRelationshipTableName(relationshipTable);

        // Blattner-X
        if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable1)) {
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT id " + "FROM dbreferencetype " + "WHERE type = ? and " + "entrytype_dbreference_hjid = ?");
            ps.setString(1, stp.systemTable2);
            ResultSet result;
            for (Row row : systemTableManager.getRows()) {
                if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals("Blattner")) {
                    ps.setString(2, row.getValue("UID"));
                    result = ps.executeQuery();
                    while (result.next()) {
                        // Fix blattner IDs of the form xxxx/yyyy
                        for (String Blattner_ID : row.getValue("ID").split("/")) {
                            tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", Blattner_ID }, { "Related", result.getString("id") },
                            // TODO This is hard-coded. Fix it.
                            { "Bridge", "S" } });
                        }
                    }
                }
            }
            ps.close();

            // X-Blattner, excluding UniProt-Blattner
        } else if (getSpeciesSpecificSystemTables().containsKey(stp.systemTable2) && !stp.systemTable1.equals("UniProt")) {
            PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT entrytype_dbreference_hjid, id " + "FROM dbreferencetype where type = ?");
            ps.setString(1, stp.systemTable1);
            ResultSet result = ps.executeQuery();

            String primary = "";
            String related = "";
            while (result.next()) {
                primary = result.getString("id");
                related = result.getString("entrytype_dbreference_hjid");
                for (Row row : systemTableManager.getRows()) {
                    if (row.getValue(TableManager.TABLE_NAME_COLUMN).equals("Blattner") && row.getValue("UID").equals(primary)) {
                        for (String Blattner_ID : row.getValue("ID").split("/")) {
                            tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", related }, { "Related", Blattner_ID },
                            // TODO This is hard-coded. Fix it.
                            { "Bridge", "S" } });
                        }
                    }
                }
            }
            ps.close();
        } else {
            for (Row row1 : systemTableManager.getRows()) {
                if (row1.getValue(TableManager.TABLE_NAME_COLUMN).equals("Blattner")) {
                    for (Row row2 : primarySystemTableManager.getRows()) {
                        if (row1.getValue("UID").equals(row2.getValue("UID"))) {
                            for (String Blattner_ID : row1.getValue("ID").split("/")) {
                                tableManager.submit(relationshipTable, QueryType.insert, new String[][] { { "\"Primary\"", row2.getValue("ID") }, { "Related", Blattner_ID },
                                //TODO This is hard-coded.  Fix it. 
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
	
	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile#getSpeciesSpecificSystemCode(java.util.List, java.util.Map)
	 */
	@Override
	public List<String> getSpeciesSpecificSystemCode(List<String> systemCodes, Map<String, String> templateDefinedSystemToSystemCode) {
		systemCodes.add(2, templateDefinedSystemToSystemCode.get("OrderedLocusNames"));
		return systemCodes;
	}
}
