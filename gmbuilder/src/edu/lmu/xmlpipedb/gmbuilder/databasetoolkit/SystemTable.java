/********************************************************
 * Filename: SystemTable.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: A specific implementation of the 
 * System table for the GenMAPP database.
 * 
 * Revision History:
 * 20060605: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

public class SystemTable extends Table {
	
	/**
	 * Constructor.  Also creates the table since
	 * every table of this type need to be created.
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public SystemTable(String tableName) throws Exception {
		super(tableName, new String[] {"ID","VARCHAR(50) NOT NULL",
				"Species","MEMO",
				"\"Date\"","DATE",
				"Remarks","MEMO"});
        // Alternative column definitions when not using Access.
//        super(tableName, new String[] {"id", "varchar(50) not null",
//                "species", "varchar",
//                "\"date\"", "varchar",
//                "remarks", "varchar"
//        });
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
		
		if(values.length != 4) {
			throw new Exception("Incorrect number of arguments" + values.length);
		}
		
		String namesAndValues = "ID;" + values[0];
		namesAndValues += ";Species;" + values[1];
		namesAndValues += ";\"Date\";" + values[2];
		namesAndValues += ";Remarks;" + values[3];
		
		generateInsert(namesAndValues);
	}
}
