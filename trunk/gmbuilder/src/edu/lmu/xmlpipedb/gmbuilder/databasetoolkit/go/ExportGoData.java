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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import generated.impl.IdImpl;
import generated.impl.IsAImpl;
import generated.impl.NameImpl;
import generated.impl.NamespaceImpl;
import generated.impl.RelationshipImpl;
import generated.impl.TermImpl;
import generated.impl.ToImpl;

public class ExportGoData {
    /**
     * Constructor
     *
     * @param outputFile
     *            The genMAPP file to populate
     * @throws IOException
     *             I/O error
     */
    public ExportGoData(Connection connection) {
        orderNo = 1;
        this.connection = connection;
        godb = new Go();
        namespace = new HashMap<String, String>();
        goCount = new HashMap<String, Integer>();
        duplicates = new HashMap<String, Boolean>();
        createNamespaceMappings();
    }

    /**
     * Staring point for exporting go data to genMAPP
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws HibernateException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     */
    public void export(char chosenAspect) throws ClassNotFoundException, SQLException, HibernateException, SAXException, IOException, JAXBException {
        String Date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
//      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
//        ExportWizard.updateExportProgress(3, "GeneOntology export - creating tables...");
        godb.createTables(connection);
//      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
//        ExportWizard.updateExportProgress(10, "GeneOntology export - populating tables...");
        populateGoTables(chosenAspect);
//      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
//        ExportWizard.updateExportProgress(40, "GeneOntology export - flushing tables...");
        godb.updateSystemsTable(connection, Date, "T");
        _Log.info("done!");
    }

    /**
     * Create a staging database of the entire GeneOntology, including all
     * species
     *
     * @param session
     *            A hibernate session (needed for HQL queries)
     * @throws SQLException
     */
    public void populateGeneOntologyStage(Configuration hibernateConfiguration) throws SQLException {
        SessionFactory sessionFactory = hibernateConfiguration.buildSessionFactory();
        // open Hibernate session
        Session session = sessionFactory.openSession();

        _Log.info("creating: " + GOTable.GeneOntologyStage);
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("drop table " + GOTable.GeneOntologyStage);
            ps.executeUpdate();
        } catch(SQLException sqlexc) {
            // This may occur if the table does not exist yet.
            _Log.info("Exception when dropping stage table", sqlexc);
            try { connection.rollback(); } catch(Exception exc) { _Log.error(exc); }
        } finally {
            try { ps.close(); } catch(Exception exc) { _Log.error(exc); }
        }

        // We don't catch this one because table creation MUST succeed.
        try {
            ps = connection.prepareStatement(GOTable.GeneOntologyStage.getCreate());
            ps.executeUpdate();
        } finally {
            try { ps.close(); } catch(Exception exc) { _Log.error(exc); }
        }

        Iterator<?> iter = null;
        String Species = null;
        String Remarks = null;

        // Get today's date
        String Date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

        // Grab all term object
        _Log.debug("Performing query...");
        iter = (Iterator<?>)session.createQuery("from generated.impl.TermImpl").iterate();

        _Log.debug("Beginning iteration...");
        _Log.debug("Beginning " + GOTable.GeneOntologyStage + " transaction");
        connection.setAutoCommit(false);
        long counter = 0;
        while (iter.hasNext()) {
            TermImpl term = (TermImpl)iter.next();
            List<?> content = term.getContent();
            _Log.debug("Processing term " + term.getHjid() +", content size = " + content.size());
            counter++;
            if (counter % 1000 == 0) {
                _Log.info("Now at term " + counter);
            }

            // Each term may contain a number of objects
            String Id = "";
            String Name = "";
            String Type = "";
            String Parent = "";
            String Relation = "";
            boolean is_root = false;
            /*
             * Each Term may have more than one parent (is_a) and a part_of. In
             * that case, create an entry for each object. Thus each term may
             * have max of three entries: two parents and one part of
             */
            for (Object o: content) {
                _Log.debug("Term content: " + o);
                if (o instanceof generated.impl.IdImpl) {
                    // Strip off "GO:" from ID
                    Id = (((IdImpl)o).getContent()).substring(3);
                } else if (o instanceof generated.impl.NameImpl) {
                    Name = ((NameImpl)o).getContent();
                } else if (o instanceof generated.impl.NamespaceImpl) {
                    Type = namespace.get(((NamespaceImpl)o).getContent());
                } else if (o instanceof generated.impl.IsRootImpl) {
                    is_root = true;
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
                    if (is_root) {
                        // create root ID entry
                        String[] values = { Id, Name, Type, null, null, null, Date, null };
                        godb.insert(connection, GOTable.GeneOntologyStage, values);
                        is_root = false;
                    } else if (Parent != "" && Relation != "") {
                        // create child ID
                        String[] values = { Id, Name, Type, Parent, Relation, Species, Date, Remarks };
                        godb.insert(connection, GOTable.GeneOntologyStage, values);
                        Relation = "";
                    }
                }
            }
        }

