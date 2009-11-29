package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class SaccharomycesCerevisiaeUniProtSpeciesProfile extends
		UniProtSpeciesProfile {
	public SaccharomycesCerevisiaeUniProtSpeciesProfile() {
	    super("Saccharomyces cerevisiae",
	       "This profile customizes the GenMAPP Builder export for Saccharomyces cerevisiae data loaded from a UniProt XML file.");
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
	        { "Link", "http://www.yeastgenome.org/cgi-bin/locus.fpl?locus=~" }
	    });

	    return tableManager;
	}

}
