/********************************************************
 * Filename: ExportToGenMAPP.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Manages the export process as a whole.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go.ExportGoData;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportWizard;

/**
 * @author Joey J. Barrett Class: ExportToGenMAPP
 */
public class ExportToGenMAPP {

    private static DatabaseProfile[] availableDatabaseProfiles;
    private static DatabaseProfile selectedDatabaseProfile;

    private static final Log _Log = LogFactory.getLog(ExportToGenMAPP.class);

    /**
     * Opens the relational database connection and checks for available
     * database profiles.
     * 
     * @param hibernateConfiguration
     * @throws Exception
     */
    public static void init(Configuration hibernateConfiguration) throws Exception {
        // Open a connection to the relational database.
        ConnectionManager.openRelationalDB(hibernateConfiguration);

        // Inspect the database. Find out what profiles it matches.
        availableDatabaseProfiles = DatabaseInspector.init(ConnectionManager.getRelationalDBConnection());
    }

    /**
     * The available database profiles.
     * 
     * @return
     */
    public static DatabaseProfile[] getAvailableDatabaseProfiles() {
        return availableDatabaseProfiles;
    }

    /**
     * Sets the selected (chosen) database profile.
     * 
     * @param selectedProfile
     */
    public static void setDatabaseProfile(DatabaseProfile selectedProfile) {
        selectedDatabaseProfile = selectedProfile;
    }

    /**
     * Gets the selected (chosen) database profile.
     * 
     * @return
     */
    public static DatabaseProfile getDatabaseProfile() {
        return selectedDatabaseProfile;
    }

    /**
     * Runs the export on the selectedDatabaseProfile.
     * 
     * @throws Exception
     */
    public static void export() throws ClassNotFoundException, SQLException, HibernateException, SAXException, IOException, JAXBException {
        ExportWizard.updateExportProgress(1, "Starting GeneOntology export...");
        (new ExportGoData(selectedDatabaseProfile.getExportConnection())).export(selectedDatabaseProfile.getAssociationsFile());

        ExportWizard.updateExportProgress(50, "Finished GeneOntology export...");
        ExportWizard.updateExportProgress(51, "Starting first pass table creation...");

        // original code by Joey Barrett
//        _Log.info("Getting first-pass table managers");
//        TableManager[] firstPass = selectedDatabaseProfile.getFirstPassTableManagers();
//        _Log.info("Exporting first-pass tables");
//        TableCoordinator.exportTables(selectedDatabaseProfile.getExportConnection(), firstPass);
        
        // JN - in-lining of calls with immediate write to gmb 
        _Log.info("Getting first-pass table managers");
        ExportWizard.updateExportProgress(53, "Preparing tables - Info table...");
        TableManager tmA = selectedDatabaseProfile.getInfoTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmA);
        
        ExportWizard.updateExportProgress(55, "Preparing tables - Relations table...");
        TableManager tmB = selectedDatabaseProfile.getRelationsTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmB);
        
        ExportWizard.updateExportProgress(57, "Preparing tables - Other table...");
        TableManager tmC = selectedDatabaseProfile.getOtherTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmC);
        
        ExportWizard.updateExportProgress(59, "Preparing tables - Systems table...");
        TableManager tmD = selectedDatabaseProfile.getSystemsTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmD);
        
        ExportWizard.updateExportProgress(61, "Preparing tables - Primary System table...");
        TableManager tmE = selectedDatabaseProfile.getPrimarySystemTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmE);
        
        ExportWizard.updateExportProgress(63, "Preparing tables - System tables...");
        TableManager tmF = selectedDatabaseProfile.getSystemTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmF);
        
        ExportWizard.updateExportProgress(65, "Preparing tables - Relationship table...");
        List<TableManager> tmG = selectedDatabaseProfile.getRelationshipTableManager();
        TableCoordinator.exportTables(selectedDatabaseProfile.getExportConnection(), (TableManager[])tmG.toArray());
        // end in-lining
        
        ExportWizard.updateExportProgress(66, "Starting second pass table creation...");
        _Log.info("Getting second-pass table managers");
        TableManager[] secondPass = selectedDatabaseProfile.getSecondPassTableManagers();
        _Log.info("Exporting second-pass tables");
        TableCoordinator.exportTables(selectedDatabaseProfile.getExportConnection(), secondPass);

        ExportWizard.updateExportProgress(66, "Preparing table - OriginalRowCounts table...");
        _Log.info("Getting row counts table manager");
        TableManager rowCounts = selectedDatabaseProfile.getRowCountsTableManager();
        _Log.info("Exporting row counts tables");
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), rowCounts);

        _Log.info("Done with ExportToGenMAPP.export()");
    }

    /**
     * Cleans up anything left from the export.
     * 
     * @throws SQLException
     */
    public static void cleanup() throws SQLException {
        ConnectionManager.closeAll();
    }
}
