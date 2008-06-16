/********************************************************
 * Filename: UniProtDatabaseProfile.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: This class is a sub-class of
 * DatabaseProfile and defines the UniProt centric
 * customizations associated with the databse.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportWizard;

/**
 * @author Joey J. Barrett
 * Class: UniProtDatabaseProfile
 */
public class UniProtDatabaseProfile extends DatabaseProfile {
	
	private List<String> produceLastRelationshipTables = new ArrayList<String>();
	
	/**
	 * Constuctor.
	 */
	public UniProtDatabaseProfile() {
		super("org.uniprot.uniprot.Uniprot", "This profile defines the requirements " +
				"for any UniProt centric gene database.", 
				new SpeciesProfile[] {new EscherichiaColiUniProtSpeciesProfile()});
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#isAvailable(java.sql.Connection)
	 */
	@Override
	public boolean isAvailable(Connection connection) throws SQLException {
		
		PreparedStatement ps = connection.prepareStatement("select hjtype from uniprottype");
		ResultSet result = ps.executeQuery();
	    while(result.next()) {
			if(result.getString("hjtype").equals(getName())) {
				return true;
			}
	    }	
	    return false;
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#checkRequirements(java.sql.Connection)
	 */
	@Override
	public void checkRequirements(Connection connection) throws SQLException {
		
		//Get the species(s) contained in the database.
		PreparedStatement ps = connection.prepareStatement("select distinct(value) from organismnametype");
		ResultSet result = ps.executeQuery();
	    while(result.next()) {
	    	
	    	String speciesName = result.getString("value");
	    	boolean speciesProfileFound = false;
	    	//Add the species found to the available species profiles.
	    	for(SpeciesProfile speciesProfile : speciesProfilesAvailable) {
				if(speciesName.equals(speciesProfile.getName())) {
					speciesProfilesFound.add(speciesProfile);
					speciesProfileFound = true;
					break;
				}
			}
	    	
	    	if(!speciesProfileFound) {		
	    		speciesProfilesFound.add(new CustomUniProtSpeciesProfile(speciesName, 
	    				"This profile defines the requirements for " +
						"a custom species profile within a UniProt database."));
	    	}
	    }	
	    
		//Get the system(s) contained in the database.
		ps = connection.prepareStatement("select distinct(type) from dbreferencetype");
		result = ps.executeQuery();
	    while(result.next()) {
	    	systemTablesFound.add(result.getString("type"));
	    }
	    
	    result.close();
	    ps.close();
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getMODSystem()
	 */
	@Override
	public String getMODSystem() {
		return "UniProt";
	}



	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getDisplayOrder()
	 */
	@Override
	protected String getDisplayOrder() {
		List<String>  systemCodes = new ArrayList<String>();
		
		for(Entry<String, SystemType> systemTable : systemTables.entrySet()) {
			
			if(!getDatabaseSpecificSystemTables().containsKey(systemTable.getKey()) &&
					!speciesProfile.getSpeciesSpecificSystemTables().containsKey(systemTable.getKey())) {
				
				systemCodes.add(templateDefinedSystemToSystemCode.get(systemTable.getKey()));
				
			}
		}
		
		switch(displayOrder) {
			case alphabetical: 
				Collections.sort(systemCodes, new CaseInsensitiveComparator());
				break;
		}
		
		systemCodes.add(0, templateDefinedSystemToSystemCode.get("UniProt"));
		systemCodes.add(1, templateDefinedSystemToSystemCode.get("GeneOntology"));
		
		systemCodes = speciesProfile.getSpeciesSpecificSystemCode(systemCodes, templateDefinedSystemToSystemCode);
		
		StringBuffer displayOrder = new StringBuffer();
		for(String systemCode : systemCodes) {
			displayOrder.append("|").append(systemCode);
		}
		displayOrder.append("|");
		
		return displayOrder.toString();
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getDatabaseSpecificSystemTables()
	 */
	@Override
	public Map<String, SystemType> getDatabaseSpecificSystemTables() {
		Map<String, SystemType> uniprotSpecificSystemTables = new HashMap<String, SystemType>();
		uniprotSpecificSystemTables.put("UniProt", SystemType.Primary);
		uniprotSpecificSystemTables.put("GeneOntology", SystemType.Improper);
		uniprotSpecificSystemTables.putAll(speciesProfile.getSpeciesSpecificSystemTables());
		return uniprotSpecificSystemTables;
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getSystemsTableManager()
	 */
	@Override
	protected TableManager getSystemsTableManager() throws Exception {
		TableManager tableManager;
		
		tableManager = new TableManager(null, new String[] {"SystemCode"});
		
		for(Entry<String, SystemType> systemTable : systemTables.entrySet()) {
			if(!speciesProfile.getSpeciesSpecificSystemTables().containsKey(systemTable.getKey())) {
				tableManager.submit("Systems", QueryType.update, new String[][] {
						{"SystemCode", templateDefinedSystemToSystemCode.get(systemTable.getKey())},
						{"\"Date\"", new SimpleDateFormat("MM/dd/yyyy").format(version)}
				});
			}
		}
		
		tableManager.submit("Systems", QueryType.update, new String[][] {
				{"SystemCode", templateDefinedSystemToSystemCode.get("UniProt")},
				{"Columns", "ID|EntryName\\sBF|GeneName\\sBF|ProteinName\\BF|Function\\BF|"}
		});
		
		tableManager = speciesProfile.getSystemsTableManagerCustomizations(tableManager);
		return tableManager;
	}
	
	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getPrimarySystemTableManager()
	 */
	@Override
	protected TableManager getPrimarySystemTableManager() throws Exception {
		if(primarySystemTableManager != null) {
			return primarySystemTableManager;
		}
		
		TableManager tableManager;
		
		tableManager = new TableManager(new String[][] {
				{"ID","VARCHAR(50) NOT NULL"},
				{"EntryName","VARCHAR(50) NOT NULL"},
				{"GeneName","VARCHAR(50) NOT NULL"},
				{"ProteinName","MEMO"},
				{"Function","MEMO"},
				{"Species","MEMO"},
				{"\"Date\"","DATE"},
				{"Remarks","MEMO"}}, new String[] {"UID"});

		PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
				"SELECT entrytype_accession_hjid, hjvalue " +
      			"FROM entrytype_accession " +
      			"WHERE entrytype_accession_hjindex = 0");
		ResultSet result = ps.executeQuery();
		while(result.next()) {
			tableManager.submit("UniProt", QueryType.insert, new String[][] {
					{"UID", result.getString("entrytype_accession_hjid")}, 
					{"ID", result.getString("hjvalue")}});
		}
		
		for(Row row : tableManager.getRows()) {
	    	ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
	    				"SELECT hjvalue " +
	    				"FROM entrytype_name " +
						"WHERE entrytype_name_hjid = ?");
	    	ps.setString(1, row.getValue("UID"));
	    	result = ps.executeQuery();
	    	while(result.next()) {
	    		tableManager.submit("UniProt", QueryType.insert, new String[][] {
						{"UID", row.getValue("UID")}, 
						{"EntryName", result.getString("hjvalue")}});
	    	}
	    	
			ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
					"SELECT value, type " +
					"FROM genenametype INNER JOIN entrytype_genetype " +
					"ON (entrytype_genetype_name_hjid = entrytype_genetype.hjid) " +
					"WHERE entrytype_gene_hjid = ?");
			ps.setString(1, row.getValue("UID"));
	    	result = ps.executeQuery();
	    	Map<String, String> typeToValue = new HashMap<String, String>();
	    	while(result.next()) {
	    		typeToValue.put(result.getString("type"), result.getString("value"));
	    	}
	    	tableManager.submit("UniProt", QueryType.insert, new String[][] {
					{"UID", row.getValue("UID")}, 
					{"GeneName", typeToValue.get("primary") != null ? 
							typeToValue.get("primary") : 
							typeToValue.get("ordered locus") != null ?
							typeToValue.get("ordered locus") :
							typeToValue.get("synonym")}});
	    	
	    	ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
					"SELECT value " +
					"FROM entrytype INNER JOIN proteinnametype " +
					"ON (protein = proteintype_name_hjid) " +
					"WHERE entrytype.hjid = ? " +
					"AND proteintype_name_hjindex = 0;");
	    	ps.setString(1, row.getValue("UID"));
	    	result = ps.executeQuery();
	        while(result.next()) {
	        	tableManager.submit("UniProt", QueryType.insert, new String[][] {
	        			{"UID", row.getValue("UID")}, 
	        			{"ProteinName", result.getString("value")}});
	        }
	        
	        ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
	        		"SELECT text " +
					"FROM commenttype INNER JOIN entrytype_comment " +
					"ON (entrytype_comment_hjchildid = hjid) " +
					"WHERE type = 'function' " +
					"AND entrytype_comment_hjid = ?");
	    	ps.setString(1, row.getValue("UID"));
	    	result = ps.executeQuery();
	        while(result.next()) {
	        	tableManager.submit("UniProt", QueryType.insert, new String[][] {
	        			{"UID", row.getValue("UID")}, 
	        			{"Function", result.getString("text")}});
	        }
	        
	        tableManager.submit("UniProt", QueryType.insert, new String[][] {
        			{"UID", row.getValue("UID")}, 
        			{"Species", "|" + speciesProfile.getSpeciesName() + "|"},
        			{"\"Date\"", new SimpleDateFormat("MM/dd/yyyy").format(version)}});
		}
		ps.close();
		
		primarySystemTableManager = tableManager;		
		return tableManager;
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getSystemTableManager()
	 */
	@Override
	protected TableManager getSystemTableManager() throws Exception {
		
		if(systemTableManager != null) {
			return systemTableManager;
		}
		
		TableManager tableManager;
		
		tableManager = new TableManager(new String[][] {
					{"ID","VARCHAR(50) NOT NULL"},
					{"Species","MEMO"},
					{"\"Date\"","DATE"},
					{"Remarks","MEMO"}}, new String[] {"ID"});
		
		PreparedStatement ps;
		ResultSet result;
		
		for(Entry<String, SystemType> systemTable : systemTables.entrySet()) {
			 if((!getDatabaseSpecificSystemTables().containsKey(systemTable.getKey())) && 
					 (!speciesProfile.getSpeciesSpecificSystemTables().containsKey(systemTable.getKey()))) {
				 
					ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
							"SELECT DISTINCT(id) " +
							"FROM dbreferencetype " +
							"WHERE type = ?");
					ps.setString(1, systemTable.getKey());
					result = ps.executeQuery();
				        
					while(result.next()) {
						tableManager.submit(systemTable.getKey(), QueryType.insert, new String[][] {
		        			{"ID", result.getString("id")}, 
		        			{"Species", "|" + speciesProfile.getSpeciesName() + "|"},
		        			{"\"Date\"", new SimpleDateFormat("MM/dd/yyyy").format(version)}});
		        			
					}
			 }
		}
		
		tableManager = speciesProfile.getSystemTableManagerCustomizations(tableManager, getPrimarySystemTableManager(), version);
		
		systemTableManager = tableManager;
		return tableManager;
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getRelationshipTableManager()
	 */
	@Override
	protected List<TableManager> getRelationshipTableManager() throws SQLException, Exception {

		List<TableManager> tableManagers = new ArrayList<TableManager>();
		
		TableManager tableManager;
		


		
		for(String relationshipTable : relationshipTables) {
			
			
			String systemTable1 = relationshipTable.split("-")[0];
			String systemTable2 = relationshipTable.split("-")[1];
			
			ExportWizard.updateExportProgress(65, "Preparing tables - " +
					"Relationship table - " + relationshipTable + "...");
			
			tableManager = new TableManager(new String[][] {
					{"\"Primary\"", "VARCHAR(50) NOT NULL"},
					{"Related", "VARCHAR(50) NOT NULL"},
					{"Bridge", "VARCHAR(3)"}}, new String[] {"\"Primary\"", "Related"});
			
			
			if(systemTable1.equals(getPrimarySystemTable()) &&
					systemTable2.equals("GeneOntology")) {
				//do nothing, this is UniProt-GeneOntology or UniProt-species
				
			//UniProt-X, UniProt-species/geneontology not included
			} else if(systemTable1.equals("UniProt") &&
					!getDatabaseSpecificSystemTables().containsKey(systemTable2)) {
				
			
				PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
						"SELECT hjvalue, id " +
						"FROM dbreferencetype INNER JOIN entrytype_accession " +
						"ON (entrytype_dbreference_hjid = entrytype_accession_hjid) " +
						"WHERE type = ?");
				
				ps.setString(1, systemTable2);
				ResultSet result = ps.executeQuery();
			    
			    String primary = "";
			    String related = "";
			    
			    while(result.next()) {
			    	
				    primary = result.getString("hjvalue");
				    related = result.getString("id");
				    	
				    tableManager.submit(relationshipTable, QueryType.insert, new String[][] {
		        			{"\"Primary\"", primary != null ? primary : ""}, 
		        			{"Related", related != null ? related : ""},
		        			//TODO This is hard-coded.  Fix it. 
		        			{"Bridge", "S"}});
			    	
			    }		
				ps.close();
				
			} 
			//X-X
			else if(!getDatabaseSpecificSystemTables().containsKey(systemTable1) &&
					!getDatabaseSpecificSystemTables().containsKey(systemTable2)) {
			
				PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement(
			    		"SELECT dbref1.id as id1, " +
			    		"dbref2.id as id2 " +
			    		"FROM dbreferencetype as dbref1 " +
			    		"INNER JOIN dbreferencetype as dbref2 " +
			    		"USING (entrytype_dbreference_hjid) " +
			    		"WHERE dbref1.type <> dbref2.type " +
			    		"AND dbref1.type = ? " +
	    				"AND dbref2.type = ?");
			    
			    ps.setString(1, systemTable1);
			    ps.setString(2, systemTable2);
			    ResultSet result = ps.executeQuery();
			    
			    String primary;
			    String related;
			    
			    while(result.next()) {
			    	
			    	primary = result.getString("id1");
			    	related = result.getString("id2");
			    	
			    	tableManager.submit(relationshipTable, QueryType.insert, new String[][] {
	        			{"\"Primary\"", primary != null ? primary : ""}, 
	        			{"Related", related != null ? related : ""},
	        			//TODO This is hard-coded.  Fix it. 
	        			{"Bridge", "S"}});
			    }	
			    ps.close();
			    //Species-X or X-Species excluding geneontology
			} else if((speciesProfile.getSpeciesSpecificSystemTables().containsKey(systemTable1) ||
					speciesProfile.getSpeciesSpecificSystemTables().containsKey(systemTable2)) &&
					!systemTable2.equals("GeneOntology")) {
				
				tableManager = speciesProfile.getSpeciesSpecificRelationshipTable(
						relationshipTable, getPrimarySystemTableManager(), 
						getSystemTableManager(), tableManager);
				
				//produce last X-geneontology anything
			} else if(systemTable2.equals("GeneOntology")) {
				
				produceLastRelationshipTables.add(relationshipTable);
				
				//No way currently of producing these
			} else {
			
				tableManager.submit(relationshipTable, QueryType.insert, new String[][] {
	        			{"\"Primary\"", ""}, 
	        			{"Related", ""},
	        			//TODO This is hard-coded.  Fix it. 
	        			{"Bridge", ""}});
			}	
			
			tableManagers.add(tableManager);
		}
		


		return tableManagers;
	}

