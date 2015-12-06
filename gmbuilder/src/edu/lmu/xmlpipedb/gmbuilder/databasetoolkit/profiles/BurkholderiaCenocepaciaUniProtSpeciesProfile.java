package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * @author Anindita Varshneya 
 */

public class BurkholderiaCenocepaciaUniProtSpeciesProfile extends UniProtSpeciesProfile {
	
	public BurkholderiaCenocepaciaUniProtSpeciesProfile() {
	    super("Burkholderia cenocepacia",
	    		216591,
	        "This profile customizes the GenMAPP Builder export for " +
	            "Burkholderia cenocepacia" +
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
	        { "Link", "http://www.burkholderia.com/getAnnotation.do?locusID=~" }
	    });
	
	    return tableManager;
	}
	
	/**
	 * Modified code from LeishmaniaMajorUniProtSpeciesProfile.java
	 */
    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) 
    		throws SQLException, InvalidParameterException {
        List<String> comparisonList = new ArrayList<String>(1);
        comparisonList.add("ORF");

        return systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, "ORF", comparisonList);
    }
}

