/********************************************************
 * Filename: ExportGoData.java
 * Author: LMU
 * Program: gmBuilder
 * Description: Export the data to the access database.
 * Revision History:
 * 20060422: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import generated.impl.IdImpl;
import generated.impl.IsAImpl;
import generated.impl.NameImpl;
import generated.impl.NamespaceImpl;
import generated.impl.RelationshipImpl;
import generated.impl.TermImpl;
import generated.impl.ToImpl;

public class ExportGoData {

	public ExportGoData() {
        orderNo = 1;
        godb = new Go();
        namespace = new HashMap<String, String>();
        goCount = new HashMap<String, Integer>();
        duplicates = new HashMap<String, Boolean>();
        createNamespaceMappings();
    }

    /**
     * Staring point for exporting go data to genMAPP with multiple species
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws HibernateException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     */
    public void export(List<DatabaseProfile.GOAspect> chosenAspects, List<Integer> taxonIds)
            throws ClassNotFoundException, SQLException, HibernateException, SAXException, IOException, JAXBException {
        Database exportDatabase = ConnectionManager.getGenMAPPDB();
        godb.createTables(exportDatabase);
        exportDatabase.flush();
        exportDatabase.close();

        populateGoTables(chosenAspects, taxonIds);
        
        Connection exportConnection = ConnectionManager.getGenMAPPDBConnection();
        godb.updateSystemsTable(exportConnection, new Date(), "T");
        exportConnection.close();

        LOG.info("done!");
    }
    
    /**
     * Create a staging database of the entire GeneOntology, including all
     * species
     *
     * @param session
     *            A hibernate session (needed for HQL queries)
     */
    // FIXME Design-wise, this is somewhat out of place because this operation pertains to the
    // *source* relational database, not the destination export database.
    public void populateGeneOntologyStage(Connection connection, Configuration hibernateConfiguration)
            throws SQLException {
        SessionFactory sessionFactory = hibernateConfiguration.buildSessionFactory();
        // open Hibernate session
        Session session = sessionFactory.openSession();

        LOG.info("creating: " + GOTable.GeneOntologyStage);
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("drop table " + GOTable.GeneOntologyStage);
            ps.executeUpdate();
        } catch(SQLException sqlexc) {
            // This may occur if the table does not exist yet.
            LOG.info("Exception when dropping stage table", sqlexc);
            try { connection.rollback(); } catch(Exception exc) { LOG.error(exc); }
        } finally {
            try { ps.close(); } catch(Exception exc) { LOG.error(exc); }
        }

        // We don't catch this one because table creation MUST succeed.
        try {
            ps = connection.prepareStatement(GOTable.GeneOntologyStage.getCreate());
            ps.executeUpdate();
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
                LOG.error(exc);
            }
        }

        Iterator<?> iter = null;
        String Species = null;
        String Remarks = null;

        // Get today's date
        Date date = new Date();

        // Grab all term object
        LOG.debug("Performing query...");
        iter = (Iterator<?>)session.createQuery("from generated.impl.TermImpl").iterate();

        LOG.debug("Beginning iteration...");
        LOG.debug("Beginning " + GOTable.GeneOntologyStage + " transaction");
        connection.setAutoCommit(false);
        long counter = 0;
        while (iter.hasNext()) {
            TermImpl term = (TermImpl)iter.next();
            List<?> content = term.getContent();
            LOG.debug("Processing term " + term.getHjid() +", content size = " + content.size());
            counter++;
            if (counter % 1000 == 0) {
                LOG.info("Now at term " + counter);
            }

            // Each term may contain a number of objects
            String Id = "";
            String Name = "";
            String Type = "";
            String Parent = "";
            String Relation = "";
            boolean isRoot = false;

            /*
             * Each Term may have more than one parent (is_a) and a part_of. In
             * that case, create an entry for each object. Thus each term may
             * have max of three entries: two parents and one part of
             */
            for (Object o: content) {
                LOG.debug("Term content: " + o);
                if (o instanceof generated.impl.IdImpl) {
                    // Strip off "GO:" from ID
                    Id = (((IdImpl)o).getContent()).substring(3);
                } else if (o instanceof generated.impl.NameImpl) {
                    Name = ((NameImpl)o).getContent();
                } else if (o instanceof generated.impl.NamespaceImpl) {
                    Type = namespace.get(((NamespaceImpl)o).getContent());
                } else if (o instanceof generated.impl.IsRootImpl) {
                    isRoot = true;
                } else if (o instanceof generated.impl.IsAImpl) {
                    // Strip off "GO:" from parent
                    Parent = (((IsAImpl)o).getContent()).substring(3);
                    Relation = "%";
                } else if (o instanceof generated.impl.RelationshipImpl) {
                    ToImpl to = (ToImpl)((RelationshipImpl)o).getContent().get(1);
                    Parent = (to.getContent()).substring(3);
                    Relation = "<";
                }

                if (Id != "" && Name != "" && Type != "") {
                    if (isRoot) {
                        // create root ID entry
                        Object[] values = { Id, Name, Type, null, null, null, date, null };
                        godb.insert(connection, GOTable.GeneOntologyStage, values);
                        isRoot = false;
                    } else if (Parent != "" && Relation != "") {
                        // create child ID
                        Object[] values = { Id, Name, Type, Parent, Relation, Species, date, Remarks };
                        godb.insert(connection, GOTable.GeneOntologyStage, values);
                        Relation = "";
                    }
                }
            }
        }

        LOG.info("Committing " + GOTable.GeneOntologyStage);
        connection.commit();

        session.close();
    }

    /**
     * A mapping from obo.xml namespaces to GO types
     */
    private void createNamespaceMappings() {
        namespace.put("biological_process", "P");
        namespace.put("molecular_function", "F");
        namespace.put("cellular_component", "C");
    }

    private void populateGoTables(List<DatabaseProfile.GOAspect> chosenAspects, List<Integer> taxonIds)
            throws SQLException, HibernateException, SAXException, IOException, JAXBException, ClassNotFoundException {
        LOG.info("Populating UniProt-GO table...");
        populateUniprotGoTableFromSQL(chosenAspects, taxonIds);
        LOG.info("Populating GeneOntology table...");
        populateGeneOntology();
        LOG.info("Populating GeneOntologyTree...");
        populateGeneOntologyTree();
        LOG.info("Populating GeneOntologyCount...");
        populateGeneOntologyCount();
        LOG.info("Populating UniProt-GO count...");
        populateUniProtGoCount();
    }

    private void populateUniProtGoCount() throws SQLException, IOException, ClassNotFoundException {
        LOG.info("creating: " + GOTable.UniProt_GoCount);
        // Create an entry for each unique GO ID
        String sql = "select ID from " + GOTable.GeneOntologyCount;

        Database gdb = ConnectionManager.getGenMAPPDB();
        Connection gdbConnection = ConnectionManager.getGenMAPPDBConnection();
        ResultSet goids = gdbConnection.prepareStatement(sql).executeQuery();
        while (goids.next()) {
            String id = goids.getString(1);
            int count = 0;
            Set<String> uniprotIds = new HashSet<String>();
            for (Row row: gdb.getTable(GOTable.UniProt_Go.getName())) {
                if (id.equals(row.getString("Related"))) {
                    count++;
                    uniprotIds.add(row.getString("Primary"));
                }
            }

            // Count the number of times each GO ID child maps to a unique UP ID
            // as defined in the Uniprot-GO table
            getTotalCount(gdb, id, uniprotIds);
            LOG.debug("Inserting UniProt-GO count record (" + id + ", " + count + ")");
            godb.insert(gdb, GOTable.UniProt_GoCount, new Object[] { id, count, uniprotIds.size() });
        }

        // Get Overall Totals for Entire GO Tree
        Table uniProtGoTable = gdb.getTable(GOTable.UniProt_Go.getName());
        Set<String> seenPrimaries = new HashSet<String>();
        for (Row row: uniProtGoTable) {
            seenPrimaries.add(row.getString("Primary"));
        }
        godb.insert(gdb, GOTable.UniProt_GoCount, new Object[] { "GO", 0, (long)seenPrimaries.size() });

        gdbConnection.close();
        gdb.flush();
        gdb.close();
    }

    /**
     * Recursively counts the number of time each GO ID maps to a unique Uniprot
     * ID as defined in the Uniprot-GO table
     *
     * @param parent
     *            parent GO ID
     * @param uniprotIds
     *            map of existing Uniprot IDs
     * @throws SQLException
     */
    private void getTotalCount(Database gdb, String parent, Set<String> uniprotIds)
            throws IOException {
        gdb.getTable(GOTable.GeneOntology.getName()).forEach(row -> {
            if (parent.equals(row.getString("Parent"))) {
                String id = row.getString("ID");
                try {
                    gdb.getTable(GOTable.UniProt_Go.getName()).forEach(pairRow -> {
                        if (id.equals(pairRow.getString("Related"))) {
                            uniprotIds.add(pairRow.getString("Primary"));
                        }
                    });
                    getTotalCount(gdb, id, uniprotIds);
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        });
    }

    /**
     * Populates GenMAPP's UniProt-GeneOnotlogy table for multiple species, 
     * using a GOA table from a PostgreSQL database instead of a GOA file
     *
     * @throws SQLException
     */
    private void populateUniprotGoTableFromSQL(List<DatabaseProfile.GOAspect> chosenAspects, List<Integer> taxonIds) throws SQLException {
    	HashMap<String, Boolean> unique = new HashMap<String, Boolean>();
        StringBuilder baseQueryBuilder = 
        	new StringBuilder( "select db_object_id, go_id, evidence_code, with_or_from from goa where db like '%UniProt%'" );
        boolean first = true;
        
        for (int taxon: taxonIds) {
            baseQueryBuilder
                .append(first ? " and (" : " or ")
                .append("taxon = 'taxon:")
                .append(taxon)
                .append("'");

            first = false;
        }

        baseQueryBuilder.append(")");

    	PreparedStatement uniProtAndGOIDPS = null;
    	LOG.info("creating: " + GOTable.UniProt_Go);

    	if (chosenAspects.size() < 3) { // Not all aspects were chosen.
    	    first = true;
            for (DatabaseProfile.GOAspect aspect: chosenAspects) {
                baseQueryBuilder
                    .append(first ? " and (" : " or ")
                    .append("aspect = '")
                    .append(aspect.name().charAt(0)) // The aspect code is the enum's first character.
                    .append("'");
                first = false;
            }
            baseQueryBuilder.append(")");
    	}

    	Database exportDatabase = null;
    	try {
    	    exportDatabase = ConnectionManager.getGenMAPPDB();
    		uniProtAndGOIDPS = ConnectionManager.getRelationalDBConnection().prepareStatement(baseQueryBuilder.toString());
    		ResultSet uniProtAndGOIDRS = uniProtAndGOIDPS.executeQuery();
    		while (uniProtAndGOIDRS.next()) {
    			String uniProtID = uniProtAndGOIDRS.getString("db_object_id");
    			uniProtID = uniProtID.trim();

    			String goID = uniProtAndGOIDRS.getString("go_id");
    			if (goID.startsWith("GO:")) {
    				goID = goID.substring(3);
    			}

    			goID = goID.trim();

    			String evidenceCode = uniProtAndGOIDRS.getString("evidence_code");
    			evidenceCode = evidenceCode.trim();

    			String withOrFrom = uniProtAndGOIDRS.getString("with_or_from");
    			if (withOrFrom.startsWith("GO:")) {
    				withOrFrom = withOrFrom.substring(3);
    			}
    			withOrFrom = withOrFrom.trim();

    			String key = uniProtID + "," + goID;
    			if (!unique.containsKey(key)) {
    				unique.put(key, true);
    				LOG.debug("UniProt-GO pair: " + uniProtID + ", " + goID);
                    godb.insert(exportDatabase, GOTable.UniProt_Go, new String[] { uniProtID, goID, "" });
    			}

    			if ("IC".equals(evidenceCode)) {
    				key = uniProtID + "," + withOrFrom;
        			if (!unique.containsKey(key)) {
        				unique.put(key, true);
        				LOG.debug("UniProt-GO pair: " + uniProtID + ", " + goID);
                        godb.insert(exportDatabase, GOTable.UniProt_Go, new String[] { uniProtID, withOrFrom, "" });
        			}
    			}
    		}
        } catch(SQLException sqlexc) {
            throw sqlexc;
        } catch(Exception exc) {
            LOG.error(exc);
        } finally {
            try {
                uniProtAndGOIDPS.close();
                exportDatabase.flush();
                exportDatabase.close();
            } catch (Exception exc) {
                LOG.error(exc);
            }
        }
    }

    /**
     * Populates genMAPP's GeneOntology table based on a user selected species
     *
     * @throws SQLException
     */
    private void populateGeneOntology() throws SQLException {
        String stageSQL = "select * from " + GOTable.GeneOntologyStage + " where Id = ? order by Id";

        Database gdb = null;
        PreparedStatement stagePS = null;
        LOG.info("creating: " + GOTable.GeneOntology);
        try {
            gdb = ConnectionManager.getGenMAPPDB();

            stagePS = ConnectionManager.getRelationalDBConnection().prepareStatement(stageSQL);

            // Grab the distinct related IDs from UniProt-GeneOntology.
            // For each of these IDs, grab the corresponding stage records, and process.
            Table uniProtGoTable = gdb.getTable(GOTable.UniProt_Go.getName());
            Set<String> seenRelateds = new HashSet<String>();
            for (Row row: uniProtGoTable) {
                // Note: Difference from original is that order is no longer guaranteed here.
                // May need to restore order if that somehow mattered.
                String relatedString = row.getString("Related");
                if (!seenRelateds.contains(relatedString)) {
                    seenRelateds.add(relatedString);
                    LOG.debug("Processing related term: " + relatedString);
                    stagePS.setString(1, relatedString);
                    processIDs(gdb, stagePS);
                }
            }
        } catch(SQLException sqlexc) {
            throw sqlexc;
        } catch(Exception exc) {
            LOG.error(exc);
            for (StackTraceElement stackTraceElement: exc.getStackTrace()) {
                LOG.error("    " + stackTraceElement);
            }
        } finally {
            try {
                stagePS.close();
                gdb.flush();
                gdb.close();
            } catch (Exception exc) {
                LOG.error(exc);
            }
        }
    }

    /**
     * Recursively inserts data based on a parent until reaching the root
     *
     * @param id
     *            parent ID
     * @throws SQLException
     */
    private void insertParents(Database gdb, String id) throws SQLException, IOException {
        String sql = "select * from " + GOTable.GeneOntologyStage + " where Id = ? order by Id";
        PreparedStatement ps = null;
        try {
            ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sql);
            ps.setString(1, id);
            processIDs(gdb, ps);
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
                LOG.error(exc);
            }
        }
    }

    private void processIDs(Database gdb, PreparedStatement stagePS) throws SQLException, IOException {
        ResultSet stageRS = null;
        try {
            stageRS = stagePS.executeQuery();
            while (stageRS.next()) {
                Object[] values = getGoValues(stageRS);
                String key = values[ID_COL] + "," + values[PARENT_COL];
                if (!duplicates.containsKey(key)) {
                    duplicates.put(key, true);
                    godb.insert(gdb, GOTable.GeneOntology, values);
                    insertParents(gdb, (String)values[PARENT_COL]);
                }
            }
        } finally {
            stageRS.close();
        }
    }

    private Object[] getGoValues(ResultSet results) throws SQLException {
        List<Object> values = new ArrayList<Object>(GOTable.GeneOntology.columnsInOrder().size());
        for (String columnName: GOTable.GeneOntology.columnsInOrder()) {
            Object value = results.getObject(columnName);
            values.add(value instanceof String ? ((String)value).trim() : value);
        }
        return values.toArray();
    }

    /**
     * Populate genMAPP's GeneOntologyTree
     *
     * @throws SQLException
     */
    private void populateGeneOntologyTree() throws SQLException, IOException, ClassNotFoundException {
        String[] rootIds = { "0003674", "0005575", "0008150" };
        String[] names = { "molecular_function", "cellular_component", "biological_process" };

        // Traverse the graph beginning with each root ID
        LOG.info("creating: " + GOTable.GeneOntologyTree);
        Connection gdbConnection = ConnectionManager.getGenMAPPDBConnection();
        for (int index = 0; index < rootIds.length; index++) {
            String id = rootIds[index];
            String name = names[index];
            goCount.put(id, 1);
            godb.insert(gdbConnection, GOTable.GeneOntologyTree, new Object[] { orderNo++, 1, id, name });
            insertChildren(gdbConnection, id, 2);
        }
        gdbConnection.close();
        
        LOG.info("Total number of GeneOntologyTree records: " + (orderNo - 1));
    }

    private void populateGeneOntologyCount() throws SQLException, IOException {
        LOG.info("creating: " + GOTable.GeneOntologyCount);
        Iterator<String> iter = goCount.keySet().iterator();
        Database gdb = ConnectionManager.getGenMAPPDB();
        while (iter.hasNext()) {
            String id = iter.next();
            int count = goCount.get(id);
            LOG.debug("Inserting GeneOntology count record (" + id + ", " + count + ")");
            godb.insert(gdb, GOTable.GeneOntologyCount, new Object[] { id, count });
        }
        gdb.flush();
        gdb.close();
    }

    /**
     * A recursive function that traverse each branch
     *
     * @param parent
     *            The parent ID
     * @param level
     *            The level in the tree
     * @throws SQLException
     */
    private void insertChildren(Connection gdbConnection, String parent, int level)
            throws SQLException, IOException {
        String sqlStatement = "SELECT name,id from " + GOTable.GeneOntology + " where parent = ? order by parent";

        PreparedStatement ps = gdbConnection.prepareStatement(sqlStatement);
        ps.setString(1, parent);
        ResultSet results = ps.executeQuery();
        while (results.next()) {
            String name = results.getString(1);
            String id = results.getString(2);
            // Keep a track of how many time each ID shows up in the tree
            if (goCount.containsKey(id)) {
                int count = goCount.get(id);
                goCount.put(id, count + 1);
            } else {
                goCount.put(id, 1);
            }

            LOG.debug("Inserting child row (" + id + ", " + name + ")");
            if (orderNo % 250 == 0) {
                LOG.info("Child rows inserted so far: " + orderNo);
            }

            godb.insert(gdbConnection, GOTable.GeneOntologyTree, new Object[] { orderNo++, level, id, name });
            insertChildren(gdbConnection, id, level + 1);
        }
        results.close();
        ps.close();
    }


    private static final Log LOG = LogFactory.getLog(ExportGoData.class);

    private static final int PARENT_COL = 4 - 1;
    private static final int ID_COL 	= 1 - 1;

    private long orderNo;

    private Go godb;
    private Map<String, String> namespace;
    private Map<String, Integer> goCount;
    private Map<String, Boolean> duplicates;
}
