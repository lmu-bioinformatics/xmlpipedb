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

import java.sql.SQLException;

import org.hibernate.cfg.Configuration;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go.ExportGoData;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportWizard;


/**
 * @author Joey J. Barrett
 * Class: ExportToGenMAPP
 */
public class ExportToGenMAPP {

	private static DatabaseProfile[] availableDatabaseProfiles;
	private static DatabaseProfile  selectedDatabaseProfile;
	
	
	/**
	 * Opens the relational database connection and checks for
	 * available database profiles.
	 * @param hibernateConfiguration
	 * @throws Exception
	 */
	public static void init(Configuration hibernateConfiguration) throws Exception {
		
		//Open a connection to the relational database.
		ConnectionManager.openRelationalDB(hibernateConfiguration);

		//Inspect the database.  Find out what profiles it matches.
		availableDatabaseProfiles = DatabaseInspector.init(ConnectionManager.getRelationalDBConnection());
	}
	
	/**
	 * The available database profiles.
	 * @return
	 */
	public static DatabaseProfile[] getAvailableDatabaseProfiles() {
		return availableDatabaseProfiles;
	}
	
	/**
	 * Sets the selected (chosen) database profile.
	 * @param selectedProfile
	 */
	public static void setDatabaseProfile(DatabaseProfile selectedProfile) {
		selectedDatabaseProfile = selectedProfile;
	}
	
	/**
	 * Gets the selected (chosen) database profile.
	 * @return
	 */
	public static DatabaseProfile getDatabaseProfile() {
		return selectedDatabaseProfile;
	}
	
	/**
	 * Runs the export on the selectedDatabaseProfile.
	 * @throws Exception
	 */
	public static void export() throws Exception {
		
		ExportWizard.updateExportProgress(1, "Starting GeneOntology export...");
		(new ExportGoData(selectedDatabaseProfile.getExportConnection())).
				export(selectedDatabaseProfile.getAssociationsFile());
		
		ExportWizard.updateExportProgress(50, "Finished GeneOntology export...");
		ExportWizard.updateExportProgress(51, "Starting first pass table creation...");
		
		TableCoordinator.exportTables(
				selectedDatabaseProfile.getExportConnection(), 
				selectedDatabaseProfile.getFirstPassTableManagers());
		
		ExportWizard.updateExportProgress(66, "Starting second pass table creation...");
		TableCoordinator.exportTables(
				selectedDatabaseProfile.getExportConnection(), 
				selectedDatabaseProfile.getSecondPassTableManagers());
		
		
		ExportWizard.updateExportProgress(98, "Preparing table - OriginalRowCounts table...");
		TableCoordinator.exportTable(
				selectedDatabaseProfile.getExportConnection(),
				selectedDatabaseProfile.getRowCountsTableManager());
	}

	/**
	 * Cleans up anything left from the export.
	 * @throws SQLException
	 */
	public static void cleanup() throws SQLException {
		ConnectionManager.closeAll();
	}
}
