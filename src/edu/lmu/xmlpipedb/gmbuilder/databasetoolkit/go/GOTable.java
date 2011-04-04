package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

/**
 * GOTable represents the information needed for creating and inserting into a
 * particular GO-related table.
 * 
 * @author dondi
 */
public enum GOTable {
    GeneOntologyTree("GeneOntologyTree",
            "create table GeneOntologyTree (OrderNo LONG,\"Level\" Int,ID VARCHAR(50),Name MEMO)",
            // Non-Access column types
//          {"varchar", "varchar", "VARCHAR(50)", "varchar"}, /* GOTree */
            "insert into GeneOntologyTree (OrderNo,\"Level\",ID,Name) values (?,?,?,?)"),
            
    GeneOntology("GeneOntology",
            "create table GeneOntology (ID VARCHAR(50) NOT NULL,Name MEMO,Type VARCHAR(2),Parent VARCHAR(50),Relation CHAR,Species MEMO,\"Date\" DATE,Remarks MEMO)",
            // Non-Access column types
//          {"VARCHAR(50) NOT NULL", "varchar", "VARCHAR(2)","VARCHAR(50)","CHAR","varchar", "varchar", "varchar"}, /* GO */
            "insert into GeneOntology (ID,Name,Type,Parent,Relation,Species,\"Date\",Remarks) values (?,?,?,?,?,?,?,?)"),
            
    GeneOntologyStage("GeneOntologyStage",
            "create table GeneOntologyStage (ID varchar(50) not null,Name varchar,Type varchar(2),Parent varchar(50),Relation char,Species varchar,\"Date\" varchar,Remarks varchar)",
            // Access table DDL
//            "create table GeneOntologyStage (ID VARCHAR(50) NOT NULL,Name MEMO,Type VARCHAR(2),Parent VARCHAR(50),Relation CHAR,Species MEMO,\"Date\" DATE,Remarks MEMO)",
            "insert into GeneOntologyStage (ID,Name,Type,Parent,Relation,Species,\"Date\",Remarks) values (?,?,?,?,?,?,?,?)"),
            
    UniProt_GoCount("\"UniProt-GOCount\"",
            "create table \"UniProt-GOCount\" (GO VARCHAR(50) NOT NULL,Count Int,Total Long)",
            // Non-Access column types
//          {"VARCHAR(50) NOT NULL", "varchar", "varchar"}  /* UniProt-GoCount */
            "insert into \"UniProt-GOCount\" (GO,Count,Total) values (?,?,?)"),
            
    GeneOntologyCount("GeneOntologyCount",
            "create table GeneOntologyCount (ID VARCHAR(50) NOT NULL,Count Int)",
            // Non-Access column types
//          {"VARCHAR(50) NOT NULL", "varchar"}, /* GOCount */
            "insert into GeneOntologyCount (ID,Count) values (?,?)"),
            
    UniProt_Go("\"UniProt-GeneOntology\"",
            "create table \"UniProt-GeneOntology\" (\"Primary\" VARCHAR(50) NOT NULL,Related VARCHAR(50) NOT NULL,Bridge VARCHAR(3) NOT NULL)",
            // Non-Access column types
//          {"VARCHAR(50) NOT NULL", "VARCHAR(50) NOT NULL", "VARCHAR(3) NOT NULL"},  /* UniProt-Go */
            "insert into \"UniProt-GeneOntology\" (\"Primary\",Related,Bridge) values (?,?,?)");

    /**
     * Returns the table's name.
     * 
     * @return The table's name
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns the SQL string for creating this table.
     * 
     * @return SQL string for creating the table
     */
    public String getCreate() {
        return _create;
    }
    
    /**
     * Returns the SQL string for inserting into this table.
     * 
     * @return SQL string for inserting into the table
     */
    public String getInsert() {
        return _insert;
    }
    
    /**
     * Private constructor for building a GOTable enum instance.
     */
    private GOTable(String name, String create, String insert) {
        _name = name;
        _create = create;
        _insert = insert;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * This table's name.
     */
    private String _name;

    /**
     * This table's creation command.
     */
    private String _create;
    
    /**
     * This tables insert command.
     */
    private String _insert;
}
