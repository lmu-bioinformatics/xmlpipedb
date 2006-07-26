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
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		
		private Map<String, String> row = new HashMap<String, String>();
		
		/**
		 * Constructor
		 */
		protected Row() {}
		
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
	}
	
	private String[][] tableDefinition;
	private List<String> primaryKeys;
	private List<Row> dataSet = new ArrayList<Row>();
	public static enum QueryType {update, insert};
	public static final String TABLE_NAME_COLUMN = "TABLE_NAME_COLUMN";
	public static final String QUERY_TYPE_COLUMN = "QUERY_TYPE_COLUMN";
	

	/**
	 * Constructor.
	 * @param tableDefinition
	 * @param primaryKeys
	 */
	public TableManager(String[][] tableDefinition, String[] primaryKeys) {
		
		this.tableDefinition = tableDefinition;
		
		List<String> primaryKeyList = new ArrayList<String>();
		primaryKeyList.add(TABLE_NAME_COLUMN);
		primaryKeyList.add(QUERY_TYPE_COLUMN);
		for(String primaryKey : primaryKeys) {
			primaryKeyList.add(primaryKey);
		}
		this.primaryKeys = primaryKeyList;
	}
	
	/**
	 * Submit new names/values to the TableManager.  The
	 * TableManager assumes nothing about rows/columns in 
	 * a table and will accept a undefined column buffering
	 * all other rows to also have the new row.  If this
	 * TableManager has primary keys they are taken into
	 * consideration during the inserting process.
	 * @param tableName
	 * @param queryType
	 * @param columnNamesToValues
	 * @throws Exception
	 */
	public void submit(String tableName, QueryType queryType, String[][] columnNamesToValues) throws Exception {
	
		//The new row.
		Row newRow = new Row();
		newRow.add(TABLE_NAME_COLUMN, tableName);
		newRow.add(QUERY_TYPE_COLUMN, queryType.name());
		
		for(int i = 0; i < columnNamesToValues.length; i++) {
			
			if(columnNamesToValues[i].length != 2) {
				throw new Exception("Incorrect number " +
						"of arguments in DataSet submission.");
			}
			
			//add the column to the new row.
			newRow.add(columnNamesToValues[i][0], columnNamesToValues[i][1]);		
		}
		
		if(primaryKeys.size() > 0) {
			
			//Requires a primary key check.
			addRowWithPrimaryKey(newRow);
		} else {
			
			//Just add the row.
			addRow(newRow);
		}
	}

	/**
	 * Adds a row to the data set ignoring primary key(s).
	 * @param columnNamesToValues
	 * @throws Exception
	 */
	private void addRow(Row newRow) throws Exception {
		
		//Add any new columns to all current rows in data set.
		for(String columnName : newRow.getColumnNames()) {
			if(currentColumnNames() == null) {
				for(Row row : dataSet) {
					row.add(columnName, "");
				}
			} else if(!currentColumnNames().contains(columnName)) {
				for(Row row : dataSet) {
					row.add(columnName, "");
				}
			}
		}
		
		//Add any missing columns from the data set 
		//to the new row.
		if(currentColumnNames() != null) {
			for(String columnName : currentColumnNames()) {
				if(!newRow.containsKey(columnName)) {
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
	private void addRowWithPrimaryKey(Row newRow) throws Exception {
		
		//Check new row for required primary keys.
		if(!newRow.getColumnNames().containsAll(primaryKeys)) {
			throw new Exception("Primary key(s) required " +
					"for DataSet submission.");
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
	 * on thier primary keys.
	 * @param row1
	 * @param row2
	 * @return
	 */
	private boolean rowsEqualOnPK(Row row1, Row row2) {
		for(String primaryKey : primaryKeys) {
			if(!row1.getValue(primaryKey).equals(row2.getValue(primaryKey))) {
				return false;
			}
		}
		return true;
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
}
	

	