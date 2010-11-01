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

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.Table;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;


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
	public static void exportTables(Connection exportConnection, TableManager[] tableManagers) throws SQLException {
		for(TableManager tableManager : tableManagers) {
//			JN -- replaced this with the call to exportTable, below
//			new Table(tableManager).export(exportConnection);
			exportTable( exportConnection, tableManager );
		}
	}
	
	
	/**
	 * Exports a single table given a table manager.
	 * @param exportConnection
	 * @param tableManager
	 * @throws SQLException
	 * @throws Exception
	 */
	public static void exportTable(Connection exportConnection, TableManager tableManager) throws SQLException {
		new Table(tableManager).export(exportConnection);
	}

}
