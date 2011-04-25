package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class SalmonellaTyphimuriumUniProtSpeciesProfile extends
		UniProtSpeciesProfile {

	public SalmonellaTyphimuriumUniProtSpeciesProfile() {
	    super("Salmonella typhimurium", 90371, 
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
	        { "Link", "http://cmr.jcvi.org/cgi-bin/CMR/shared/AnnotationSearch.cgi?search_type=nt_locus&match_type=exact&search_string=~" }
	    });

	    return tableManager;
	}
}

