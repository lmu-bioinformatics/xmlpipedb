package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType; 

public class ShewanellaOneidensisUniProtSpeciesProfile extends UniProtSpeciesProfile {
	
	public ShewanellaOneidensisUniProtSpeciesProfile() {
		
	    super("Shewanella oneidensis",
	    		211586,
	        "This profile customizes the GenMAPP Builder export for Shewanella oneidensis data loaded from a UniProt XML file.");
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
	        { "Link", "http://bacteria.ensembl.org/shewanella_oneidensis_mr_1/Search/Results?species=Shewanella%20oneidensis%20MR-1;idx=;q=~;site=ensemblthis" }
	    });

	    return tableManager;
	}
	
}

