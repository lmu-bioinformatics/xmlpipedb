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
    public static final String xjcArgs[] = {
        // HyperJaxb invokes xjc in debug mode for some reason
        "-debug",
        // Enables extensions so we can use hyperjaxb2
        "-nv", "-extension",
        // equals extension is required for hyperjaxb2
        "-Xequals",
        // hashCode extension is requred for hb2
        "-XhashCode"
    };

    public static final String HIB_PROPERTIES = "hibernate.properties";

    /**
     * The execution entry point for the utility.
     *
     * @param args
     */
    public static void main(String[] args) {
        // Initialize log4j.
        BasicConfigurator.configure();

        Xsd2dbCommandLine cmdline = new Xsd2dbCommandLine();
        cmdline.parse(args);
        Options options = new Options();
        options.targetDir = new File(cmdline.dbSrcDir.getPath() + File.separator + cmdline.subDirs[Xsd2dbCommandLine.SRC_DIR]);
        //  TODO:  need to add the bindings file
        
        if (cmdline.getSchemaType() == Xsd2dbCommandLine.Schema.DTD)
        //  Set the schema language to DTD
            options.setSchemaLanguage(Options.SCHEMA_DTD);
        else
        //  Set the schema language to XSD
            options.setSchemaLanguage(Options.SCHEMA_XMLSCHEMA);
        
        System.out.println(cmdline.dbSrcDir.getPath());
        //  Sets the schema.
        options.addGrammar(new org.xml.sax.InputSource(cmdline.dbSrcDir.getPath() + File.separator + cmdline.subDirs[Xsd2dbCommandLine.XSD_DIR] + File.separator + cmdline.xsdName));
        try {
            options.parseArguments(xjcArgs);
        } catch(Exception e) {
            System.out.println("XJC args are invalid");
        }

        ErrorReceiver errorReceiver = new ErrorReceiverImpl();
        AnnotatedGrammar grammar = null;
        try {
            grammar = GrammarLoader.load(options, errorReceiver);
            if (grammar == null)
                System.out.println("Unable to parse schema");
        } catch(Exception e) {
        
        
        }

        try {
            GeneratorContext generatorContext = Driver.generateCode(grammar, options, errorReceiver);
            if (generatorContext == null)
                System.out.println("failed to compile a schema");
            // Calling the hyperjaxb2 addon manualy
            AbstractParameterizableCodeAugmenter codeAugmenter = new AddOn();
            ErrorHandler errorHandler = new ConsoleErrorReporter();
            // Must run before building the code model.
            codeAugmenter.run(grammar, generatorContext, options, errorHandler);
            grammar.codeModel.build(Driver.createCodeWriter(options.targetDir, options.readOnly));
        } catch(Exception e) {
            System.out.println("Error: The hyperjaxb addon failed to generate hibernate mappings.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        File destDir = new File(cmdline.dbSrcDir, cmdline.subDirs[Xsd2dbCommandLine.HBM_DIR]);
        File srcDir = new File(cmdline.dbSrcDir, cmdline.subDirs[Xsd2dbCommandLine.SRC_DIR]);
        FilenameFilter hbmFilter = new HbmFilter();
        movefilesRecursive(destDir, srcDir, hbmFilter);
        Configuration cfg = new Configuration();
        File hibPropertiesFile = new File(HIB_PROPERTIES);
        Properties hibProperties = new Properties();
        try {
            hibProperties.load(new FileInputStream(hibPropertiesFile));
        } catch(Exception e) {
            System.out.println("Properties file failed to load.");
        }
        cfg.setProperties(hibProperties);
        addHibFiles(cfg, destDir);

        // Produce the SQL file.
        SchemaExport schemaExporter = new SchemaExport(cfg);
        schemaExporter.setOutputFile(cmdline.dbSrcDir.getPath() + "/" + cmdline.subDirs[Xsd2dbCommandLine.SQL_DIR] + "/" + "schema.sql");
        schemaExporter.setDelimiter(";");
        schemaExporter.create(true, false);

        // Copy needed library files.
        Class cmdlineClass = cmdline.getClass();
        File libDir = new File(cmdline.dbSrcDir, cmdline.subDirs[Xsd2dbCommandLine.LIB_DIR]);
        libDir.mkdir();
        try {
            byte[] buffer = new byte[1024];
            for (String libName: LIB_DEP) {
                BufferedInputStream bis = new BufferedInputStream(cmdlineClass.getResourceAsStream("/" + libName));
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

        InputStreamReader buildFileReader;
        InputStream buildFileStream = cmdlineClass.getResourceAsStream("build.xml");
        buildFileReader = new InputStreamReader(buildFileStream);
        try {
            System.out.println("found build file");
            File cannedBuildfile = new File(cmdline.dbSrcDir, "build.xml");
            cannedBuildfile.createNewFile();
            FileWriter buildFileWriter = new FileWriter(cannedBuildfile);
            int streamedChar = 0;
            while (((streamedChar = buildFileReader.read()) != -1))
                buildFileWriter.write(streamedChar);
            buildFileWriter.close();
        } catch(IOException ioException) {
            System.out.println("Error writing canned build file: " + ioException.getMessage());
        }

        System.out.println("Build Finished!");
    }

    private static void addHibFiles(Configuration cfg, File hibDir) {
        HbmFilter hbmFilter = new HbmFilter();
        File fileList[] = hibDir.listFiles(hbmFilter);
        for (int i = 0; i < fileList.length; i++) {
            if (!fileList[i].isDirectory())
                cfg.addFile(fileList[i]);
        }
    }

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
    
    /**
     * JARs on which the final database project will depend.
     */
    private static final String[] LIB_DEP = {
        "jaxb-api.jar",
        "jaxb-impl.jar",
        "jaxb-libs.jar",
        "relaxngDatatype.jar"
    };
}
