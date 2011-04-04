/********************************************************
 * Filename: DatabaseProfile.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Sub-class this class for a new
 * gene-centric database profile.
 *
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionConfiguration;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities.SystemTablePair;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * DatabaseProfiles contain the SpeciesProfiles for the species supported by that
 * database that are available in our system for export. For example, if the
 * DatabaseProfile is for Uniprot and we have imported data for E.coli and
 * A.thaliana, there would be 2 SpeciesProfiles in the speciesProfilesAvailable
 * array. Once one of those is selected, that SpeciesProfile will be set in
 * the speciesProfile object.
 *
 * Currently, DatabaseProfile objects are created by the ExportToGenMAPP
 * in the static initializer. The DP objects are then updated by getting
 * references to them through static methods in ExportToGenMAPP.
 *
 * @author Joey J. Barrett Class: DatabaseProfile
 * @author Jeffrey Nicholas
 */
public abstract class DatabaseProfile extends Profile {
    // Static Variables -- these are used in the ExportWizard GUI
    public static enum DisplayOrderPreset {
        alphabetical
    };

    public static enum SystemType {
        Proper, Improper, Primary
    };

    protected static Map<String, SystemType> templateDefinedSystemTables = new HashMap<String, SystemType>();
    protected static Map<String, String> templateDefinedSystemToSystemCode = new HashMap<String, String>();

    // Instance Variables
    protected final SpeciesProfile[] speciesProfilesAvailable;
    protected List<SpeciesProfile> speciesProfilesFound = new ArrayList<SpeciesProfile>();
    protected List<String> systemTablesFound = new ArrayList<String>();

    protected String owner;
    protected Date version;
    protected String modSystem;
    protected SpeciesProfile speciesProfile;
    protected Date modify;
    protected DisplayOrderPreset displayOrderPreset;
    protected String displayOrder;
    protected String notes;
    protected String genMAPPDatabase = null; // jn - changed from File to String
    protected ConnectionConfiguration connectionConfiguration = null;
    protected File associationsFile = null;
    protected Map<String, SystemType> systemTables;
    protected String[] relationshipTables;
    protected char chosenAspect;

    protected TableManager primarySystemTableManager = null;
    protected TableManager systemTableManager = null;
    public static URL url = null;

