/********************************************************
 * Filename: ImportGoData.java
 * Author: LMU
 * Adaptation: Don Murphy
 * Program: gmBuilder
 * Description: Export the data to the access database.
 * Revision History:
 * 20100402: Initial Revision, based on ExportGoData.java.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class ImportGoData {
	/**
     * Constructor
     *
     * @throws IOException
     *             I/O error
     */
    public ImportGoData(Connection connection) throws IOException {
        orderNo = 1;
        this.connection = connection;
        godb = new Go();
        namespace = new HashMap<String, String>();
        goCount = new HashMap<String, Integer>();
        duplicates = new HashMap<String, Boolean>();
//        createNamespaceMappings();
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
    public void GOAimport(File goaFile) throws ClassNotFoundException, SQLException, HibernateException, SAXException, IOException, JAXBException {
        String Date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

        // TODO: Change from exporting GOA to GenMAPP, to importing GOA to PostgreSQL database

//      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
//        ExportWizard.updateExportProgress(3, "GeneOntology export - creating tables...");
//        godb.createTables(connection);
//      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
//        ExportWizard.updateExportProgress(10, "GeneOntology export - populating tables...");
//        populateGoTables(goaFile);
//      FIXME: This must be done non-statically with a check to see if the object is null OR not done here at all.
//        ExportWizard.updateExportProgress(40, "GeneOntology export - flushing tables...");
//        godb.updateSystemsTable(connection, Date, "T");
        _Log.info("done!");
    }
    /**
     * Log object for ImportGoData.
     */
    private static final Log _Log = LogFactory.getLog(ImportGoData.class);

    // GO DB variables
    private static final int NUM_OF_GO_COLS = 8;
    private static final int PARENT_COL = 4 - 1;
    private static final int ID_COL 	= 1 - 1;

    private int orderNo;
    private Connection connection;
    private Go godb;
    private HashMap<String, String> namespace;
    private HashMap<String, Integer> goCount;
    private HashMap<String, Boolean> duplicates;
}