        _Log.info("Committing " + GOTable.GeneOntologyStage);
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

    /**
     * Populate genMAPP's GO tables
     *
     * @throws SQLException
     * @throws HibernateException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     */
    private void populateGoTables(char chosenAspect) throws SQLException, HibernateException, SAXException, IOException, JAXBException {
        _Log.info("Populating UniProt-GO table...");
        //populateUniprotGoTable(goaFile);
        populateUniprotGoTableFromSQL(chosenAspect);
        _Log.info("Populating GeneOntology table...");
        populateGeneOntology();
        _Log.info("Populating GeneOntologyTree...");
        populateGeneOntologyTree();
        _Log.info("Populating GeneOntologyCount...");
        populateGeneOntologyCount();
        _Log.info("Populating UniProt-GO count...");
        populateUniProtGoCount();
//        _Log.info("Dropping GO stage...");
//        dropGOStage();
    }

//    private void dropGOStage() throws SQLException {
//        PreparedStatement ps = connection.prepareStatement("drop table " + GOTable.GeneOntologyStage);
//        ps.executeUpdate();
//        ps.close();
//    }

    private QueryHolder getUniProtIDs(String sql, String id) throws SQLException {
        QueryHolder qh = new QueryHolder();
        qh.ps = connection.prepareStatement(sql);
        qh.ps.setString(1, id);
        qh.rs = qh.ps.executeQuery();
        return qh;
    }

    /**
     * Populates genMAPP's Uniprot-GOCount table
     *
     * @throws SQLException
     */
    private void populateUniProtGoCount() throws SQLException {
        _Log.info("creating: " + GOTable.UniProt_GoCount);
        // Create an entry for each unique GO ID
        String sql = "select Id from " + GOTable.GeneOntologyCount;
        ResultSet goids = connection.prepareStatement(sql).executeQuery();
        while (goids.next()) {
            String id = goids.getString(1);
            QueryHolder qh = getUniProtIDs("select \"Primary\" from " + GOTable.UniProt_Go + " where Related = ?", id);
            int count = 0;
            HashMap<String, Boolean> uniprot_IDs = new HashMap<String, Boolean>();
            // Count the number of times each GO ID maps to a unique UP ID
            // as defined in the Uniprot-GO table
            while (qh.rs.next()) {
                ++count;
                uniprot_IDs.put(qh.rs.getString(1), true);
            }
            qh.close();
            // Count the number of times each GO ID child maps to a unique UP ID
            // as defined in the Uniprot-GO table
            getTotalCount(id, uniprot_IDs);
            _Log.debug("Inserting UniProt-GO count record (" + id + ", " + count + ")");
            String[] values = new String[] { id, count + "", uniprot_IDs.size() + "" };
            godb.insert(connection, GOTable.UniProt_GoCount, values);
        }

        // Get Overall Totals for Entire GO Tree
        sql = "select COUNT(Primary) as total from (select DISTINCT Primary from " + GOTable.UniProt_Go + ")";
        // Alternative query when using a database other than Access.
        // sql = "select COUNT(\"Primary\") as total from (select DISTINCT
        // \"Primary\" from " + GOTable.UniProt_Go + ") as primaries";
        ResultSet r = connection.prepareStatement(sql).executeQuery();
        r.next();
        String total = r.getString(1);
        godb.insert(connection, GOTable.UniProt_GoCount, new String[] { "GO", 0 + "", total });
    }

