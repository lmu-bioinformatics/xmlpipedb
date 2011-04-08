package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class PseudomonasAeruginosaUniProtSpeciesProfile extends
		UniProtSpeciesProfile {

	public PseudomonasAeruginosaUniProtSpeciesProfile() {
	    super("Pseudomonas aeruginosa", 287,
	       "This profile customizes the GenMAPP Builder export for Pseudomonas aeruginosa data loaded from a UniProt XML file.");
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
	        { "Link", "http://www.pseudomonas.com/getAnnotation.do?locusID=~" }
	    });

	    return tableManager;
	}

}
