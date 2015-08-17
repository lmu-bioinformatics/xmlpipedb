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
 */
public class TableCoordinator {

    public static void exportTables(Connection exportConnection, TableManager[] tableManagers) throws SQLException {
        for (TableManager tableManager: tableManagers) {
            // JN -- replaced this with the call to exportTable, below
            // new Table(tableManager).export(exportConnection);
            exportTable(exportConnection, tableManager);
        }
    }

    public static void exportTable(Connection exportConnection, TableManager tableManager) throws SQLException {
        new Table(tableManager).export(exportConnection);
    }

}
