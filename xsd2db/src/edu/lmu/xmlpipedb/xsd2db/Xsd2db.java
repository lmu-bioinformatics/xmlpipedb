/** Revision History
 *  ??/??/?? Scott. File orignaly created. 
 *  03/15/06 Adam Carasso.  Added xjc and hyperjaxb2 addin exectution 
 *  03/23/06 Adam Carasso.  Added sql database generation by executing 
 *							Hibernate schemaExport.
 *  03/23/06 Adam Carasso   Removed Hardcoding for uniprot and refactored
 *                          a couple functional parts such as movefiles 
 *                          and adding hibernate files to the hibernate
 *                          configure object.
 *  03/27/06 Adam Carasso   Added inschema schema option...
 * Note: Please follow commenting convetions already in this file!
 */
package edu.lmu.xmlpipedb.xsd2db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.lang.StringBuffer;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.jvnet.hyperjaxb2.addon.AddOn;
import org.jvnet.jaxbcommons.addon.AbstractParameterizableCodeAugmenter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.Driver;
import com.sun.tools.xjc.ErrorReceiver;
import com.sun.tools.xjc.GrammarLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.generator.GeneratorContext;
import com.sun.tools.xjc.grammar.AnnotatedGrammar;


public class Xsd2db {
    /**
     * additional command line arguments to be passed to the xjcCompiler.
     */
    private static final String XJC_ARGS[] = {
        // HyperJaxb needs xjc to be invoked in debug mode
        "-debug",
        // Enables extensions so we can use hyperjaxb2
        "-nv", "-extension",
        // equals extension is required for hyperjaxb2
        "-Xequals",
        // hashCode extension is requred for hb2
        "-XhashCode"
    };
    /**
     * JARs on which the final database project will depend.
     */
    private static final String[] LIB_DEP = {
        "jaxb-api.jar",
        "jaxb-impl.jar",
        "jaxb-libs.jar",
        "relaxngDatatype.jar"
    };
    /**
     * Map to store the absolute paths for all the sub directories.
     */
    private HashMap<String, String> pathMap;
    private static final String SUB_DIRS[] = {"xsd", "src", "hbm", "sql", "lib"};
    private static final int XSD_DIR = 0;
    private static final int SRC_DIR = 1;
    private static final int HBM_DIR = 2;
    private static final int SQL_DIR = 3;
    private static final int LIB_DIR = 4;
    
    
    private static int BUFFER_SIZE = 1024;

    public static final String HIB_PROPERTIES = "hibernate.properties";

    public static enum Schema {
        DTD, XSD
    }
    
    private static Xsd2db instance;
    
    /**
     * Options for the xjc compiler such as dest dir.
     */
    private Options xjcOptions;
    /**
     * Name of the schema file
     */
    private String xsdName;
    /**
     * hibernate configuration object
     */
    private Configuration hibernateConfig;

    /**
     * Constructor
     */
    private Xsd2db()
    {
        xjcOptions = new Options();
        pathMap = new HashMap<String, String>();
    }
    
