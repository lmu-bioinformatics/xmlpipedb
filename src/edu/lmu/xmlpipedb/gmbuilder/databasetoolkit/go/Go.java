/********************************************************
 * Filename: Go.java
 * Author: LMU
 * Program: gmBuilder
 * Description: Creates the tables to export to the 
 * access database.    
 * Revision History:
 * 20060422: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;

public class Go {
    /**
	 * Create GO tables
	 * 
	 * @param connection
	 * 			The database connection
	 * @throws SQLException
	 */
	public void createTables(Connection connection) throws SQLException {
        Statement s = null;
        try {
            s = connection.createStatement();
            for (GOTable goTable: GOTable.values()) {
                if (!goTable.equals(GOTable.GeneOntologyStage)) {
                    s.execute(goTable.getCreate());
                }
            }
        } finally {
            try { s.close(); } catch(Exception exc) { _Log.error(exc); }
        }
	}
	
	/**
	 * Insert data into a GO table
	 * 
	 * @param connection
	 * 				The database connection
	 * @param table
	 * 				Go table name 
	 * @param values
	 * 				go data 
	 * @throws SQLException
	 */
	public void insert(Connection connection, GOTable table, String[] values) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(table.getInsert());
            for (int index = 1; index <= values.length; index++) {
                ps.setString(index, GenMAPPBuilderUtilities.straightToCurly(values[index-1]));
            }
            
            _Log.debug("Performing insert: " + ps);
            ps.executeUpdate();
        } finally {
            try { ps.close(); } catch(Exception exc) { _Log.error(exc); }
        }
 	}
	
	/**
	 * Updates the date for GO in the Systems table 
	 * 
	 * @param connection
	 * 				the database connection
	 * @param date
	 * 				today date
	 * @param systemCode
	 * 				Unique identifier for the GO table
	 * @throws SQLException
	 */
	public void updateSystemsTable(Connection connection, String date, String systemCode) throws SQLException {
		String stmt = "UPDATE Systems SET \"Date\" = ? WHERE SystemCode = ?";
		PreparedStatement ps = connection.prepareStatement(stmt);
		ps.setString(1, date);
		ps.setString(2, systemCode);
    	ps.executeUpdate();
    	ps.close();
	}

    /**
     * Go class log.
     */
    private static final Log _Log = LogFactory.getLog(Go.class);
}