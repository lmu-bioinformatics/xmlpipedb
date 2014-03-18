package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;

public class StreptococcusPneumoniaeR6UniProtSpeciesProfile extends StreptococcusPneumoniaeTIGR4UniProtSpeciesProfile {

    public StreptococcusPneumoniaeR6UniProtSpeciesProfile() {
        super("StreptococcusPneumoniaeR6",
                171101,
            "This profile customizes the GenMAPP Builder export for " +
                "Streptococcus pneumoniae R6" +
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
            { "Link", "http://bacteria.ensembl.org/streptococcus_pneumoniae_r6/Gene/Summary?g=~" }
        });

        return tableManager;
    }

    protected String getIdPattern() {
        return "SPR_*";
    }

}
