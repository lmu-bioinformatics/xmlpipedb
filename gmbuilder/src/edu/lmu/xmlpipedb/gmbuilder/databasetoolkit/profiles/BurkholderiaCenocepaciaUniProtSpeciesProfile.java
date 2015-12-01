package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class BurkholderiaCenocepaciaUniProtSpeciesProfile extends UniProtSpeciesProfile {
	
	public BurkholderiaCenocepaciaUniProtSpeciesProfile() {
	    super("Burkholderia cenocepacia",
	    		216591,
	        "This profile customizes the GenMAPP Builder export for " +
	            "Burkholderia cenocepacia" +
	            " data loaded from a UniProt XML file.");
	}

	@Override
	public TableManager getSystemsTableManagerCustomizations(TableManager tableManager, DatabaseProfile dbProfile) {
	    super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
	    tableManager.submit("Systems", QueryType.update, new String[][] {
	        { "SystemCode", "N" },
	        { "Species", "|" + getSpeciesName() + "|" }
	    });
	
	    tableManager.submit("Systems", QueryType.update, new String[][] {
	        { "SystemCode", "N" },
	        { "Link", "http://www.burkholderia.com/getAnnotation.do?locusID=~" }
	    });
	
	    return tableManager;
	}
}