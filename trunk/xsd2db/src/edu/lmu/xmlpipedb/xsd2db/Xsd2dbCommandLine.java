/** Revision History
 *  ??/??/?? Scott. File orignaly created.
 *  03/15/06 Adam Carasso.  Changed private option varibles to protected.
 */

package edu.lmu.xmlpipedb.xsd2db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class Xsd2dbCommandLine {
    private final String bindings = "bindings";
    private final String outputDirectory = "outputDirectory";
    private final String updateXSD = "updateXSD";
    private final String xsdURL = "xsdURL";
    private final String dtdSchema = "dtdSchema";
    private final String help = "help";

    private final String usage = "usage: program_name [--" + outputDirectory + "=dirname] " + "[--" + bindings + "=filename] [[-" + updateXSD + "] --" + xsdURL + "=url] [-" + dtdSchema + "] [-" + help + "]";
    private final String helpMsg = "--" + outputDirectory + "=dirname -- " + "The directory when generating the source code and file; defaults to db-gen\n" + "--" + bindings + "=filename       -- "  + "The binding file used when generation the database source code and files\n"+ "                             for the time.  Defaults to standard binding file supplied by XSD-To-DB\n" + "--" + xsdURL + "=url              -- " + "The URL of the XSD to convert\n" + "-" + updateXSD  + "                -- "  + "Replaces the XSD being used with the new version\n" + "-" + dtdSchema + "                -- Sets schema type to DTD; defaults to XSD\n" + "-" + help + "                     -- Displays this help and exits\n";
    protected static final int XSD_DIR = 0;
    protected static final int SRC_DIR = 1;
    protected static final int HBM_DIR = 2;
    protected static final int SQL_DIR = 3;
    protected String[] subDirs = { "xsd", "src", "hbm", "sql" };

    protected Options options;
    protected File dbSrcDir;
    protected String bindingsFile;
    protected String xsdurl;
    protected String xsdName;
    protected HashMap<String, String> map;

    public enum Schema { DTD, XSD }

    private Schema schemaType;

    /**
     * Constructor
     *
     */
    public Xsd2dbCommandLine() {
        options = new Options();
        map = new HashMap<String, String>();
        addXsd2dbOptions();
    }

    /**
     * Returns the absolute path for the the database source output directory
     * and any child directories
     *
     * @param dir
     *            directory name
     * @return absoulte path of the requested directory
     */
    public String getAbsolutePath(String dir) {
        return map.get(dir);
    }

    /**
     * Returns the type of schema file to process.
     * 
     * @return DTD or XSD
     */
    public Schema getSchemaType() {
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

        try {
            line = parser.parse(options, args);
        } catch(ParseException e) {
            printErrorMsgAndExit(e.getMessage() + "\n\n" + usage);
        }

        if (line.hasOption(help)) {
            printErrorMsgAndExit(usage + "\n\n" + helpMsg);

        }

        dbSrcDir = new File(line.hasOption(outputDirectory) ? line.getOptionValue(outputDirectory) : "db-gen");

        bindingsFile = line.hasOption(bindings) ? line.getOptionValue(bindings) : "";

        xsdurl = line.hasOption(xsdURL) ? line.getOptionValue(xsdURL) : "";
        
        schemaType = line.hasOption(dtdSchema) ? Schema.DTD : Schema.XSD;
        
        if (line.hasOption(updateXSD) && xsdurl.equals("")) {
            printErrorMsgAndExit(usage + "\n\n--" + xsdURL + "=url must be specified when using -" + updateXSD);

        }

        if (!dbSrcDir.isDirectory() || (dbSrcDir.list().length == 0)) {
            if (xsdurl.equals("")) {
                printErrorMsgAndExit("\nFirst time run with output directory '" + dbSrcDir.toString() + "'. Therefore --" + xsdURL + "=url must be specifed");
            } else {
                createDirectoryStructure();
            }
        }
        createAbsoulutePaths();

        if (!xsdurl.equals("")) {
            xsdName = xsdurl.substring(xsdurl.lastIndexOf("/"));
            try {
                downLoadXsdFile();
            } catch(IOException e) {
                printErrorMsgAndExit(e.getMessage() + "\n\nError downloading xsd file");
            }
        }

        if (!bindingsFile.equals("")) {
            File in = new File(bindingsFile);
            File out = new File(getAbsolutePath("xsd") + File.separator + in.getName());
            if (in.canRead()) {
                try {
                    copyBindingsFile(in, out);
                } catch(Exception e) {
                    printErrorMsgAndExit(e.getMessage() + "\n\nError copying bindings file");
                }
            } else {
                printErrorMsgAndExit("Unable to read bindings file: '" + in.getPath() + "'");
            }
        } else {

        }
    }

    /**
     * Creates absoulte paths for the database source output directory and any
     * children directories
     *
     */
    private void createAbsoulutePaths() {
        map.put("dbsrc", dbSrcDir.getAbsolutePath());

        for (String dir : subDirs) {
            map.put(dir, new File(dbSrcDir.toString() + File.separator + dir).getAbsolutePath());
        }
    }

    /**
     * Copies a binding file to dbsrcdir/xsd
     *
     * @param in
     *            input file
     * @param out
     *            output file
     * @throws Exception
     *             I/O error or File not found error
     */
    private void copyBindingsFile(File in, File out) throws Exception {
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        byte[] buf = new byte[1024];
        int i = 0;

        System.out.println("\nCopying binding file " + in.getName() + " ...");

        while ((i = fis.read(buf)) != -1) {
            fos.write(buf, 0, i);
        }
        fis.close();
        fos.close();

        System.out.println("Copy successful... ");
    }

    /**
     * Downloads the user requested .xsd file to dbsrcdir/xsd
     *
     * @throws IOException
     *             if an I/O error occurs
     */
    private void downLoadXsdFile() throws IOException {
        File xsdfile = new File(getAbsolutePath("xsd") + xsdurl.substring(xsdurl.lastIndexOf("/")));
        URL url = null;

        System.out.println("\nDownloading " + xsdfile.getName() + " ...");

        try {
            url = new URL(xsdurl);
        } catch(MalformedURLException e) {
            printErrorMsgAndExit(e.getMessage());
        }

        BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(xsdfile));

        byte[] buffer = new byte[128];
        int bytesRead;
        while (true) {
            bytesRead = inputStream.read(buffer);
            if (bytesRead == -1)
                break;
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        outputStream.close();

        System.out.println("Download successful...");
    }

    /**
     * Adds the acceptable command line options
     *
     */
    private void addXsd2dbOptions() {
        options.addOption(help, false, null);
        options.addOption(updateXSD, false, null);
        options.addOption(dtdSchema, false, null);
        options.addOption(OptionBuilder.withLongOpt(outputDirectory).withValueSeparator('=').hasArg().create());

        options.addOption(OptionBuilder.withLongOpt(bindings).withValueSeparator('=').hasArg().create());

        options.addOption(OptionBuilder.withLongOpt(xsdURL).withValueSeparator('=').hasArg().create());
    }

    /**
     * Prints error message and then exists the program
     * @param s 	the message to print
     */
    private void printErrorMsgAndExit(String s) {
        System.out.print(s);
        System.exit(0);
    }

    /**
     * Creates the database source output directory structure,
     * per user request. Defaults to db-gen
     *
     */
    private void createDirectoryStructure() {
        dbSrcDir.mkdir();

        for (String dir : subDirs) {
            new File(dbSrcDir.toString() + File.separator + dir).mkdir();
        }
    }
}
