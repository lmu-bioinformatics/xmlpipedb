package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

public class BordetellaPertussisUniProtSpeciesProfile extends UniProtSpeciesProfile {

    public BordetellaPertussisUniProtSpeciesProfile() {
        super("Bordetella pertussis", 257313, "This profile customizes the GenMAPP Builder export for " +
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

    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager,
            TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        /*
         * This method is only called (and therefore this bit 'o logic is only
         * invoked) when the species specific class has not overridden this
         * method.
         */
        List<String> comparisonList = new ArrayList<String>(1);
        comparisonList.add("ordered locus");
        comparisonList.add("ORF");

        return systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version,
                "OrderedLocusNames", comparisonList);
    }
}