    // Get the systems and system types from the template file.
    static {
        try {
            ConnectionManager.openGenMAPPTemplateDB();
            PreparedStatement ps = ConnectionManager.getGenMAPPTemplateDBConnection().prepareStatement("select System, SystemCode, Misc from Systems");
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String system = result.getString("System").trim();
                String systemCode = result.getString("SystemCode").trim();
                String misc = result.getString("Misc");
                templateDefinedSystemToSystemCode.put(system, systemCode);
                templateDefinedSystemTables.put(system, misc == null ? SystemType.Proper : misc.contains("|I|") ? SystemType.Improper : SystemType.Proper);
            }
            result.close();
            ps.close();
            ConnectionManager.closeGenMAPPTemplateDB();
        } catch(SQLException unhandled) {
            unhandled.printStackTrace();
        } catch(Exception unhandled) {
            unhandled.printStackTrace();
        }
    }

    /**
     * The constructor creates a DatabaseProfile object that contains an array
     * of SpeciesProfile objects.
     *
     * @param name
     * @param description
     * @param speciesProfilesAvailable
     */
    public DatabaseProfile(String name, String description, SpeciesProfile[] speciesProfilesAvailable) {
        super(name, description);
        this.speciesProfilesAvailable = speciesProfilesAvailable;
        this.displayOrderPreset = DisplayOrderPreset.alphabetical;

		//FIXME jn -- TEMP CODE START
//        	URL u = getClass().getResource("/edu/lmu/xmlpipedb/gmbuilder/resource/dbfiles/GeneDBTmpl.mdb");
//        	URI uri = null;
//        	try {
//				 uri = new URI(u.getFile());
//			} catch (URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			uri.getPath();
//			File f = new File(uri.getPath());
//			f.exists();
//			f.getAbsoluteFile();

		//FIXME jn -- TEMP CODE END

    }

    /**
     * Returns whether this profile is available within the given connection. A
     * sub-class must define exactly what needs to be verified to be a valid
     * X-centric database.
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public abstract boolean isAvailable(Connection connection) throws SQLException;

    /**
     * Returns whether this profile matches any other requirements for the
     * X-centric database. This is place to check for species contained in the
     * database.
     *
     * @param connection
     * @throws SQLException
     */
    public abstract void checkRequirements(Connection connection) throws SQLException;

    /**
     * Returns the available species profiles contained in this database.
     *
     * @return
     */
    public SpeciesProfile[] getSpeciesProfilesFound() {
        return speciesProfilesFound.toArray(new SpeciesProfile[0]);
    }

    /**
     * Returns the available display order options.
     *
     * @return
     */
    public DisplayOrderPreset[] getAvailableDisplayOrderPresets() {
        return DisplayOrderPreset.values();
    }

    /**
     * Returns the available system tables found in this database. It is the
     * difference between the tables defined in the template file and the tables
     * found in the database.
     *
     * @return
     */
    public Map<String, SystemType> getAvailableSystemTables() {

        Map<String, SystemType> systemTablesAvailable = new HashMap<String, SystemType>();

        for (String systemTableName : templateDefinedSystemTables.keySet()) {
            if (systemTablesFound.contains(systemTableName)) {
                systemTablesAvailable.put(systemTableName, templateDefinedSystemTables.get(systemTableName));
            }
        }

        systemTablesAvailable.putAll(getDatabaseSpecificSystemTables());
        return systemTablesAvailable;
    }

    /**
     * Returns a map of the database specific system tables. For an example of
     * how to do this look at the sub-classes of this class.
     *
     * @return
     */
    public abstract Map<String, SystemType> getDatabaseSpecificSystemTables();

    /**
     * Returns the relationship tables based on the chosen system tables in the
     * export wizard. It uses the predefined definition for relationship table
     * combinations.
     *
     * @return
     */
    public String[] getAvailableRelationshipTables() {

        List<String> relationshipTablesAvailable = new ArrayList<String>();

        for (Entry<String, SystemType> systemTable1 : systemTables.entrySet()) {
            if (systemTable1.getValue() == SystemType.Primary) {
                for (Entry<String, SystemType> systemTable2 : systemTables.entrySet()) {
                    if (!systemTable1.getKey().equals(systemTable2.getKey()) && !relationshipTablesAvailable.contains(systemTable2.getKey() + "-" + systemTable1.getKey())) {
                        relationshipTablesAvailable.add(0, systemTable1.getKey() + "-" + systemTable2.getKey());
                    }
                }
                break;
            }
        }

        for (Entry<String, SystemType> systemTable1 : systemTables.entrySet()) {
            if (systemTable1.getValue() == SystemType.Proper) {
                for (Entry<String, SystemType> systemTable2 : systemTables.entrySet()) {
                    if (!systemTable1.getKey().equals(systemTable2.getKey()) && !relationshipTablesAvailable.contains(systemTable2.getKey() + "-" + systemTable1.getKey())) {
                        relationshipTablesAvailable.add(relationshipTablesAvailable.size() == 0 ? 0 : relationshipTablesAvailable.size(), systemTable1.getKey() + "-" + systemTable2.getKey());
                    }
                }
            }
        }

        return relationshipTablesAvailable.toArray(new String[0]);
    }

    /**
     * Returns the profile name.
     *
     * @return
     */
    public String getProfileName() {
        return getName();
    }

    /**
     * Returns the profile description.
     *
     * @return
     */
    public String getProfileDescription() {
        return getDescription();
    }

    /**
     * Returns the MODSystem for this X-centric database.
     *
     * @return
     */
    public abstract String getMODSystem();

    /**
     * Returns the display order of the chosen system tables using the display
     * order option chosen in the export wizard.
     *
     * @return
     */
    public abstract String getDefaultDisplayOrder();

    /**
     * Returns the GenMAPP database chosen in the export wizard.
     *
     * @return
     */
    public String getGenMAPPDatabase() {
        return genMAPPDatabase;
    }

    /**
     * Returns the alternate connection chosen in the export wizard. (not
     * finished implementation)
     *
     * @return
     */
    public ConnectionConfiguration getConnectionConfiguration() {
        return connectionConfiguration;
    }

    /**
     * Returns the associations file chosen in the export wizard.
     *
     * @return
     */
    public File getAssociationsFile() {
        return associationsFile;
    }

    /**
     * Returns the aspect chosen in the export wizard.
     *
     * @return
     */
    public char getChosenAspect() {
    	return chosenAspect;
    }

    /**
     * Returns the primary system table for this X-centric database. The primary
     * system table must have a system type of "Primary".
     *
     * @return
     */
    public String getPrimarySystemTable() {
        for (Entry<String, SystemType> systemTable : getDatabaseSpecificSystemTables().entrySet()) {
            if (systemTable.getValue() == SystemType.Primary) {
                return systemTable.getKey();
            }
        }
        return "";
    }

    /**
     * Returns a map of the chosen system tables from the export wizard.
     * System tables may include: UniProt, PDB, Pfam, InterPro, or GeneOntology
     * for UniProt's E.coli database, for example.
     *
     * @return Map
     */
    public Map<String, SystemType> getSystemTables() {
        return systemTables;
    }

    /**
     * Returns the selected species profile chosen in the export wizard.
     *
     * @param selectedProfile
     */
    public SpeciesProfile getSelectedSpeciesProfile() {
        return speciesProfile;
    }

    /**
     * Sets the selected species profile chosen in the export wizard.
     *
     * @param selectedProfile
     */
    public void setSelectedSpeciesProfile(SpeciesProfile selectedProfile) {
        this.speciesProfile = selectedProfile;
    }

    /**
     * Sets the owner string for the Gene Database to be exported.
     *
     * @param owner
     *            The owner string
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Returns the version date for the Gene Database to be exported.
     *
     * @return The version date for the Gene Database to be exported
     */
    public Date getVersion() {
        return this.version;
    }

    /**
     * Sets the version date for the Gene Database to be exported.
     *
     * @param version
     *            The version date
     */
    public void setVersion(Date version) {
        this.version = version;
    }

    /**
     * Sets the MOD system name for the Gene Database to be exported.
     *
     * @param modSystem
     *            The MOD system name
     */
    public void setMODSystem(String modSystem) {
        this.modSystem = modSystem;
    }

    /**
     * Sets the species name for the Gene Database to be exported.
     *
     * @param speciesName
     *            The species name to use
     */
    public void setSpeciesName(String speciesName) {
        speciesProfile.setCustomizedName(speciesName);
    }

    /**
     * Sets the modify date to use for the Gene Database to be exported.
     *
     * @param modify
     *            The modify date to use
     */
    public void setModify(Date modify) {
        this.modify = modify;
    }

    /**
     * Sets the display order string for the Gene Database to be exported.
     *
     * @param displayOrder
     *            The display order string to use
     */
    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Sets the note string for the Gene Database to be exported.
     *
     * @param notes
     *            The notes to include
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Convenience method for automatically selecting the "all" aspect.
     */
    public void setDatabaseProperties(String genMAPPDatabase, ConnectionConfiguration connectionConfiguration) {
        setDatabaseProperties(genMAPPDatabase, connectionConfiguration, 'A');
    }
    
    /**
     * Sets the connections and aspect chosen in the export wizard.
     *
     * @param genMAPPDatabase
     * @param connectionConfiguration
     * @param chosenAspect
     */
    public void setDatabaseProperties(String genMAPPDatabase, ConnectionConfiguration connectionConfiguration, char chosenAspect) {
        this.genMAPPDatabase = genMAPPDatabase;
        this.connectionConfiguration = connectionConfiguration;
        this.chosenAspect = chosenAspect;
    }

    /**
     * Sets the associations file chosen in the import wizard
     */
    public void setGOAProperties(File associationsFile) {
    	this.associationsFile = associationsFile;
    }

    /**
     * Sets the aspect chosen in the export wizard.
     *
     * @param chosenAspect
     */
    public void setChosenAspect(char chosenAspect) {
    	this.chosenAspect = chosenAspect;
    }

    /**
     * Sets the table properties, specifically the system tables (both proper
     * and improper) from the export wizard. ExportPanel3 gets these values
     *
     * @param properSystemTables
     * @param improperSystemTables
     */
    public void setTableProperties(Object[] properSystemTables, Object[] improperSystemTables) {
        systemTables = new HashMap<String, SystemType>();
        for (Object properSystemTable : properSystemTables) {

            if (getPrimarySystemTable().equals((String)properSystemTable)) {
                systemTables.put((String)properSystemTable, SystemType.Primary);
            } else {
                systemTables.put((String)properSystemTable, SystemType.Proper);
            }
        }
        for (Object improperSystemTable : improperSystemTables) {
            systemTables.put((String)improperSystemTable, SystemType.Improper);
        }
    }

    /**
     * Sets the relationship tables chosen in the export wizard.
     *
     * @param relationshipTables
     */
    public void setRelationshipTableProperties(String[] relationshipTables) {
        this.relationshipTables = relationshipTables;
    }

    /**
     * Returns table managers associated with the first pass through the tables
     * to be created. It also updates the export wizard to which tables it is
     * preparing.
     *
     * @return
     * @throws InvalidParameterException
     * @throws Exception
     */
    //JN - 2007-02-24 -- This stuff was in-lined in the ExportToGenMAPP.export()
    //  method a few months ago. It should no longer be needed and I've now
    //  commented it out. If this causes any issues, they should be addressed
    //  by changing the call and/or functionality, but *not* by adding this
    //  back in.
