package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

public class SaccharomycesCerevisiaeUniProtSpeciesProfile extends
		UniProtSpeciesProfile {

    public SaccharomycesCerevisiaeUniProtSpeciesProfile() {
	    super("Saccharomyces cerevisiae",
	       "This profile customizes the GenMAPP Builder export for Saccharomyces cerevisiae data loaded from a UniProt XML file.");
	}

    /**
     * For this species, the SGD table has a different schema, so we DON'T want
     * any such rows.
     * 
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.UniProtSpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, java.util.Date)
     */
    @Override
    public TableManager getSystemTableManagerCustomizations(
            TableManager tableManager, TableManager primarySystemTableManager,
            Date version) throws SQLException, InvalidParameterException {
        tableManager.removeTableRowsFor("SGD");
        return tableManager;
    }

    @Override
	public TableManager getSystemsTableManagerCustomizations(TableManager tableManager, DatabaseProfile dbProfile) {
	    super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
	    tableManager.submit("Systems", QueryType.update, new String[][] {
	        { "SystemCode", "D" },
	        { "Species", "|" + getSpeciesName() + "|" }
	    });

	    tableManager.submit("Systems", QueryType.update, new String[][] {
	        { "SystemCode", "D" },
	        { "Link", "http://www.yeastgenome.org/cgi-bin/locus.fpl?dbid=~" }
	    });

	    tableManager.submit("Systems", QueryType.update, new String[][] {
		        { "SystemCode", "En" },
		        { "Species", "|" + getSpeciesName() + "|" }
		});

	    tableManager.submit("Systems", QueryType.update, new String[][] {
		        { "SystemCode", "En" },
		        { "Link", "http://www.ensembl.org/Saccharomyces_cerevisiae/Gene/Summary?g=~" }
		});


	    return tableManager;
	}

    @Override
    public List<TableManager> getSpeciesSpecificCustomTables(Date version) throws SQLException {
        TableManager tableManager = null;
        PreparedStatement ps;
        int recordCounter = 0;

        String querySQL = "select et.hjid, tsgd.id, tgp.value as symbol, tgol.value as orf from entrytype et left outer join ( select entrytype_dbreference_hjid as hjid, id from dbreferencetype left outer join entrytype on (entrytype_dbreference_hjid =  entrytype.hjid) where type = 'SGD' ) as tsgd on (et.hjid = tsgd.hjid) left outer join ( select b.entrytype_gene_hjid as hjid, a.value from genenametype a left outer join entrytype_genetype b on (entrytype_genetype_name_hjid =  b.hjid) where a.type = 'primary' ) as tgp on (et.hjid = tgp.hjid) left outer join ( select b.entrytype_gene_hjid as hjid, a.value from genenametype a left outer join entrytype_genetype b on (entrytype_genetype_name_hjid = b.hjid) where a.type = 'ordered locus' ) as tgol  on (et.hjid = tgol.hjid) order by tsgd.id; ";
        
        tableManager = new TableManager(new String[][] {
                { "ID", "VARCHAR(50) NOT NULL" },
                { "Symbol", "VARCHAR(50) NOT NULL" },
                { "ORF", "VARCHAR(50) NOT NULL" }, 
                { "Species", "MEMO" },
                { "\"Date\"", "DATE" }, { "Remarks", "MEMO" }
            }, new String[] { "UID" });

        ps = ConnectionManager.getRelationalDBConnection().prepareStatement(querySQL);
        ResultSet result = ps.executeQuery();
        
        while (result.next()) {
            String sgdRow = getSGDRow(result);
            _Log.debug("\nRecord: [" + ++recordCounter + "]");
            _Log.debug(sgdRow);
            
            if (result.getString("id") == null) {
                _Log.error("The following record does not have an SGD ID. The record will be skipped:" + sgdRow);
                continue;
            }

            if (result.getString("orf") == null) {
                _Log.error("The following record does not have an ORF name. The record will be skipped:" + sgdRow);
                continue;
            }

            String symbol = result.getString("symbol");
            if (symbol == null) {
                // No need to check for ORF being null, since we wouldn't even
                // get here if it were.
                _Log.info("Found no symbol but had an ORF: will use the ORF to fill in the symbol:" + sgdRow);
                symbol = result.getString("orf");
            }
            
            tableManager.submit("SGD", QueryType.insert, new String[][] {
                { "UID", result.getString("hjid") },
                { "ID", result.getString("id") },
                { "Symbol", symbol }, 
                { "ORF", result.getString("orf") },
                { "Species", "|" + getSpeciesName() + "|" },
                { "\"Date\"", GenMAPPBuilderUtilities.getSystemsDateString(version) }
            });
        }
        ps.close();

        Row[] tmrows = tableManager.getRows();
        _Log.info("End of Method - Number of rows in TM: [" + tmrows.length + "]");

        // OK, ready to go.
        List<TableManager> tableManagerList = new ArrayList<TableManager>();
        tableManagerList.add(tableManager);
        return tableManagerList;
    }

    private String getSGDRow(ResultSet sgdResult) throws SQLException {
        return "hjid, id, symbol, orf\n" +
            sgdResult.getString("hjid") + ", " +
            sgdResult.getString("id") + ", " +
            sgdResult.getString("symbol") + ", " +
            sgdResult.getString("orf");
    }

    private static final Log _Log = LogFactory.getLog(SaccharomycesCerevisiaeUniProtSpeciesProfile.class);

}