	/* (non-Javadoc)
	 * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile#getSecondPassTableManagers()
	 */
	@Override
	public TableManager[] getSecondPassTableManagers() throws SQLException, Exception {
		List<TableManager> tableManagers = new ArrayList<TableManager>();	
		ExportWizard.updateExportProgress(66, "Preparing tables - Second pass Relationship tables...");
		tableManagers.add(getSecondPassRelationshipTables());
		return tableManagers.toArray(new TableManager[0]);
	}
	
	/**
	 * A helper method for getting the second pass
	 * TableManagers, specifically the relationship
	 * TableManagers.
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private TableManager getSecondPassRelationshipTables() throws SQLException, Exception {
		
		TableManager tableManager;
		
		tableManager = new TableManager(new String[][] {
				{"\"Primary\"", "VARCHAR(50) NOT NULL"},
				{"Related", "VARCHAR(50) NOT NULL"},
				{"Bridge", "VARCHAR(3)"}}, new String[] {"\"Primary\"", "Related"});
	
		PreparedStatement ps = null;
		ResultSet result = null;
		
		for(String relationshipTable : produceLastRelationshipTables.toArray(new String[0])) {

			String systemTable1 = relationshipTable.split("-")[0];
				
			String sqlStatement = "SELECT [UniProt-" + systemTable1 + "].Related as id1, " +
			"[UniProt-GeneOntology].Related as id2 " +
			"FROM [UniProt-" + systemTable1 + "] " +
			"INNER JOIN [UniProt-GeneOntology] " +
			"ON [UniProt-" + systemTable1 + "].Primary = [UniProt-GeneOntology].Primary";
		
	        // Alternative query when using a database other than Access.
//	        String sqlStatement =  "SELECT \"" + tableName + "\".Related, " +
//	        "\"UniProt-GeneOntology\".Related " +
//	        "FROM \"" + tableName + "\" " +
//	        "INNER JOIN \"UniProt-GeneOntology\" " +
//	        "ON \"" + tableName + "\".\"Primary\" = \"UniProt-GeneOntology\".\"Primary\"";
			System.out.println(sqlStatement);
			ps = getExportConnection().prepareStatement(sqlStatement);
			
		    result = ps.executeQuery();
		    while(result.next()) {
		    	tableManager.submit(relationshipTable, QueryType.insert, new String[][] {
	        			{"\"Primary\"", result.getString("id1")}, 
	        			{"Related", result.getString("id2")},
	        			//TODO This is hard-coded.  Fix it. 
	        			{"Bridge", "S"}});
		    }
		}
		return tableManager;
	}
}
