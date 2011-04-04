/********************************************************
 * Filename: TableManager.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: The TableManager is a virtual 
 * representation of table.  It contains information 
 * about how to contruct a table in an SQL database,
 * the data contained in the table and the primary
 * keys.
 * 
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Joey J. Barrett
 * Class: TableManager
 */
public class TableManager {
	/**
	 * @author Joey J. Barrett
	 * Class: Row
	 * Description: Represents a row in a
	 * virtual table.
	 */
	public class Row {
		/**
		 * Constructor
		 */
		protected Row() {
            row = new HashMap<String, String>();
        }
		
		/**
		 * Add a column name and value to this row.
		 * @param columnName
		 * @param value
		 */
		protected void add(String columnName, String value) {
			row.put(columnName, value);
		}
		
		/**
		 * Returns the value associated with this column name.
		 * @param columnName
		 * @return
		 */
		public String getValue(String columnName) {
			return row.get(columnName);
		}
		
		/**
		 * Returns this row as a map.
		 * @return
		 */
		public Map<String, String> getRowAsMap() {
			return row;
		}
		
		/**
		 * Returns all the column names associated with this row.
		 * @return
		 */
		protected Set<String> getColumnNames() {
			return row.keySet();
		}
		
		/**
		 * Returns true if column name exists in this row, false otherwise.
		 * @param columnName
		 * @return
		 */
		protected boolean containsKey(String columnName){
			return row.containsKey(columnName);
		}
		
		/**
		 * Returns true if this column/value pair 
		 * exists in this row, false otherwise.
		 * @param columnName
		 * @param value
		 * @return
		 */
		protected boolean containsPair(String columnName, String value){	
			return row.get(columnName).equals(value) ? true : false;
		}
		
		/**
		 * Merge two rows into one.  Used for two rows which
		 * are equal based on their primary keys.
		 * @param row
		 */
		protected void merge(Row row) {
			this.row.putAll(row.asMap());
		}
		
		/**
		 * Return this row as a Map.
		 * @return
		 */
		private Map<String, String> asMap() {
			return row;
		}

        private Map<String, String> row;
    }
	
	public static enum QueryType {update, insert};
	public static final String TABLE_NAME_COLUMN = "TABLE_NAME_COLUMN";
	public static final String QUERY_TYPE_COLUMN = "QUERY_TYPE_COLUMN";

	/**
	 * Constructor.
	 * 
	 * @param tableDefinition -
	 *            A two-dimensional array containing the column name and the
	 *            data type of the column.
	 * @param primaryKeys -
	 *            An array containing the name of the column that will be the
	 *            primary key for the table. This must be one of the strings
	 *            that was passed in the tableDefinition parameter.
	 */
	public TableManager(String[][] tableDefinition, String[] primaryKeys) {
		this.tableDefinition = tableDefinition;
        this.dataSet = new ArrayList<Row>();
        this.tableNames = new HashSet<String>();
		this.primaryKeys = new ArrayList<String>();

        this.primaryKeys.add(TABLE_NAME_COLUMN);
		this.primaryKeys.add(QUERY_TYPE_COLUMN);
		for(String primaryKey : primaryKeys) {
			this.primaryKeys.add(primaryKey);
		}
	}
	
	/**
     * Submit new names/values to the TableManager. The TableManager assumes
     * nothing about rows/columns in a table and will accept a undefined column
     * buffering all other rows to also have the new row. If this TableManager
     * has primary keys they are taken into consideration during the inserting
     * process.
     * 
     * @param tableName
     * @param queryType
     * @param columnNamesToValues
     * @throws Exception
     */
    public void submit(String tableName, QueryType queryType, String[][] columnNamesToValues) {
        // Add the table name to the set. Since it's a set, we don't have to
        // worry about repeats.
        tableNames.add(tableName);

        // The new row.
        Row newRow = new Row();
        newRow.add(TABLE_NAME_COLUMN, tableName);
        newRow.add(QUERY_TYPE_COLUMN, queryType.name());

        for (int i = 0; i < columnNamesToValues.length; i++) {
            if (columnNamesToValues[i].length != 2) {
                _Log.fatal("Incorrect number of arguments in DataSet submission.");
            }

            // add the column to the new row.
            newRow.add(columnNamesToValues[i][0], columnNamesToValues[i][1]);
        }

        if (primaryKeys.size() > 0) {
            // Requires a primary key check.
            addRowWithPrimaryKey(newRow);
        } else {
            // Just add the row.
            addRow(newRow);
        }
    }

    /**
     * Returns the rows in this TableManager.
     * @return
     */
    public Row[] getRows() {
        return dataSet.toArray(new Row[0]);
    }

