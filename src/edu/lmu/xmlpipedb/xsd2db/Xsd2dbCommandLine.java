/** Revision History
 *  ??/??/?? Scott. File orignaly created.
 *  03/15/06 Adam Carasso.  Changed private option varibles to protected.
 *  03/28/06 Scott.  Changed DTD constant to public enum.
 */

package edu.lmu.xmlpipedb.xsd2db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.net.URL;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class Xsd2dbCommandLine {

    /*
     * Private varibles used to store option names
     */
    private static final String BINDINGS = "bindings";
    private static final String OUTPUT_DIRECTORY = "outputDirectory";
    private static final String UPDATE_XSD = "updateXSD";
    private static final String XSD_URL = "xsdURL";
    private static final String DTD_SCHEMA = "dtdSchema";
    private static final String HELP = "help";

    /**
     * Usage message
     */
    private static final String USAGE = "usage: xsd2db [--" + OUTPUT_DIRECTORY + "=dirname] " + "[--" + BINDINGS + 
                                        "=filename] [[-" + UPDATE_XSD + "] --" + XSD_URL + "=url] [-" + DTD_SCHEMA + "] [-" + HELP + "]";
    /**
     * Help message
     */
    private static final String HELP_MESSAGE = "--" + OUTPUT_DIRECTORY + "=dirname -- " + 
                                                "The directory when generating the source code and file; defaults to db-gen\n" + 
                                                "--" + BINDINGS + "=filename       -- " + 
                                                "The binding file used when generation the database source code and files\n" + 
                                                "                             for the time.  Defaults to standard binding file supplied by XSD-To-DB\n" + 
                                                "--" + XSD_URL + "=url              -- " + "The URL of the XSD to convert\n" + "-" + 
                                                UPDATE_XSD + "                -- " + 
                                                "Replaces the XSD being used with the new version\n" + "-" + DTD_SCHEMA + 
                                                "                -- Sets schema type to DTD; defaults to XSD\n" + "-" + HELP + 
                                                "                     -- Displays this help and exits\n";
    private static final int BUFFER_SIZE = 1024;

    /**
     * Singleton instance varible.
     */
    private static Xsd2dbCommandLine instance;

    /*
     * Place to store the command line argumetns that 
     * xsd2db was invoked with.
     */ 
    private Options options;
    private File dbSrcDir;
    private String bindingsFile;
    private String xsdurl;
    private Xsd2db.Schema schemaType;

    /**
     * Constructor.  Used to instainted the singleton.
     */
    private Xsd2dbCommandLine() {
        options = new Options();
        addXsd2dbOptions();
    }

    /**
     * Returns the singleton instance of Xsd2dbCommandLine.
     */
    public static Xsd2dbCommandLine getInstance()
    {
        if (instance == null)
            instance = new Xsd2dbCommandLine();
        return instance;
    }
    
    
    public File getDBSrcDir()
    {
        return dbSrcDir;
    }
    public String getBindingsFile()
    { 
        return bindingsFile;
    }
    public String getXSDURL()
    {
        return xsdurl;
    }
    public Xsd2db.Schema getSchemaType()
    {
        return schemaType;
    }

    /**
     * Parse the command line
     *
     * @param args
     *            command line arguments
     */
    public void parse(String[] args) {
        CommandLineParser parser = new PosixParser();
        CommandLine line = null;

        // parse command line options
        try {
            line = parser.parse(options, args);
        } catch(ParseException e) {
            printErrorMsgAndExit(e.getMessage() + "\n\n" + USAGE);
        }

        if (line.hasOption(HELP)) {
            printErrorMsgAndExit(USAGE + "\n\n" + HELP_MESSAGE);
        }

        // Read the parsed options
        dbSrcDir = new File(line.hasOption(OUTPUT_DIRECTORY) ? line.getOptionValue(OUTPUT_DIRECTORY) : "db-gen");

        bindingsFile = line.hasOption(BINDINGS) ? line.getOptionValue(BINDINGS) : "";

        xsdurl = line.hasOption(XSD_URL) ? line.getOptionValue(XSD_URL) : "";

        schemaType = line.hasOption(DTD_SCHEMA) ? Xsd2db.Schema.DTD : Xsd2db.Schema.XSD;

        if (line.hasOption(UPDATE_XSD) && xsdurl.equals("")) {
            printErrorMsgAndExit(USAGE + "\n\n--" + XSD_URL + "=url must be specified when using -" + UPDATE_XSD);

        }
    }
    
    /**
     * Entry point into the command line executable.
     */
    public static void main(String args[])
    {
        Xsd2dbCommandLine.getInstance().run(args);
    }       
    
    
    public void run(String args[])
    {
        parse(args);
        if (!dbSrcDir.isDirectory() || (dbSrcDir.list().length == 0)) {
            if (xsdurl.equals("")) {
                printErrorMsgAndExit("\nFirst time run with output directory '" + dbSrcDir.toString() + "'. Therefore --" + XSD_URL + "=url must be specifed");
            } 
        }
        Xsd2db.getInstance().run(dbSrcDir, xsdurl, schemaType);
    }

 

    
    /**
     * Adds the acceptable command line options
     */
    @SuppressWarnings("static-access")
    private void addXsd2dbOptions() {
        options.addOption(HELP, false, null);
        options.addOption(UPDATE_XSD, false, null);
        options.addOption(DTD_SCHEMA, false, null);
        options.addOption(OptionBuilder.withLongOpt(OUTPUT_DIRECTORY).withValueSeparator('=').hasArg().create());
        options.addOption(OptionBuilder.withLongOpt(BINDINGS).withValueSeparator('=').hasArg().create());
        options.addOption(OptionBuilder.withLongOpt(XSD_URL).withValueSeparator('=').hasArg().create());
    }

    /**
     * Prints error message and then exists the program
     * @param s 	the message to print
     */
    private void printErrorMsgAndExit(String s) {
        System.out.print(s);
        System.exit(0);
    }

}
