package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class StreptococcusPneumoniaeG54UniProtSpeciesProfile extends StreptococcusPneumoniaeTIGR4UniProtSpeciesProfile {

    public StreptococcusPneumoniaeG54UniProtSpeciesProfile() {
        super("StreptococcusPneumoniaeG54",
            512266,
            "This profile customizes the GenMAPP Builder export for " +
                "Streptococcus pneumoniae G54" +
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
            { "Link", "http://bacteria.ensembl.org/streptococcus_pneumoniae_g54/Gene/Summary?g=~" }
        });

        return tableManager;
    }

}
