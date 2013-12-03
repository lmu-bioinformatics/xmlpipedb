package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;


public class SinorhizobiumMelilotiUniProtSpeciesProfile extends UniProtSpeciesProfile {
    
    public SinorhizobiumMelilotiUniProtSpeciesProfile() {
        super("Sinorhizobium meliloti",
            266834,
            "This profile customizes the GenMAPP Builder export for " +
                "Sinorhizobium meliloti" +
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
            { "Link", "http://cmr.jcvi.org/tigr-scripts/CMR/shared/GenePage.cgi?locus=~" }
        });
    
        return tableManager;
    }
    
    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        List<String> comparisonList = new ArrayList<String>();
        comparisonList.add("ordered locus");
        comparisonList.add("ORF");

        return systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, "OrderedLocusNames", comparisonList);
    }
}
