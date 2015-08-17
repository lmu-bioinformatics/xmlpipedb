/********************************************************
 * Filename: ConnectionManager.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Manages all connection required by
 * GenMAPP builder.
 *
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/
package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Joey J. Barrett
 */
public class ConnectionManager {
    // FIXME Fix this to use getResource() rather than hardcoded
    private static final String GENMAPP_DATABASE_TEMPLATE =
            "/edu/lmu/xmlpipedb/gmbuilder/resource/dbfiles/GeneDBTmpl.mdb";

    private static final Log LOG = LogFactory.getLog(ConnectionManager.class);

    private static final File TEMPORARY_GENMAPP_DATABASE_TEMPLATE = new File(System.getProperty("user.dir") +
            "/GeneMAPPBuilder.db");

    private static Connection relationalDBConnection = null;
    private static Connection genMAPPDBConnection = null;
    private static Connection genMAPPTemplateDBConnection = null;

    static {
        URL u = null;
        try {
            u = ConnectionManager.class.getResource(GENMAPP_DATABASE_TEMPLATE);
            new URI(u.getFile());
        } catch (URISyntaxException e) {
            LOG.error("Error while creating URL from path " + GENMAPP_DATABASE_TEMPLATE, e);
            LOG.error("Error while creating URI from URL. URL = " + u);
        }
    }

    /**
     * Opens a connection to the relational database. If the parameter is null
     * an exception is thrown. If the connection is still open, an exception is
     * thrown.
     *
     * @param hibernateConfiguration
     * @throws Exception
     */
    public static void openRelationalDB(Configuration hibernateConfiguration) throws Exception {
        // Open a connection to the relational database, requires a hibernate
        // configuration.
        if (hibernateConfiguration != null) {
            if (relationalDBConnection != null) {
                throw new Exception("A relational database connection cannot be created " +
                        "while a previous connection is still open.");
            }
            openRelationalDatabaseConnection(hibernateConfiguration);
        } else {
            throw new Exception("A relational database connection is not specified.");
        }
    }

    /**
     * Opens a connection to the GenMAPP Access database. If the parameter is
     * null an exception is thrown. If the connection is still open, an
     * exception is thrown.
     *
     * @param genMAPPDatabase
     * @throws Exception
     */
    public static void openGenMAPPDB(String genMAPPDatabase) {
        if (genMAPPDatabase != null) {
            if (genMAPPDBConnection == null) {
                try {
                    copyFile(GENMAPP_DATABASE_TEMPLATE, new File(genMAPPDatabase));
                    genMAPPDBConnection = openAccessDatabaseConnection(genMAPPDatabase);
                } catch (IOException e) {
                    LOG.error("Unable to copy file", e);
                } catch (ClassNotFoundException e) {
                    LOG.error("Unable to instantiate Access connection", e);
                } catch (SQLException e) {
                    LOG.error("Unable to open Access connection", e);
                }
            } else {
                LOG.error("A GenMAPP database connection cannot be created " +
                        "while a previous connection is still open.");
            }
        } else {
            LOG.error("A GenMAPP database connection is not specified.");
        }
    }

    /**
     * Opens a connection to the GenMAPP relational database. If the parameter
     * is null an exception is thrown. If the connection is still open, an
     * exception is thrown.
     *
     * @param connectionConfiguration
     * @throws Exception
     */
    public static void openGenMAPPDB(ConnectionConfiguration connectionConfiguration) {
        // Open a connection to the GenMAPP database, requires a connection
        // configuration.
        if (connectionConfiguration != null) {
            if (genMAPPDBConnection == null) {
                try {
                    DriverManager.registerDriver(connectionConfiguration.getDriver());
                    genMAPPDBConnection = DriverManager.getConnection(connectionConfiguration.getConnectionURL(),
                            connectionConfiguration.getUserName(), connectionConfiguration.getPassword());
                } catch (SQLException e) {
                    LOG.error("Unable to open connection to " + connectionConfiguration.getConnectionURL(), e);
                }
            } else {
                LOG.error("A GenMAPP database connection cannot be created " +
                        "while a previous connection is still open.");
            }
        } else {
            LOG.error("A GenMAPP database connection is not specified.");
        }
    }

