package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class StreptococcusPneumoniaeTIGR4UniProtSpeciesProfile extends
		UniProtSpeciesProfile {
	public StreptococcusPneumoniaeTIGR4UniProtSpeciesProfile() {
	    super("StreptococcusPneumoniaeTIGR4",
	        170187,
	        "This profile customizes the GenMAPP Builder export for " +
	            "StreptococcusPneumoniaeTIGR4" +
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
	        { "Link", "http://www.streppneumoniae.com/gene_detail_output.asp?id=2741&name=~" }
	    });

	    return tableManager;
	}
	
}


