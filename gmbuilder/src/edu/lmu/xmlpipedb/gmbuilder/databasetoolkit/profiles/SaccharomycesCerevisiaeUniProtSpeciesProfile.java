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
	        { "SystemCode", "D" },
	        { "Species", "|" + getSpeciesName() + "|" }
	    });

	    tableManager.submit("Systems", QueryType.update, new String[][] {
	        { "SystemCode", "D" },
	        { "Link", "http://www.yeastgenome.org/cgi-bin/locus.fpl?dbid=~" }
	    });

	    tableManager.submit("Systems", QueryType.update, new String[][] {
		        { "SystemCode", "En" },
		        { "Species", "|" + getSpeciesName() + "|" }
		});

	    tableManager.submit("Systems", QueryType.update, new String[][] {
		        { "SystemCode", "En" },
		        { "Link", "http://www.ensembl.org/Saccharomyces_cerevisiae/Gene/Summary?g=~" }
		});


	    return tableManager;
	}

}
