/********************************************************
 * Filename: SystemTable.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: A specific implementation of the 
 * UniProt System table for the GenMAPP database.
 * 
 * Revision History:
 * 20060605: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

public class UniProtTable extends Table {

	/**
	 * Constructor.  Also creates the table since
	 * the table of this type needs to be created.
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public UniProtTable() throws Exception {
		super("UniProt", new String[] {"ID","VARCHAR(50) NOT NULL",
        		"EntryName","VARCHAR(50) NOT NULL",
        		"GeneName","VARCHAR(50) NOT NULL",
        		"ProteinName","MEMO",
        		"Function","MEMO",
        		"Species","MEMO",
        		"\"Date\"","DATE",
        		"Remarks","MEMO"});
		//Automatically create table.
		create();
	}

	//Incomplete.
	@Override
	public void insert(String values) throws Exception {
		// TODO Auto-generated method stub

	}

}
