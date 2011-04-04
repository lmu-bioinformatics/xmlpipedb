/********************************************************
 * Filename: ImportGOA.java
 * Author: Joey J. Barrett
 * Adaptation: Don Murphy
 * Program: gmBuilder
 * Description: Manages the import of GeneOntology
 * associations to the PostgreSQL database.
 *
 * Revision History:
 * 20100402: Initial Revision; based on ExportToGenMAPP.java.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportWizard;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author Joey J. Barrett Class: ImportGOA
 */
public class ImportGOA {

	/*
	 * These are static so they can be accessed by the ImportPanels
	 * without passing a reference to the ImportGOA class
	 *
	 * The array of DatabaseProfiles called availableDatabaseProfiles holds all
	 * the available database profiles. This accommodates the possibility of
	 * supporting more than just UniProt, for example TIGR could be in the list,
	 * too.
	 *
	 * The selectedDatabaseProfile is which one we actually selected.
	 */
	//FIXME: REFACTOR: These should be stored somewhere else.
	// availableProfiles should be in a GuiConfig object (or some such)
	// selected... goes into an ExportProperties object
    private static DatabaseProfile[] availableDatabaseProfiles;
    private static DatabaseProfile selectedDatabaseProfile;

    private static final Log _Log = LogFactory.getLog(ImportGOA.class);

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
     * @throws InvalidParameterException
     *
     * @throws Exception
     */
    public static void GOAimport() throws ClassNotFoundException, SQLException, HibernateException, SAXException, IOException, JAXBException, InvalidParameterException {
    	_Log.warn("GOA Import Started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format( System.currentTimeMillis()) );
    	ExportWizard.updateExportProgress(1, "Starting GeneOntology import...");

//      TODO: replace with new import method
//    	(new ExportGoData(selectedDatabaseProfile.getExportConnection())).export(selectedDatabaseProfile.getAssociationsFile());

/*        ExportWizard.updateExportProgress(50, "Finished GeneOntology export...");
        ExportWizard.updateExportProgress(51, "Starting first pass table creation...");


        // JN - in-lining of calls with immediate write to gmb
        _Log.info("Getting first-pass table managers");
        // No species specific processing
        ExportWizard.updateExportProgress(53, "Preparing tables - Info table...");
        _Log.info("Start getInfoTableManger()");
        TableManager tmA = selectedDatabaseProfile.getInfoTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmA);

//      This uses SpeciesProfile
        _Log.info("Start getRelationsTableManager()");
        ExportWizard.updateExportProgress(55, "Preparing tables - Relations table...");
        TableManager tmB = selectedDatabaseProfile.getRelationsTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmB);

        // No species specific processing
        _Log.info("Start getOtherTableManager()");
        ExportWizard.updateExportProgress(57, "Preparing tables - Other table...");
        TableManager tmC = selectedDatabaseProfile.getOtherTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmC);

//      This uses SpeciesProfile
        _Log.info("Start getSystemsTableManager()");
        ExportWizard.updateExportProgress(59, "Preparing tables - Systems table...");
        TableManager tmD = selectedDatabaseProfile.getSystemsTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmD);

        // No species specific processing
        // This gets all the UniProt table information
        _Log.info("Start getPrimarySystemTableManager()");
        ExportWizard.updateExportProgress(61, "Preparing tables - Primary System table...");
        TableManager tmE = selectedDatabaseProfile.getPrimarySystemTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmE);

//      This uses SpeciesProfile
        // This gets info for all of the proper system tables (e.g. TAIR, Blattner, UniGene
        _Log.info("Start getSystemTableManager()");
        ExportWizard.updateExportProgress(63, "Preparing tables - System tables...");
        TableManager tmF = selectedDatabaseProfile.getSystemTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmF);

//      This uses SpeciesProfile
        _Log.info("Start getRelationshipTableManager()");
        ExportWizard.updateExportProgress(65, "Preparing tables - Relationship table...");
        List<TableManager> tmG = selectedDatabaseProfile.getRelationshipTableManager();
        TableCoordinator.exportTables(selectedDatabaseProfile.getExportConnection(), tmG.toArray(new TableManager[0]));
        // end in-lining

        //JN - "in-lining" is not needed here since the call getSecondPassTableManagers
        //     contains only one call, which is immediately written out, here.

        ExportWizard.updateExportProgress(66, "Starting second pass table creation...");
        _Log.info("Getting second-pass table managers");
        // No species specific processing
        _Log.info("Start ()");
//      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
        ExportWizard.updateExportProgress(67, "Preparing tables - Second pass Relationship tables...");
        TableManager[] secondPass = selectedDatabaseProfile.getSecondPassTableManagers();
        _Log.info("Exporting second-pass tables");
        _Log.info("Start exportTables(selectedDatabaseProfile.getExportConnection(), secondPass)");
        TableCoordinator.exportTables(selectedDatabaseProfile.getExportConnection(), secondPass);

        //JN - Like "SecondPass", above, this method is only creating 1 TableManager
        //		and therefore does not require "in-lining".
        ExportWizard.updateExportProgress(68, "Preparing table - OriginalRowCounts table...");
        _Log.info("Getting row counts table manager");
        _Log.info("Start selectedDatabaseProfile.getRowCountsTableManager()");
        //No species specific processing
        TableManager rowCounts = selectedDatabaseProfile.getRowCountsTableManager();
        _Log.info("Exporting row counts tables");
        _Log.info("Start exportTable(selectedDatabaseProfile.getExportConnection(), rowCounts)");
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), rowCounts);*/
        _Log.warn("Export Finished at: " + DateFormat.getTimeInstance(DateFormat.LONG).format( System.currentTimeMillis()) );
        _Log.info("Done with ImportGOA.GOAimport()");
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
