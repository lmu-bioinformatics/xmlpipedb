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
import java.text.DateFormat;
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
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportInProgressPanel;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author Joey J. Barrett Class: ExportToGenMAPP
 * @author Richard Brous: multi-species export
 */
public class ExportToGenMAPP {

	/*
	 * These are static so they can be accessed by the ExportPanel1, 2, etc.
	 * without passing a reference to the ExportToGenMapp class
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
     * @throws InvalidParameterException
     *
     * @throws Exception
     */
    public static void export(ExportInProgressPanel exportInProgressPanel)
            throws ClassNotFoundException, SQLException, HibernateException, SAXException,
                    IOException, JAXBException, InvalidParameterException {
    	
    	_Log.warn("Export Started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format( System.currentTimeMillis()) );
        exportInProgressPanel.setProgress(1, "Starting GeneOntology export...");
    	
    	// RB - Modified ExportGoData second argument to be a List of taxon ids, from a single taxon id.
    	(new ExportGoData(selectedDatabaseProfile.getExportConnection()))
    		.export(selectedDatabaseProfile.getChosenAspects(), selectedDatabaseProfile.getTaxonIds());
 	
    	exportInProgressPanel.setProgress(50, "Finished GeneOntology export...");
    	exportInProgressPanel.setProgress(51, "Starting first pass table creation...");

        // JN - in-lining of calls with immediate write to gmb
        _Log.info("Getting first-pass table managers");
        
        // RB - modified getInfoTableManager for species submit argument
        exportInProgressPanel.setProgress(53, "Preparing tables - Info table...");
        _Log.info("Start getInfoTableManger()");
        TableManager tmA = selectedDatabaseProfile.getInfoTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmA);

//      This uses SpeciesProfile
        _Log.info("Start getRelationsTableManager()");
        exportInProgressPanel.setProgress(55, "Preparing tables - Relations table...");
        TableManager tmB = selectedDatabaseProfile.getRelationsTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmB);

        // RB - No multiple species specific processing
        _Log.info("Start getOtherTableManager()");
        exportInProgressPanel.setProgress(57, "Preparing tables - Other table...");
        TableManager tmC = selectedDatabaseProfile.getOtherTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmC);

        // RB - This uses a SpeciesProfile but method only for E.coli or A.thaliana
        _Log.info("Start getSystemsTableManager()");
        exportInProgressPanel.setProgress(59, "Preparing tables - Systems table...");
        TableManager tmD = selectedDatabaseProfile.getSystemsTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmD);

        // This gets all the UniProt table information
        // RB - This uses a SpeciesProfile but method only for E.coli or A.thaliana
        _Log.info("Start getPrimarySystemTableManager()");
        exportInProgressPanel.setProgress(61, "Preparing tables - Primary System table...");
        TableManager tmE = selectedDatabaseProfile.getPrimarySystemTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmE);

        // This gets info for all of the proper system tables (e.g. TAIR, Blattner, UniGene
        // RB - Modified getSystemTableManager() for variable number of species.
        _Log.info("Start getSystemTableManager()");
        exportInProgressPanel.setProgress(63, "Preparing tables - System tables...");
        TableManager tmF = selectedDatabaseProfile.getSystemTableManager();
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), tmF);

        // RB - Modified getRelationshipTableManager() for variable number of species.
        _Log.info("Start getRelationshipTableManager()");
        exportInProgressPanel.setProgress(65, "Preparing tables - Relationship table...");
        List<TableManager> tmG = selectedDatabaseProfile.getRelationshipTableManager();
        TableCoordinator.exportTables(selectedDatabaseProfile.getExportConnection(), tmG.toArray(new TableManager[0]));
        // end in-lining

        //JN - "in-lining" is not needed here since the call getSecondPassTableManagers
        //     contains only one call, which is immediately written out, here.

        exportInProgressPanel.setProgress(66, "Starting second pass table creation...");
        _Log.info("Getting second-pass table managers");
        _Log.info("Start ()");
//      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
        exportInProgressPanel.setProgress(67, "Preparing tables - Second pass Relationship tables...");
        // RB - Modified getSecondPassTableManager() for variable number of species.
        TableManager[] secondPass = selectedDatabaseProfile.getSecondPassTableManagers();
        _Log.info("Exporting second-pass tables");
        _Log.info("Start exportTables(selectedDatabaseProfile.getExportConnection(), secondPass)");
        TableCoordinator.exportTables(selectedDatabaseProfile.getExportConnection(), secondPass);

        //JN - Like "SecondPass", above, this method is only creating 1 TableManager
        //		and therefore does not require "in-lining".
        exportInProgressPanel.setProgress(68, "Preparing table - OriginalRowCounts table...");
        _Log.info("Getting row counts table manager");
        _Log.info("Start selectedDatabaseProfile.getRowCountsTableManager()");
        //No species specific processing
        TableManager rowCounts = selectedDatabaseProfile.getRowCountsTableManager();
        _Log.info("Exporting row counts tables");
        _Log.info("Start exportTable(selectedDatabaseProfile.getExportConnection(), rowCounts)");
        TableCoordinator.exportTable(selectedDatabaseProfile.getExportConnection(), rowCounts);
        _Log.warn("Export Finished at: " + DateFormat.getTimeInstance(DateFormat.LONG).format( System.currentTimeMillis()) );
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
