package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class BordetellaPertussisUniProtSpeciesProfile extends UniProtSpeciesProfile {
	
	public BordetellaPertussisUniProtSpeciesProfile() {
	    super("Bordetella pertussis",
	    		257313,
	        "This profile customizes the GenMAPP Builder export for " +
	            "Bordetella pertussis" +
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
	        { "Link", "http://www.genedb.org/gene/~;jsessionid=A06A0EFE93C64E476380393D4CBEFA69?actionName=%2FQuery%2FquickSearch&resultsSize=1&taxonNodeName=Bpertussis" }
	    });

	    return tableManager;
	}
}
