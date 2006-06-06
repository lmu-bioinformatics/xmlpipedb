/********************************************************
 * Filename: RowCountsTable.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: A specific implementation of the 
 * RowCounts table for the GenMAPP database.
 * 
 * Revision History:
 * 20060606: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

public class RowCountsTable extends Table {

	/**
	 * Constructor.  Also creates the table since
	 * every table of this type need to be created.
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public RowCountsTable(String tableName) throws Exception {
		super(tableName, new String[] {"\"Table\"", "VARCHAR(50) NOT NULL", "Rows", "VARCHAR(50) NOT NULL"});
		//Automatically create table.
		create();
	}

	/* 
	 * This implementation only requires a set of values in a 
	 * predefined order and does not require the column name 
	 * to be included.  The values are "packed" by the column 
	 * name and then a insert statement is generated.
	 */
	@Override
	public void insert(String valueString) throws Exception {
		String[] values = valueString.split(";");
		
		if(values.length != 2) {
			throw new Exception("Incorrect number of arguments" + values.length);
		}
		
		String namesAndValues = "\"Table\";" + values[0];
		namesAndValues += ";Rows;" + values[1];
		
		generateInsert(namesAndValues);
	}

}
