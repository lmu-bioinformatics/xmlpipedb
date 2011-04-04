package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class MycobacteriumSmegmatisUniProtSpeciesProfile extends UniProtSpeciesProfile {
	public MycobacteriumSmegmatisUniProtSpeciesProfile() {
		super("Mycobacterium smegmatis (strain ATCC 700084 / mc(2)155)",
		      "This profile customizes the GenMAPP Builder export for Mycobacterium smegmatis data loaded from a UniProt XML file.");
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
	        { "Link", "http://mycobrowser.epfl.ch/smegmasearch.php?gene+name=~" }
	    });

	    return tableManager;
	}
}