    /**
     * Returns an instance of the singleton Xsd2db.
     */
    public static Xsd2db getInstance()
    {
        if (instance == null)
            instance = new Xsd2db();
        return instance;
    }
    
    
    /**
     * Initilization function.  Initlizes components used in the generation
     * of the JAXB objects and HBM files, such as log4j.
     */
    private void init() 
    {
        // Initialize log4j.
        BasicConfigurator.configure();
        // Initialize hibernate
        hibernateConfig = new Configuration();
        File hibPropertiesFile = new File(HIB_PROPERTIES);
        Properties hibProperties = new Properties();
        try {
            hibProperties.load(new FileInputStream(hibPropertiesFile));
        } catch(Exception e) {
            System.out.println("Properties file failed to load.");
        }
        hibernateConfig.setProperties(hibProperties);
    }
    
    
    /**
     * Sets the options for the xjc compiler.
     * @param xjcOptions xjc Options object to be set.
     */  
    private void setXjcOptions(File projectDirectory, Xsd2db.Schema schemaType)
    {
        xjcOptions.targetDir = new File(projectDirectory.getPath() + 
                                        File.separator + 
                                        SUB_DIRS[SRC_DIR]);
        if (schemaType == Xsd2db.Schema.DTD)
        //  Set the schema language to DTD
            xjcOptions.setSchemaLanguage(Options.SCHEMA_DTD);
        else
        //  Set the schema language to XSD
            xjcOptions.setSchemaLanguage(Options.SCHEMA_XMLSCHEMA);
        //  Sets the schema.
        xjcOptions.addGrammar(new org.xml.sax.InputSource(projectDirectory.getPath() + 
                                                          File.separator + 
                                                          SUB_DIRS[XSD_DIR] + 
                                                          File.separator + xsdName));
        //  Attempt to parse additional xjc arguments.
        try {
            xjcOptions.parseArguments(XJC_ARGS);
        } catch(Exception e) {
            System.out.println("XJC args are invalid");
        }
    }

                    
    
    
    /**
     * The execution entry point for the utility.  Creates a directory 
     * structure for xsd2db project, downloads the given schema and then
     * outputs JAXB objects and hibernate mapping files to persist the 
     * schema into a relational database.  Also outputs a build file
     * to make compiling the project into a jar easy.
     *
     * @param projectDir Directory to place the project in.
     * @param schemaURL  URL for schema file
     * @param schemaType Type of the given schema file (DTD or XSD).
     */
    public void run(File projectDir, String schemaURL, Xsd2db.Schema schemaType) {
        // Inits log4j
        getInstance().init();
        // Set up the directory structure of the project
        createDirectoryStructure(projectDir);
        // Fill in the path map.
        createAbsoulutePaths(projectDir);
        if (schemaURL == null || schemaURL.equals(""))
        {
            File schemaFiles[] = projectDir.listFiles(); 
            File schemaFile = schemaFiles[0];
            try {
                schemaURL = schemaFile.toURL().toString();
            } catch (MalformedURLException e)
            {
                System.out.println("URL for stored schema file could not be generated.  Please use the --xsdURL option.");
            }
        } 
        if (schemaURL != null || !schemaURL.equals(""))
        {
            try 
            {
                downLoadXsdFile(schemaURL);
            } catch (MalformedURLException e) 
            {
                System.out.println("Schema URL is not a valid URL");
            } catch (IOException e)   
            {
                System.out.println("Could not download schema file");
            }
        }

        setXjcOptions(projectDir, schemaType);
        
        
        ErrorReceiver errorReceiver = new ErrorReceiverImpl();
        AnnotatedGrammar grammar = null;
        try {
            grammar = GrammarLoader.load(xjcOptions, errorReceiver);
            if (grammar == null)
                System.out.println("Unable to parse schema");
        } catch(Exception e) {
            System.out.println("Error loading the grammar");
            e.printStackTrace(); 
        }

        try {
            GeneratorContext generatorContext = Driver.generateCode(grammar, xjcOptions, errorReceiver);
            if (generatorContext == null)
                System.out.println("failed to compile a schema");
            // Create the hyperJAXB addon object.
            AbstractParameterizableCodeAugmenter codeAugmenter = new AddOn();
            // Create a console error handler to send errors in the hyperJAXB addon to the console.
            ErrorHandler errorHandler = new ConsoleErrorReporter();
            // Must run the hyperJAXB addon before generating the the JAXB objects.
            codeAugmenter.run(grammar, generatorContext, xjcOptions, errorHandler);
            grammar.codeModel.build(Driver.createCodeWriter(xjcOptions.targetDir, xjcOptions.readOnly));
        } catch(Exception e) {
            System.out.println("Error: There was an error with the hyperjaxb addon or xjc comipler.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Move the hbm files to the hbm dir.
        File destDir = new File(projectDir, SUB_DIRS[HBM_DIR]);
        File srcDir = new File(projectDir, SUB_DIRS[SRC_DIR]);
        FilenameFilter hbmFilter = new HbmFilter();
        movefilesRecursive(destDir, srcDir, hbmFilter);
        
        // Use Hibernate to export a DDL for our database.
        exportDDL(projectDir, destDir);
 
        // Copy the canned build file and libs into
        // the project directory.
        copyLibAndBuildFiles(projectDir);

        System.out.println("Build Finished!");
    }

    /**
     * Exports a DDL file to the sql subdirectory of the projectDirectory.
     *
     * @param projectDir Directory of the generated project.
     * @param mappingDir Directory containing the hibernate mappings.
     */
    public void exportDDL(File projectDir, File mappingDir)
    {
        // Add hibernate mapping files to hibernate cfg object
        addHibFiles(hibernateConfig, mappingDir);
        // Produce the SQL file.
        SchemaExport schemaExporter = new SchemaExport(hibernateConfig);
        schemaExporter.setOutputFile(projectDir.getPath() + File.separator + SUB_DIRS[SQL_DIR] + File.separator + "schema.sql");
        schemaExporter.setDelimiter(";");
        schemaExporter.create(true, false);
    }
    
    /**
     * Recursively adds a directory of hbm files to an hibernate configuration 
     * object.
     *
     * @param cfg Hibernate Configuration object to add the hibernate files to.
     * @param hibDir Directory containing the hbm files.
     */
    public static void addHibFiles(Configuration cfg, File hibDir) {
        HbmFilter hbmFilter = new HbmFilter();
        File fileList[] = hibDir.listFiles(hbmFilter);
        for (int i = 0; i < fileList.length; i++) {
            if (!fileList[i].isDirectory())
                cfg.addFile(fileList[i]);
        }
    }

    /**
     * Recursively moves a directory of files from srcDir to destDir if they match 
     * the FileNameFilter.
     *
     * @param destDir Directory to move files to
     * @param srcDir  Directory to move files from
     * @param filter  Filename filter that must be matched
     *                in order for files to be moved
     */
    public static void movefilesRecursive(File destDir, File srcDir, FilenameFilter filter) {
        File filesToMove[] = srcDir.listFiles(filter);
        for (int i = 0; i < filesToMove.length; i++) {
            if (filesToMove[i].isDirectory())
                movefilesRecursive(destDir, filesToMove[i], filter);
            else {
                File newFile = new File(destDir, filesToMove[i].getName());
                filesToMove[i].renameTo(newFile);
            }
        }
    }

    public void copyLibAndBuildFiles(File projectDir)
    {
                // Copy needed library files.
        Class xsd2dbClass = this.getClass();
        File libDir = new File(projectDir, SUB_DIRS[LIB_DIR]);
        libDir.mkdir();
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            for (String libName: LIB_DEP) {
                BufferedInputStream bis = new BufferedInputStream(xsd2dbClass.getResourceAsStream("/" + libName));
                File libFile = new File(libDir, libName);
                libFile.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(libFile));
                int bytesRead = bis.read(buffer);
                while (bytesRead > -1) {
                    bos.write(buffer, 0, bytesRead);
                    bytesRead = bis.read(buffer);
                }
                bos.close();
                bis.close();
            }
        } catch(IOException ioexc) {
            System.out.println("Error writing library file: " + ioexc.getMessage());
        }

        // Copy Build file into project
        InputStreamReader buildFileReader;
        InputStream buildFileStream = xsd2dbClass.getResourceAsStream("build.xml");
        buildFileReader = new InputStreamReader(buildFileStream);
        try {
            System.out.println("found build file");
            File cannedBuildfile = new File(projectDir, "build.xml");
            cannedBuildfile.createNewFile();
            FileWriter buildFileWriter = new FileWriter(cannedBuildfile);
            int streamedChar = 0;
            while (((streamedChar = buildFileReader.read()) != -1))
                    buildFileWriter.write(streamedChar);
            buildFileWriter.close();
        } catch(IOException ioException) {
            System.out.println("Error writing canned build file: " + ioException.getMessage());
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
        byte[] buf = new byte[BUFFER_SIZE];
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
     * Downloads the user requested .xsd file to projectDir/xsd
     *
     * @throws IOException
     *             if an I/O error occurs
     */
    private void downLoadXsdFile(String xsdUrl) throws IOException {
        xsdName = xsdUrl.substring(xsdUrl.lastIndexOf("/"));
        File xsdfile = new File(getAbsolutePath("xsd") + xsdName);
        URL url = null;
        System.out.println("\nDownloading " + xsdfile.getName() + "...");
        try {
            url = new URL(xsdUrl);
        } catch(MalformedURLException e) {
            System.out.println("Error: Xsd url is not valid");
            e.printStackTrace();
        }

        BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(xsdfile));

        byte[] buffer = new byte[BUFFER_SIZE];
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
     * Creates the database source output directory structure,
     * per user request. Defaults to db-gen
     *
     */
    private void createDirectoryStructure(File projectDir) {
        projectDir.mkdir();

        for (String dir : SUB_DIRS) {
            File newDirectory = new File(projectDir.toString() + 
                                         File.separator +
                                         dir);
                if(!newDirectory.exists())
                    newDirectory.mkdir();
        }
    }

    /**
     * Creates absoulte paths for the database source output directory and any
     * children directories
     *
     */
    private void createAbsoulutePaths(File projectDir) {
        pathMap.put("projectDir", projectDir.getAbsolutePath());

        for (String dir : SUB_DIRS) {
            pathMap.put(dir, new File(projectDir.toString() + 
                                      File.separator + 
                                      dir).getAbsolutePath());
        }
    }

    /**
     * Returns the absolute path for the database source output directory
     * and any child directories
     *
     * @param dir
     *            directory name
     * @return absoulte path of the requested directory
     */
    public String getAbsolutePath(String dir) {
        return pathMap.get(dir);
    }
    

    /**
     * {@link ErrorReceiver} that produces messages
     * as Ant messages.
     */
    private static class ErrorReceiverImpl extends ErrorReceiver {
        public void warning(SAXParseException e) {
            System.out.println("Warning: " + e);
        }

        public void error(SAXParseException e) {
            System.out.println("Error: " + e);
        }

        public void fatalError(SAXParseException e) {
            System.out.println("Fatal Error: " + e);
        }

        public void info(SAXParseException e) {
            System.out.println("info: " + e);
        }
    }


    private static class HbmFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            if (name.endsWith("hbm.xml") || (new File(dir + "/" + name)).isDirectory())
                return true;
            return false;
        }
    }
}
