package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

public class SystemTable {
	
	private String type;
	private String[] ids;
	
	protected SystemTable(String type, String[] ids) {
		this.type = type;
		this.ids = ids;
	}
	public String[] getIds() { return ids; }
	public String getType() { return type; }
}
