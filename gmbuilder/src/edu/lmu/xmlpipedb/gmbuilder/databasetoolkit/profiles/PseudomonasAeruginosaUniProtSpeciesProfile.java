package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

public class PseudomonasAeruginosaUniProtSpeciesProfile extends
		UniProtSpeciesProfile {

	public PseudomonasAeruginosaUniProtSpeciesProfile() {
	    super("Pseudomonas aeruginosa",
	       "This profile customizes the GenMAPP Builder export for Pseudomonas aeruginosa data loaded from a UniProt XML file.");
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
	        { "Link", "http://www.pseudomonas.com/getAnnotation.do?locusID=~" }
	    });

	    return tableManager;
	}

    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        List<String> comparisonList = new ArrayList<String>(1);
        comparisonList.add("ordered locus");

        return systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, "OrderedLocusNames", comparisonList );
    }

}
