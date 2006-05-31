package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

public class DBReference {
	
	private String type;
	private String[] ids;
	
	protected DBReference(String type, String[] ids) {
		this.type = type;
		this.ids = ids;
	}
	public String[] getIds() { return ids; }
	public String getType() { return type; }
}
