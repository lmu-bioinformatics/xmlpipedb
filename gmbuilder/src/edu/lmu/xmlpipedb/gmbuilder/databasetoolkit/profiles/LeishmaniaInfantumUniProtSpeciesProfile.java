package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

public class LeishmaniaInfantumUniProtSpeciesProfile extends LeishmaniaMajorUniProtSpeciesProfile {
    
    public LeishmaniaInfantumUniProtSpeciesProfile() {
        super("Leishmania infantum",
            5671,
            "This profile customizes the GenMAPP Builder export for " +
                "Leishmania infantum" +
                " data loaded from a UniProt XML file.");
    }
    
    protected String getIDPattern() {
        return "L[Ii][Nn]J%";
    }
    
    protected String getExportPrefix() {
        return "LinJ";
    }

}