    /**
     * Recursively counts the number of time each GO ID maps to a unique Uniprot
     * ID as defined in the Uniprot-GO table
     *
     * @param parent
     *            parent GO ID
     * @param uniprot_IDs
     *            map of existing Uniprot IDs
     * @throws SQLException
     */
    private void getTotalCount(String parent, HashMap<String, Boolean> uniprot_IDs) throws SQLException {
        QueryHolder qh = getUniProtIDs("select Id from " + GOTable.GeneOntology + " where Parent = ?", parent);
        while (qh.rs.next()) {
            String id = qh.rs.getString(1);

            String mysql = "select \"Primary\" from " + GOTable.UniProt_Go + " where Related = ?";
            PreparedStatement myps = connection.prepareStatement(mysql);
            myps.setString(1, id);
            ResultSet upResults = myps.executeQuery();
            while (upResults.next()) {
                uniprot_IDs.put(upResults.getString(1), true);
            }
            myps.close();
            getTotalCount(id, uniprot_IDs);

        }
        qh.close();

    }

    /**
     * Populate genMAPP's Uniprot-GeneOntology table
     *
     * @throws IOException
     * @throws SQLException
     */
/*    private void populateUniprotGoTable(File goaFile) throws IOException, SQLException {
        _Log.debug("Processing GOA file: " + goaFile);
        populateUniprotGoTable(new BufferedReader(new FileReader(goaFile.getCanonicalPath())), godb, connection);
    }
*/

    /**
     * The actual GOA reader; null godb and connection arguments interpret this
     * as a test call, and produce debug statements instead.
     */
/*    protected static void populateUniprotGoTable(BufferedReader in, Go godb, Connection connection) throws IOException, SQLException {
        String line = null;
        HashMap<String, Boolean> unique = new HashMap<String, Boolean>();

        Pattern idPattern1 = Pattern.compile("UniProtKB/[\\w-]+:(\\w+)");
        Pattern idPattern1a = Pattern.compile("UniProtKB/[\\w-]+\\s+(\\w+)");
        Pattern idPattern2 = Pattern.compile("UniProt(KB)?\\s+(\\w{6})");
        Pattern goIDPattern = Pattern.compile("GO:(\\w+)");
        while ((line = in.readLine()) != null) {
            _Log.debug("Processing GOA line: " + line);

            // Grab the Uniprot ID --- the multiple patterns are needed to accommodate various known GOA file formats.
            Matcher m1 = idPattern1.matcher(line);
            Matcher m1a = idPattern1a.matcher(line);
            Matcher m2 = idPattern2.matcher(line);
            boolean regexp1 = m1.find();
            boolean regexp1a = m1a.find();
            boolean regexp2 = m2.find();
            if (regexp1 || regexp1a || regexp2) {
                String uniprotID = regexp1 ? m1.group(1) : (regexp1a ? m1a.group(1) : m2.group(2));
                // Grab the GO ID(s).
                Matcher match = goIDPattern.matcher(line);
                while (match.find()) {
                    String goID = match.group(1);
                    String key = uniprotID + "," + goID;
                    if (!unique.containsKey(key)) {
                        unique.put(key, true);
                        String[] values = new String[] { uniprotID, goID, "" };
                        if ((godb != null) && (connection != null)) {
                            godb.insert(connection, GOTable.UniProt_Go, values);
                        } else {
                            _Log.debug("UniProt-GO pair: " + uniprotID + ", " + goID);
                        }
                    }
                }
            }
        }

        // Potential query for use with new export from GOA in PostgreSQL database:
        // select with_or_from, go_id
        // from goa
        // where with_or_from like 'UniProtKB:%' or with_or_from like 'UniProt:%';
    }
*/

    /**
     * Populates GenMAPP's UniProt-GeneOnotlogy table, using a GOA table from
     * a PostgreSQL database instead of a GOA file
     *
     * @throws SQLException
     */

