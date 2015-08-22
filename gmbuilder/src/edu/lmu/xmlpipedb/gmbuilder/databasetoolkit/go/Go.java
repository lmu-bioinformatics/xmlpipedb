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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.healthmarketscience.jackcess.Database;

import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;

public class Go {
    /**
	 * Create GO tables
	 * 
	 * @param connection
	 * 			The database connection
	 * @throws SQLException
	 */
	public void createTables(Database database) throws IOException {
        for (GOTable goTable: GOTable.values()) {
            if (!goTable.equals(GOTable.GeneOntologyStage)) {
                GenMAPPBuilderUtilities.createAccessTableDirectly(database, goTable.getName(),
                        goTable.getColumnsToTypes());
            }
        }
	}
	
    public void insert(Database database, GOTable table, Object[] values) throws IOException {
        Map<String, Object> columnsToValues = new HashMap<String, Object>();

        int i = 0;
        for (String column: table.columnsInOrder()) {
            Object value = values[i];
            columnsToValues.put(column, value instanceof String ?
                    GenMAPPBuilderUtilities.straightToCurly((String)value) : value);
            i++;
        }

        GenMAPPBuilderUtilities.insertAccessRowDirectly(database, table.getName(), columnsToValues);
    }

    public void insert(Connection connection, GOTable table, Object[] values) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(table.getInsert());
            for (int index = 1; index <= values.length; index++) {
                Object value = values[index - 1];
                if (value instanceof Date) {
                    ps.setDate(index, new java.sql.Date(((Date)value).getTime()));
                } else {
                    ps.setString(index, value == null ? null :
                            GenMAPPBuilderUtilities.straightToCurly(value.toString()));
                }
            }

            LOG.debug("Performing insert: " + ps);
            ps.executeUpdate();
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
                LOG.error(exc);
            }
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
	public void updateSystemsTable(Connection connection, Date date, String systemCode) throws SQLException {
		String stmt = "UPDATE Systems SET [Date] = ? WHERE SystemCode = ?";
		PreparedStatement ps = connection.prepareStatement(stmt);
		ps.setTimestamp(1, new Timestamp(date.getTime()));
		ps.setString(2, systemCode);
    	ps.executeUpdate();
    	ps.close();
	}

    private static final Log LOG = LogFactory.getLog(Go.class);
}