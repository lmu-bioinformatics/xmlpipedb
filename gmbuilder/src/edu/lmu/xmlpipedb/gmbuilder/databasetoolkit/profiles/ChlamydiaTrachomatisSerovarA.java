package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class ChlamydiaTrachomatisSerovarA extends UniProtSpeciesProfile {
	
	public ChlamydiaTrachomatisSerovarA() {
	    super("ChlamydiaTrachomatisSerovarA",
	    		315277,
	        "This profile customizes the GenMAPP Builder export for " +
	            "ChlamydiaTrachomatisSerovarAUniProtSpeciesProfile" +
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
	        { "Link", "***species-specific-database-link***" }
	    });

	    return tableManager;
	}
	
}
