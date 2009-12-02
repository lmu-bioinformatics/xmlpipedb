package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class MycobacteriumTuberculosisUniProtSpeciesProfile extends
		UniProtSpeciesProfile {



public MycobacteriumTuberculosisUniProtSpeciesProfile() {
    super("Mycobacterium tuberculosis",
       "This profile customizes the GenMAPP Builder export for Genus species data loaded from a UniProt XML file.");
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
        { "Link", "http://www.genome.jp/dbget-bin/www_bget?mtu+~" }
    });

    return tableManager;
}
}
