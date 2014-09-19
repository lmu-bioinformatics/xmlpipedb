package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class StaphylococcusAureusCOLUniProtSpeciesProfile extends UniProtSpeciesProfile {

    public StaphylococcusAureusCOLUniProtSpeciesProfile() {
        super(
            "Staphylococcus aureus (subsp. aureus COL)", 93062,
            "This profile customizes the GenMAPP Builder export for Staphylococcus aureus subsp. aureus COL data loaded from a UniProt XML file"
        );
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
                { "Link", "http://ensemblgenomes.org/id/~" }
        });

        return tableManager;
    }

}
