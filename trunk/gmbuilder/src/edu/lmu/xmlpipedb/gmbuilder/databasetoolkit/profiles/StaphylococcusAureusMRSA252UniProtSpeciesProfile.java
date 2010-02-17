package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

public class StaphylococcusAureusMRSA252UniProtSpeciesProfile extends
		UniProtSpeciesProfile {
	public StaphylococcusAureusMRSA252UniProtSpeciesProfile (){
		super("Staphylococcus aureus (strain MRSA252)", "This profile customizes the GenMAPP Builder export for Staphylococcus Aureus MRSA 252 data loaded from a UniProt XML file");
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
				{ "Link", "http://www.genedb.org/genedb/Search?name=~&organism=saureusMRSA" }
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
