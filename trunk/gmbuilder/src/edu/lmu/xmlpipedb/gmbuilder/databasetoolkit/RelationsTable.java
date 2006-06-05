/********************************************************
 * Filename: RelationsTable.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: A specific implementation of the 
 * Relations table for the GenMAPP database.
 * 
 * Revision History:
 * 20060605: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

public class RelationsTable extends Table {

	/**
	 * Constructor.  Also creates the table since
	 * every table of this type need to be created.
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public RelationsTable(String tableName) throws Exception {
		super(tableName, new String[] {"\"Primary\"","VARCHAR(50) NOT NULL",
				"Related","VARCHAR(50) NOT NULL",
				"Bridge","VARCHAR(3)"});
		//Automatically create this table.
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
		
		if(values.length != 3) {
			throw new Exception("Incorrect number of arguments");
		}
		
		String namesAndValues = "\"Primary\";" + values[0];
		namesAndValues += ";Related;" + values[1];
		namesAndValues += ";Bridge;" + values[2];

		generateInsert(namesAndValues);
		
	}

}
