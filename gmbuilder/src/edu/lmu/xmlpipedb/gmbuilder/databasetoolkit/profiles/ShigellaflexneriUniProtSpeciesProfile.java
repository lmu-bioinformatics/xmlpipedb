package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class ShigellaflexneriUniProtSpeciesProfile extends UniProtSpeciesProfile {
	public ShigellaflexneriUniProtSpeciesProfile() {
	    super("Shigella flexneri",
	        198214,
	        "This profile customizes the GenMAPP Builder export for " +
	            "Shigella flexneri" +
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
	        { "Link", "http://www.mgc.ac.cn/cgi-bin/ShiBASE/ShiBASE_query.cgi?synonym=~" }
	    });

	    return tableManager;
	}
}