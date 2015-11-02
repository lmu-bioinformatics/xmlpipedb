package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class SalmonellaTyphimuriumUniProtSpeciesProfile extends
		UniProtSpeciesProfile {

	public SalmonellaTyphimuriumUniProtSpeciesProfile() {
	    super("Salmonella typhimurium", 99287, 
	       "This profile customizes the GenMAPP Builder export for Salmonella typhimurium data loaded from a UniProt XML file.");
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
	        { "Link", "http://bacteria.ensembl.org/Multi/Search/Results?species=all;idx=;q=~;site=ensemblunit" }
	    });

	    return tableManager;
	}
}