    /**
     * Opens a connection to the GenMAPP template database by copying the
     * template file to a temporary location. If the connection is still open,
     * an exception is thrown. NOTE: THIS CONNECTION SHOULD BE USED AS READ
     * ONLY!!!
     * 
     * @throws Exception
     * @throws Exception
     */
    public static void openGenMAPPTemplateDB() throws Exception {
        // Open a connection to the GenMAPP template database.

        if (genMAPPTemplateDBConnection == null) {
            // Copy the template file to a temporary file.
            copyFile(GENMAPP_DATABASE_TEMPLATE, TEMPORARY_GENMAPP_DATABASE_TEMPLATE);
            /*
             * JN - 7/15/2006 -- for some reason, there is a leading slash on
             * the path returned by uri.getPath, hence the .substring(1), which
             * will return the string starting at position 1, not position 0
             * (the slash). If you get a File object from this path, the File
             * object knows how to deal and is OK. The Connection object,
             * however, is not so cool and cannot deal.
             */
            genMAPPTemplateDBConnection = openAccessDatabaseConnection(
                    TEMPORARY_GENMAPP_DATABASE_TEMPLATE.getAbsolutePath());
        } else {
            throw new Exception("A GenMAPP template database connection cannot be created " +
                    "while a previous connection is still open.");
        }
    }

    public static Connection getGenMAPPDBConnection() {
        if (genMAPPDBConnection == null) {
            LOG.error("A GenMAPP database connection is not open.");
        }
        return genMAPPDBConnection;
    }

    public static Connection getRelationalDBConnection() {
        if (relationalDBConnection == null) {
            LOG.error("A relational database connection is not open.");
        }

        return relationalDBConnection;
    }

    /**
     * NOTE: THIS CONNECTION SHOULD BE USED AS READ ONLY!!!
     */
    public static Connection getGenMAPPTemplateDBConnection() throws Exception {
        if (genMAPPTemplateDBConnection == null) {
            throw new Exception("A GenMAPP template database connection is not open.");
        }

        return genMAPPTemplateDBConnection;
    }

    public static boolean isGenMAPPDBConnectionOpen() {
        return genMAPPDBConnection != null;
    }

    public static boolean isRelationalDBConnectionOpen() {
        return relationalDBConnection != null;
    }

    public static boolean isGenMAPPTemplateDBConnectionOpen() {
        return genMAPPTemplateDBConnection != null;
    }

    /**
     * Close and set null the open GenMAPP database and connection. If the
     * connection is null nothing happens.
     *
     * @throws SQLException
     */
    public static void closeGenMAPPDB() throws SQLException {
        if (genMAPPDBConnection != null) {
            genMAPPDBConnection.close();
            genMAPPDBConnection = null;
        }
    }

    /**
     * Close and set null the open relational database connection. If the
     * connection is null nothing happens.
     *
     * @throws SQLException
     */
    public static void closeRelationalDB() throws SQLException {
        if (relationalDBConnection != null) {
            relationalDBConnection.close();
            relationalDBConnection = null;
        }
    }

    /**
     * Close and set null the open GenMAPP template database connection. If the
     * connection is null nothing happens.
     *
     * @throws SQLException
     */
    public static void closeGenMAPPTemplateDB() throws SQLException {
        if (genMAPPTemplateDBConnection != null) {
            genMAPPTemplateDBConnection.close();
            TEMPORARY_GENMAPP_DATABASE_TEMPLATE.delete();
            genMAPPTemplateDBConnection = null;
        }
    }

    /**
     * Close and set null all open connections. If a connection is null nothing
     * happens.
     *
     * @throws SQLException
     */
    public static void closeAll() throws SQLException {
        closeRelationalDB();
        closeGenMAPPDB();
        closeGenMAPPTemplateDB();
    }

    /**
     * Open a relational database connection.
     *
     * @param hibernateConfiguration
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void openRelationalDatabaseConnection(Configuration hibernateConfiguration) throws SQLException,
            ClassNotFoundException {
        // TODO Fix this to update it to the new xpdutils stuff.
        Class.forName("org.postgresql.Driver");
        relationalDBConnection = DriverManager.getConnection(
            hibernateConfiguration.getProperty("hibernate.connection.url"),
            hibernateConfiguration.getProperty("hibernate.connection.username"),
            hibernateConfiguration.getProperty("hibernate.connection.password")
        );
    }

    private static Connection openAccessDatabaseConnection(String databaseFile) throws ClassNotFoundException,
            SQLException {
        StringBuffer databaseConnectionString = new StringBuffer("jdbc:ucanaccess://");
        databaseConnectionString.append(databaseFile);

        LOG.info("Using database connection string: " + databaseConnectionString.toString());
        return DriverManager.getConnection(databaseConnectionString.toString(), "", "");
    }

    private static void copyFile(String originalFilePath, File newFile) throws IOException {
        InputStream in = ConnectionManager.class.getResourceAsStream(originalFilePath);
        OutputStream out = new FileOutputStream(newFile);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

}
