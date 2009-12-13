package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for the Go class.
 * 
 * @author   dondi
 * @since    JUnit 4.x
 */
public class GOTableTest {
    /**
     * Tests the correctness of the number of GOTable instances.
     */
    @Test
    public void testSize() {
        // Verify the number of enumerated instances.
        assertEquals(6, GOTable.values().length);
    }

    /**
     * Tests the correctness of the names of the GOTable instances.
     */
    @Test
    public void testName() {
        assertEquals("GeneOntologyTree", GOTable.GeneOntologyTree.getName());
        assertEquals("GeneOntology", GOTable.GeneOntology.getName());
        assertEquals("GeneOntologyStage", GOTable.GeneOntologyStage.getName());
        assertEquals("\"UniProt-GOCount\"", GOTable.UniProt_GoCount.getName());
        assertEquals("GeneOntologyCount", GOTable.GeneOntologyCount.getName());
        assertEquals("\"UniProt-GeneOntology\"", GOTable.UniProt_Go.getName());
    }

    /**
     * Tests the correctness of the create commands for GOTable instances.
     */
    @Test
    public void testGetCreateMap() {
        // Verify each command.
        assertEquals("create table GeneOntologyTree (OrderNo LONG,\"Level\" Int,ID VARCHAR(50),Name MEMO)", GOTable.GeneOntologyTree.getCreate());
        assertEquals("create table GeneOntology (ID VARCHAR(50) NOT NULL,Name MEMO,Type VARCHAR(2),Parent VARCHAR(50),Relation CHAR,Species MEMO,\"Date\" DATE,Remarks MEMO)", GOTable.GeneOntology.getCreate());
        assertEquals("create table GeneOntologyStage (ID varchar(50) not null,Name varchar,Type varchar(2),Parent varchar(50),Relation char,Species varchar,\"Date\" varchar,Remarks varchar)", GOTable.GeneOntologyStage.getCreate());
        assertEquals("create table \"UniProt-GOCount\" (GO VARCHAR(50) NOT NULL,Count Int,Total Long)", GOTable.UniProt_GoCount.getCreate());
        assertEquals("create table GeneOntologyCount (ID VARCHAR(50) NOT NULL,Count Int)", GOTable.GeneOntologyCount.getCreate());
        assertEquals("create table \"UniProt-GeneOntology\" (\"Primary\" VARCHAR(50) NOT NULL,Related VARCHAR(50) NOT NULL,Bridge VARCHAR(3) NOT NULL)", GOTable.UniProt_Go.getCreate());
    }

    /**
     * Tests the correctness of the insert command for GOTable instances.
     */
    @Test
    public void testGetInsertMap() {
        // Verify each command.
        assertEquals("insert into GeneOntologyTree (OrderNo,\"Level\",ID,Name) values (?,?,?,?)", GOTable.GeneOntologyTree.getInsert());
        assertEquals("insert into GeneOntology (ID,Name,Type,Parent,Relation,Species,\"Date\",Remarks) values (?,?,?,?,?,?,?,?)", GOTable.GeneOntology.getInsert());
        assertEquals("insert into GeneOntologyStage (ID,Name,Type,Parent,Relation,Species,\"Date\",Remarks) values (?,?,?,?,?,?,?,?)", GOTable.GeneOntologyStage.getInsert());
        assertEquals("insert into \"UniProt-GOCount\" (GO,Count,Total) values (?,?,?)", GOTable.UniProt_GoCount.getInsert());
        assertEquals("insert into GeneOntologyCount (ID,Count) values (?,?)", GOTable.GeneOntologyCount.getInsert());
        assertEquals("insert into \"UniProt-GeneOntology\" (\"Primary\",Related,Bridge) values (?,?,?)", GOTable.UniProt_Go.getInsert());
    }
}