//    public TableManager[] getFirstPassTableManagers() throws SQLException, InvalidParameterException {
//        List<TableManager> tableManagers = new ArrayList<TableManager>();
////      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
////        ExportWizard.updateExportProgress(53, "Preparing tables - Info table...");
//        tableManagers.add(getInfoTableManager());
////      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
////        ExportWizard.updateExportProgress(55, "Preparing tables - Relations table...");
//        tableManagers.add(getRelationsTableManager());
////      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
////        ExportWizard.updateExportProgress(57, "Preparing tables - Other table...");
//        tableManagers.add(getOtherTableManager());
////      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
////        ExportWizard.updateExportProgress(59, "Preparing tables - Systems table...");
//        tableManagers.add(getSystemsTableManager());
////      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
////        ExportWizard.updateExportProgress(61, "Preparing tables - Primary System table...");
//        tableManagers.add(getPrimarySystemTableManager());
////      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
////        ExportWizard.updateExportProgress(63, "Preparing tables - System tables...");
//        tableManagers.add(getSystemTableManager());
////      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
////        ExportWizard.updateExportProgress(65, "Preparing tables - Relationship table...");
//        tableManagers.addAll(getRelationshipTableManager());
//
//        return tableManagers.toArray(new TableManager[0]);
//    }

    /**
     * Returns table managers associated with the second pass through the tables
     * to be created. This is to be defined by a X-centric database.
     *
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public abstract TableManager[] getSecondPassTableManagers() throws SQLException;

    /**
     * Prepares a TableManager for this database.
     *
     * @return TableManager
     * @throws Exception
     */
    public TableManager getInfoTableManager() {
        TableManager tableManager = new TableManager(null, new String[] {});
        tableManager.submit("Info", QueryType.insert, new String[][] { { "Owner", owner }, { "Version", new SimpleDateFormat("yyyyMMdd").format(version) }, { "MODSystem", modSystem }, { "Species", speciesProfile.getSpeciesName() }, { "Modify", new SimpleDateFormat("yyyyMMdd").format(modify) }, { "DisplayOrder", displayOrder }, { "Notes", notes } });
        return tableManager;
    }

    /**
     * Prepares the relations TableManager for this database. If a species
     * specific change is required the getRelationTableManagerCustomization()
     * function is called.
     *
     * @return
     * @throws Exception
     */
    public TableManager getRelationsTableManager() {
        TableManager tableManager = new TableManager(null, new String[] { "SystemCode", "RelatedCode" });
        for (String relationTable : relationshipTables) {
            SystemTablePair stp = GenMAPPBuilderUtilities.parseRelationshipTableName(relationTable);
            // The reason why it is not short-circuit, is that we must process both systemTable1 and systemTable2
            if (speciesProfile.getSpeciesSpecificSystemTables().containsKey(stp.systemTable1) | speciesProfile.getSpeciesSpecificSystemTables().containsKey(stp.systemTable2)) {
                tableManager = speciesProfile.getRelationsTableManagerCustomizations(stp.systemTable1, stp.systemTable2, templateDefinedSystemToSystemCode, tableManager);
            } else {
                tableManager.submit("Relations", QueryType.insert, new String[][] { { "SystemCode", templateDefinedSystemToSystemCode.get(stp.systemTable1) }, { "RelatedCode", templateDefinedSystemToSystemCode.get(stp.systemTable2) }, { "Relation", relationTable }, { "Type", stp.systemTable1.equals(getPrimarySystemTable()) || stp.systemTable2.equals(getPrimarySystemTable()) ? "Direct" : "Inferred" }, { "Source", "" } });
            }
        }

        return tableManager;
    }

    /**
     * Returns a TableManager for the "Other" table.
     *
     * @return
     */
    public TableManager getOtherTableManager() {
        return new TableManager(null, new String[] {});
    }

    /**
     * This function must be implemented by an X-centric database and should the
     * return the systems table manager associated with that database.
     *
     * @return
     * @throws Exception
     */
    public abstract TableManager getSystemsTableManager();

    /**
     * This function must be implemented by an X-centric database and should
     * return a TableManager with the primary system table information
     * associated with that database.
     *
     * @return
     * @throws Exception
     */
    public abstract TableManager getPrimarySystemTableManager() throws SQLException;

    /**
     * This function must be implemented by an X-centric database and should
     * return a TableManager with all system tables.
     *
     * @return
     * @throws InvalidParameterException
     * @throws Exception
     */
    public abstract TableManager getSystemTableManager() throws SQLException, InvalidParameterException;

    /**
     * This function must be implemented by an Xcentric database and should
     * return a TableManager List with all the relationship tables for the
     * database. Relationship tables are tables like: UniProt-EMBL, UniProt-PDB,
     * and UniProt-Blattner. There are likely to be several tables of this type
     * in an Export.
     *
     * @return
     * @throws InvalidParameterException
     * @throws Exception
     */
    public abstract List<TableManager> getRelationshipTableManager() throws SQLException, InvalidParameterException;

    /**
     * Creates the OriginalRowCounts TableManager. This function should be fixed
     * to query the database for which tables have actually been created rather
     * than the few hardcoded values and a compiling of the other tables assumed
     * created.
     *
     * @return
     * @throws Exception
     */
    public TableManager getRowCountsTableManager() throws SQLException {
        TableManager tableManager;

        tableManager = new TableManager(new String[][] { { "\"Table\"", "VARCHAR(50) NOT NULL" }, { "Rows", "VARCHAR(50) NOT NULL" } }, new String[] { "\"Table\"" });

        List<String> allTables = new ArrayList<String>();

        PreparedStatement ps = ConnectionManager.getGenMAPPDBConnection().prepareStatement("select name from MSysObjects where ((type=1) and (flags=0))");
        ResultSet result = ps.executeQuery();

        while (result.next()) {
            allTables.add(result.getString("name").trim());
        }

        String sqlStatement;

        for (String tableName : allTables) {
            sqlStatement = "SELECT Count(*) as count FROM [" + tableName + "]";
            // Alternative query when using a database other than Access.
            // String delimiter = (tableName.indexOf("-") > -1) ? "\"" : "";
            // sqlStatement = "SELECT Count(*) FROM " + delimiter + tableName +
            // delimiter;
            ps = ConnectionManager.getGenMAPPDBConnection().prepareStatement(sqlStatement);
            result = ps.executeQuery();
            while (result.next()) {
                tableManager.submit("OriginalRowCounts", QueryType.insert, new String[][] { { "\"Table\"", tableName }, { "Rows", result.getString("count") } });
            }
        }
        ps.close();

        return tableManager;
    }

    /**
     * Returns the chosen export connection from the export wizard.
     *
     * @return Connection
     * @throws Exception
     */
    public Connection getExportConnection() {
        if (genMAPPDatabase != null) {
            if (ConnectionManager.isGenMAPPDBConnectionOpen()) {
                return ConnectionManager.getGenMAPPDBConnection();
            } else {
                ConnectionManager.openGenMAPPDB(genMAPPDatabase);
                return ConnectionManager.getGenMAPPDBConnection();
            }
        } else if (connectionConfiguration != null) {
            if (ConnectionManager.isGenMAPPDBConnectionOpen()) {
                return ConnectionManager.getGenMAPPDBConnection();
            } else {
                ConnectionManager.openGenMAPPDB(connectionConfiguration);
                return ConnectionManager.getGenMAPPDBConnection();
            }
        }
        return null;
    }
}
