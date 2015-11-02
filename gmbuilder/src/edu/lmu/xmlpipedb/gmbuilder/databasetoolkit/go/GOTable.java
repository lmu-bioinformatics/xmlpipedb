package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;

/**
 * GOTable represents the information needed for creating and inserting into a
 * particular GO-related table.
 */
public enum GOTable {

    GeneOntologyTree("GeneOntologyTree", new String[][] {
                { "OrderNo", "LONG" }, { "Level", "INT" }, { "ID", "VARCHAR(50)" }, { "Name", "MEMO" }
            }),
            
    GeneOntology("GeneOntology", new String[][] {
                { "ID", "VARCHAR(50) NOT NULL" }, { "Name", "MEMO" }, { "Type", "VARCHAR(2)" },
                { "Parent", "VARCHAR(50)" }, { "Relation", "CHAR" }, { "Species", "MEMO" },
                { "Date", "DATE" }, { "Remarks", "MEMO" }
            }),
            
    GeneOntologyStage("GeneOntologyStage", new String[][] {
                { "ID", "VARCHAR(50) NOT NULL" }, { "Name", "VARCHAR" }, { "Type", "VARCHAR(2)" },
                { "Parent", "VARCHAR(50)" }, { "Relation", "CHAR" }, { "Species", "VARCHAR" },
                { "Date", "DATE" }, { "Remarks", "VARCHAR" }
            }),
            
    UniProt_GoCount("UniProt-GOCount", new String[][] {
                { "GO", "VARCHAR(50) NOT NULL" }, { "Count", "INT" }, { "Total", "LONG" }
            }),
            
    GeneOntologyCount("GeneOntologyCount", new String[][] {
                { "ID", "VARCHAR(50) NOT NULL" }, { "Count", "INT" }
            }),
            
    UniProt_Go("UniProt-GeneOntology", new String[][] {
                { "Primary", "VARCHAR(50) NOT NULL" }, { "Related", "VARCHAR(50) NOT NULL" },
                { "Bridge", "VARCHAR(3) NOT NULL" }
            });

    public String getName() {
        return name;
    }

    public Map<String, String> getColumnsToTypes() {
        return columnsToTypes;
    }

    public List<String> columnsInOrder() {
        return columnsInOrder;
    }

    public String getCreate() {
        StringBuilder sb = new StringBuilder("CREATE TABLE ")
            .append(name)
            .append(" (");
        
        sb.append(String.join(",", columnsInOrder.stream().map(
                columnName -> columnName + " " + columnsToTypes.get(columnName)
            ).toArray(String[]::new)))
            .append(")");

        return sb.toString();
    }

    public String getInsert() {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(name);
        sb.append(" (");
        sb.append(String.join(",", columnsInOrder));
        sb.append(") VALUES (");
        
        String[] placeholders = new String[columnsInOrder.size()];
        for (int i = 0; i < placeholders.length; i++) {
            placeholders[i] = "?";
        }
        sb.append(String.join(",", placeholders));
        sb.append(")");
        return sb.toString();
    }

    private GOTable(String name, String[][] columnsToTypesAsArray) {
        this.name = name;
        columnsToTypes = GenMAPPBuilderUtilities.string2DArrayToMap(columnsToTypesAsArray);
        columnsInOrder = new ArrayList<String>(columnsToTypesAsArray.length);
        for (String[] columnToType: columnsToTypesAsArray) {
            columnsInOrder.add(columnToType[0]);
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    private String name;
    private Map<String, String> columnsToTypes;
    private List<String> columnsInOrder;
}