    private void populateUniprotGoTableFromSQL(char chosenAspect) throws SQLException {
    	HashMap<String, Boolean> unique = new HashMap<String, Boolean>();
    	String uniProtAndGOIDSQL = "select db_object_id, go_id, evidence_code, with_or_from from goa where db like '%UniProt%'";
    	PreparedStatement uniProtAndGOIDPS = null;
    	_Log.info("creating: " + GOTable.UniProt_Go);

    	if (chosenAspect != 'A') {
    		if (chosenAspect == 'C') {
    			uniProtAndGOIDSQL = uniProtAndGOIDSQL + " and aspect = 'C'";
    		} else if (chosenAspect == 'F') {
    			uniProtAndGOIDSQL = uniProtAndGOIDSQL + " and aspect = 'F'";
    		} else if (chosenAspect == 'P') {
    			uniProtAndGOIDSQL = uniProtAndGOIDSQL + " and aspect = 'P'";
    		}
    	}

    	try {
    		uniProtAndGOIDPS = ConnectionManager.getRelationalDBConnection().prepareStatement(uniProtAndGOIDSQL);
    		ResultSet uniProtAndGOIDRS = uniProtAndGOIDPS.executeQuery();
    		while (uniProtAndGOIDRS.next()) {

    			String uniProtID = uniProtAndGOIDRS.getString("db_object_id");
    			/*if (uniProtID.startsWith("UniProtKB:")) {
    				uniProtID = uniProtID.substring(10);
    			} else if (uniProtID.startsWith("UniProt:")) {
    				uniProtID = uniProtID.substring(8);
    			}*/
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
                    String[] values = new String[] { uniProtID, goID, "" };
                    if ((godb != null) && (connection != null)) {
                        godb.insert(connection, GOTable.UniProt_Go, values);
                    } else {
                        _Log.debug("UniProt-GO pair: " + uniProtID + ", " + goID);
                    }
    			}

    			if (evidenceCode == "IC") {
    				key = uniProtID + "," + withOrFrom;
        			if (!unique.containsKey(key)) {
        				unique.put(key, true);
                        String[] values = new String[] { uniProtID, withOrFrom, "" };
                        if ((godb != null) && (connection != null)) {
                            godb.insert(connection, GOTable.UniProt_Go, values);
                        } else {
                            _Log.debug("UniProt-GO pair: " + uniProtID + ", " + goID);
                        }
        			}
    			}

    		}
        } catch(SQLException sqlexc) {
            throw sqlexc;
        } catch(Exception exc) {
            _Log.error(exc);
        } finally {
        	try { uniProtAndGOIDPS.close(); } catch(Exception exc) { _Log.error(exc); }
        }
    }

    /**
     * Populates genMAPP's GeneOntology table based on a user selected species
     *
     * @throws SQLException
     */
    private void populateGeneOntology() throws SQLException {
//        String sql = "select * from " + GOTable.GeneOntologyStage + " where Id in (select DISTINCT(Related) from " + GOTable.UniProt_Go + ") order by Id";
        String relatedIDSQL = "select distinct(Related) from " + GOTable.UniProt_Go + " order by Related";
        String stageSQL = "select * from " + GOTable.GeneOntologyStage + " where Id = ? order by Id";
        PreparedStatement relatedPS = null;
        PreparedStatement stagePS = null;
        _Log.info("creating: " + GOTable.GeneOntology);
        try {
            // Grab the distinct related IDs from UniProt-GeneOntology.
            relatedPS = connection.prepareStatement(relatedIDSQL);
            stagePS = ConnectionManager.getRelationalDBConnection().prepareStatement(stageSQL);

            // For each of these IDs, grab the corresponding stage records, and process.
            ResultSet relatedRS = relatedPS.executeQuery();
            while (relatedRS.next()) {
                String relatedString = relatedRS.getString("Related");
                _Log.debug("Processing related term: " + relatedString);
                stagePS.setString(1, relatedString);
                processIDs(stagePS);
            }
        } catch(SQLException sqlexc) {
            throw sqlexc;
        } catch(Exception exc) {
            _Log.error(exc);
        } finally {
            try { stagePS.close(); } catch(Exception exc) { _Log.error(exc); }
            try { relatedPS.close(); } catch(Exception exc) { _Log.error(exc); }
        }
    }

    /**
     * Recursively inserts data based on a parent until reaching the root
     *
     * @param id
     *            parent ID
     * @throws SQLException
     */
    private void insertParents(String id) throws SQLException {
        String sql = "select * from " + GOTable.GeneOntologyStage + " where Id = ? order by Id";
        PreparedStatement ps = null;
        try {
            ps = ConnectionManager.getRelationalDBConnection().prepareStatement(sql);
            ps.setString(1, id);
            processIDs(ps);
//            ResultSet results = ps.executeQuery();
//            while (results.next()) {
//                String[] values = getGoValues(results);
//                String key = id + "," + values[PARENT_COL];
//                if (!duplicates.containsKey(key)) {
//                    duplicates.put(key, true);
//                    godb.insert(connection, GOTable.GeneOntology, values);
//                    insertParents(values[PARENT_COL]);
//                }
//            }
        } catch(SQLException sqlexc) {
            throw sqlexc;
        } catch(Exception exc) {
            _Log.error(exc);
        } finally {
            try { ps.close(); } catch(Exception exc) { _Log.error(exc); }
        }
    }

    private void processIDs(PreparedStatement stagePS) throws SQLException {
        ResultSet stageRS = null;
        try {
            stageRS = stagePS.executeQuery();
            while (stageRS.next()) {
                String[] values = getGoValues(stageRS);
                String key = values[ID_COL] + "," + values[PARENT_COL];
                if (!duplicates.containsKey(key)) {
                    duplicates.put(key, true);
                    godb.insert(connection, GOTable.GeneOntology, values);
                    insertParents(values[PARENT_COL]);
                }
            }
        } finally {
            stageRS.close();
        }
    }

    private String[] getGoValues(ResultSet results) throws SQLException {
        String[] values = new String[NUM_OF_GO_COLS];
        for (int x = 0; x < values.length; x++) {
            try {
                values[x] = results.getString(x + 1).trim();
            } catch(NullPointerException ex) {
            }
        }
        return values;
    }

    /**
     * Populate genMAPP's GeneOntologyTree
     *
     * @throws SQLException
     */
    private void populateGeneOntologyTree() throws SQLException {
        String[] root_ids = { "0003674", "0005575", "0008150" };
        String[] names = { "molecular_function", "cellular_component", "biological_process" };
        _Log.info("creating: " + GOTable.GeneOntologyTree);

        // Traverse the graph beginning with each root ID
        for (int index = 0; index < root_ids.length; index++) {
            String id = root_ids[index];
            String name = names[index];
            goCount.put(id, 1);
            godb.insert(connection, GOTable.GeneOntologyTree, new String[] { orderNo++ + "", 1 + "", id, name });
            insertChildren(id, 2);
        }
    }

    /**
     * Populate genMAPP's populateGeneOntologyCount
     *
     * @throws SQLException
     */
    private void populateGeneOntologyCount() throws SQLException {
        Iterator<String> iter = goCount.keySet().iterator();
        String id;
        int count;
        _Log.info("creating: " + GOTable.GeneOntologyCount);
        while (iter.hasNext()) {
            id = iter.next();
            count = goCount.get(id);
            _Log.debug("Inserting GeneOntology count record (" + id + ", " + count + ")");
            godb.insert(connection, GOTable.GeneOntologyCount, new String[] { id, count + "" });
        }
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
    private void insertChildren(String parent, int level) throws SQLException {
        String sqlStatement = "SELECT name,id from " + GOTable.GeneOntology + " where parent = ? order by parent";
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
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
            _Log.debug("Inserting child row (" + id + ", " + name + ")");
            godb.insert(connection, GOTable.GeneOntologyTree, new String[] { orderNo++ + "", level + "", id, name });
            insertChildren(id, level + 1);
        }
        ps.close();
    }

    private class QueryHolder {
        public PreparedStatement ps;
        public ResultSet rs;

        public void close() {
            try {
                rs.close();
            } catch(Exception exc) {
                exc.printStackTrace();
            }
            try {
                ps.close();
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    /**
     * Log object for ExportGoData.
     */
    private static final Log _Log = LogFactory.getLog(ExportGoData.class);

    // GO DB variables
    private static final int NUM_OF_GO_COLS = 8;
    private static final int PARENT_COL = 4 - 1;
    private static final int ID_COL 	= 1 - 1;

    @SuppressWarnings("unused")
    private char chosenAspect;
    private int orderNo;
    private Connection connection;
    private Go godb;
    private HashMap<String, String> namespace;
    private HashMap<String, Integer> goCount;
    private HashMap<String, Boolean> duplicates;
}
