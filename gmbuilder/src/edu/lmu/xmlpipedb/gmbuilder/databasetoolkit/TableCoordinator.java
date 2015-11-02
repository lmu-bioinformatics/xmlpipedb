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

import java.io.IOException;
import java.sql.SQLException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.Table;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;

/**
 * @author Joey J. Barrett
 */
public class TableCoordinator {

    public static void exportTables(TableManager[] tableManagers)
            throws SQLException, IOException, ClassNotFoundException {
        for (TableManager tableManager: tableManagers) {
            // JN -- replaced this with the call to exportTable, below
            // new Table(tableManager).export(exportConnection);
            exportTable(tableManager);
        }
    }

    public static void exportTable(TableManager tableManager)
            throws SQLException, IOException, ClassNotFoundException {
        new Table(tableManager).export();
    }

}
