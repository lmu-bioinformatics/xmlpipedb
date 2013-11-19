package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

public class LeishmaniaMajorUniProtSpeciesProfile extends UniProtSpeciesProfile {
    
    public LeishmaniaMajorUniProtSpeciesProfile() {
        super("Leishmania major",
            5664,
            "This profile customizes the GenMAPP Builder export for " +
                "Leishmania major" +
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
            { "Link", "http://www.genedb.org/gene/~" }
        });
    
        return tableManager;  
    }

    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        /*
         * This method is only called (and therefore this bit 'o logic is only
         * invoked) when the species specific class has not overridden this
         * method.
         */
        List<String> comparisonList = new ArrayList<String>(1);
        comparisonList.add("ORF");

        return systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, "OrderedLocusNames", comparisonList);
    }

}


