/********************************************************
 * Filename: TableCoordinator.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Builds tables from a list of table manager
 * ojects.  Exports the tables to the given database
 * connection.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.sql.Connection;
import java.sql.SQLException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.Table;


/**
 * @author Joey J. Barrett
 * Class: TableCoordinator
 */
public class TableCoordinator {
	
	/**
	 * Export many tables given a TableManager[].
	 * @param exportConnection 
	 * @param tableManagers
	 * @throws SQLException
	 * @throws Exception
	 */
	public static void exportTables(Connection exportConnection, TableManager[] tableManagers) throws SQLException, Exception {
		for(TableManager tableManager : tableManagers) {
			new Table(tableManager).export(exportConnection);
		}
	}
	
	/**
	 * Exports a single table given a table manager.
	 * @param exportConnection
	 * @param tableManager
	 * @throws SQLException
	 * @throws Exception
	 */
	public static void exportTable(Connection exportConnection, TableManager tableManager) throws SQLException, Exception {
		new Table(tableManager).export(exportConnection);
	}

}
