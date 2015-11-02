package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GOTableTest {
    @Test
    public void testSize() {
        // Verify the number of enumerated instances.
        assertEquals(6, GOTable.values().length);
    }

    @Test
    public void testName() {
        assertEquals("GeneOntologyTree", GOTable.GeneOntologyTree.getName());
        assertEquals("GeneOntology", GOTable.GeneOntology.getName());
        assertEquals("GeneOntologyStage", GOTable.GeneOntologyStage.getName());
        assertEquals("\"UniProt-GOCount\"", GOTable.UniProt_GoCount.getName());
        assertEquals("GeneOntologyCount", GOTable.GeneOntologyCount.getName());
        assertEquals("\"UniProt-GeneOntology\"", GOTable.UniProt_Go.getName());
    }


    @Test
    public void testGetInsertMap() {
        assertEquals("insert into GeneOntologyTree (OrderNo,\"Level\",ID,Name) values (?,?,?,?)", GOTable.GeneOntologyTree.getInsert());
        assertEquals("insert into GeneOntology (ID,Name,Type,Parent,Relation,Species,[Date],Remarks) values (?,?,?,?,?,?,?,?)", GOTable.GeneOntology.getInsert());
        assertEquals("insert into GeneOntologyStage (ID,Name,Type,Parent,Relation,Species,[Date],Remarks) values (?,?,?,?,?,?,?,?)", GOTable.GeneOntologyStage.getInsert());
        assertEquals("insert into \"UniProt-GOCount\" (GO,Count,Total) values (?,?,?)", GOTable.UniProt_GoCount.getInsert());
        assertEquals("insert into GeneOntologyCount (ID,Count) values (?,?)", GOTable.GeneOntologyCount.getInsert());
        assertEquals("insert into \"UniProt-GeneOntology\" ([Primary],Related,Bridge) values (?,?,?)", GOTable.UniProt_Go.getInsert());
    }
}