    /**
     * Returns the table definition in this
     * TableManager.
     * @return
     */
    public String[][] getTableDefinition() {
        return tableDefinition;
    }
    
    /**
     * Returns the primary keys defined in this
     * TableManager ignoring table name and
     * query type.
     * @return
     */
    public List<String> getPrimaryKeys() {
        List<String> publicPrimaryKeys = primaryKeys;
        publicPrimaryKeys.remove(TABLE_NAME_COLUMN);
        publicPrimaryKeys.remove(QUERY_TYPE_COLUMN);
        return publicPrimaryKeys;
    }

    /**
     * Returns the table names that have been "submitted" to this TableManager.
     * 
     * @return The table names that have been "submitted" to this TableManager
     */
    public Set<String> getTableNames() {
        return tableNames;
    }

    /**
     * Eliminates the table rows for the given table name; typically used when
     * the target table turns out to need a different schema from the "usual"
     * table type represented by this table manager.
     */
    public void removeTableRowsFor(String tableName) {
        List<Row> rowsToRemove = new ArrayList<Row>();
        for (Row r: dataSet) {
            if (r.getValue(TABLE_NAME_COLUMN).equals(tableName)) {
                rowsToRemove.add(r);
            }
        }
        
        _Log.debug("Removing rows for table named " + tableName + "...");
        _Log.debug("Initial row count: " + dataSet.size());
        dataSet.removeAll(rowsToRemove);
        tableNames.remove(tableName);
        _Log.debug("Post-remove row count: " + dataSet.size());
    }

    /**
     * Adds a row to the data set ignoring primary key(s).
     * 
     * @param columnNamesToValues
     * @throws Exception
     */
    private void addRow(Row newRow) {
        // Add any new columns to all current rows in data set.
        for (String columnName : newRow.getColumnNames()) {
            if (currentColumnNames() == null) {
                for (Row row : dataSet) {
                    row.add(columnName, "");
                }
            } else if (!currentColumnNames().contains(columnName)) {
                for (Row row : dataSet) {
                    row.add(columnName, "");
                }
            }
        }

        // Add any missing columns from the data set
        // to the new row.
        if (currentColumnNames() != null) {
            for (String columnName : currentColumnNames()) {
                if (!newRow.containsKey(columnName)) {
                    newRow.add(columnName, "");
                }
            }
        }

        //Add the new row.
        dataSet.add(newRow);
    }
	
	/**
	 * Adds a new row to the data set based on primary key(s).
	 * @param newRow
	 * @throws Exception
	 */
	private void addRowWithPrimaryKey(Row newRow) {
		//Check new row for required primary keys.
		if(!newRow.getColumnNames().containsAll(primaryKeys)) {
			_Log.fatal("Primary key(s) required for DataSet submission.");
		}
		
		//Look for a primary key match.
		Row primaryKeyMatch = null;
		for(Row row : dataSet) {
			if(rowsEqualOnPK(newRow, row)) {
				primaryKeyMatch = row;
				dataSet.remove(row);
				break;
			}
		}
		
		if(primaryKeyMatch == null) {
			//Did not find a match, just add the new row.
			addRow(newRow);
		} else {
		
			//Found a match, merge the row into one, 
			//then add the new row.
			primaryKeyMatch.merge(newRow);
			addRow(primaryKeyMatch);
		}
	}
	
	/**
	 * Returns a list of the current column names.
	 * @return
	 */
	protected Set<String> currentColumnNames() {
		if(dataSet.size() == 0) {
			return null;
		} else {
			
			Row row = dataSet.get(0);
			return row.getColumnNames();
		}
	}
	
	/**
	 * Returns whether two rows are equal based
	 * on their primary keys.
	 * @param row1
	 * @param row2
	 * @return
	 */
	private boolean rowsEqualOnPK(Row row1, Row row2) {
        for (String primaryKey : primaryKeys) {
            if (_Log.isDebugEnabled()) {
                //_Log.debug("Processing " + primaryKey + " for rows " + row1 + " and " + row2);
                //_Log.debug("Row 1 value for primary key: " + row1.getValue(primaryKey));
                //_Log.debug("Row 2 value for primary key: " + row2.getValue(primaryKey));
            }

            if (!row1.getValue(primaryKey).equals(row2.getValue(primaryKey))) {
                return false;
            }
        }
        return true;
    }

    private static final Log _Log = LogFactory.getLog(TableManager.class);
    
    private String[][] tableDefinition;
    private List<String> primaryKeys;
    private List<Row> dataSet;
    
    /**
     * The table names that have been "submitted" to this table manager.
     */
    private Set<String> tableNames;
}
