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

import java.io.File;

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
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.cfg.Configuration;
import java.io.FilenameFilter;
import java.util.Properties;
import java.io.FileInputStream;
public class Xsd2db {
    public static final String xjcArgs[] = { "-debug",	//  HyperJaxb invokes xjc in debug mode for 
														//  ..some reason 
											"-nv",
											"-extension", 
														//  Enables extensions so we can use hyperjaxb2
											"-Xequals", //  equals extension is required for hyperjaxb2
											"-XhashCode", 
														//  hashCode extension is requred for hb2
											"-Xhyperjaxb" };
    public static final String HIB_PROPERTIES = "./hibernate.properties";

    /**
     * The execution entry point for the utility.
     *
     * @param args
     */
    public static void main(String[] args) {
        Xsd2dbCommandLine cmdline = new Xsd2dbCommandLine();
        cmdline.parse(args);
        Options options = new Options();
        options.targetDir = new File(cmdline.dbSrcDir.getPath() + File.separator + cmdline.subDirs[Xsd2dbCommandLine.SRC_DIR]);
        //  TODO:  need to add the bindings file
        if (cmdline.getSchemaType() == Xsd2dbCommandLine.DTD_SCHEMA)
            options.setSchemaLanguage(Options.SCHEMA_DTD);
        else
            options.setSchemaLanguage(Options.SCHEMA_XMLSCHEMA);
        //  Set the schema language to XSD
        System.out.println(cmdline.dbSrcDir.getPath());
        options.addGrammar(new org.xml.sax.InputSource(cmdline.dbSrcDir.getPath() + File.separator + cmdline.subDirs[Xsd2dbCommandLine.XSD_DIR] + File.separator + cmdline.xsdName));
        //  Sets the schema.

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
        } catch(Exception e) { // TODO: unsupress exception 
        }
        try {
            GeneratorContext generatorContext = Driver.generateCode(grammar, options, errorReceiver);
            if (generatorContext == null)
                System.out.println("failed to compile a schema");
            AbstractParameterizableCodeAugmenter codeAugmenter = new AddOn();
            // Calling the hyperjaxb2 addon manualy
            ErrorHandler errorHandler = new ConsoleErrorReporter();
            codeAugmenter.run(grammar, generatorContext, options, errorHandler);
            // Must run before building the code model.
            grammar.codeModel.build(Driver.createCodeWriter(options.targetDir, options.readOnly));
	
        } catch(Exception e) { // TODO: unsupress exception
            System.out.println(e.getMessage());
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
		}
		catch(Exception e) {
            System.out.println("Properties file failed to load.");
        }
		cfg.setProperties(hibProperties);
		addHibFiles(cfg, destDir);
        /*File fileList[] = hbmDir.listFiles(hbmFilter);
		if (fileList.length==0)
			System.out.println("No Files found");
		for(int i = 0; i< fileList.length; i++)
		{
			System.out.println(fileList[i].toString());
			if (fileList[i].toString().endsWith(".hbm.xml"))
			{
				cfg.addFile(fileList[i]);
			}
		}
        */
        
		SchemaExport schemaExporter = new SchemaExport(cfg);
		schemaExporter.setOutputFile(cmdline.dbSrcDir.getPath() + "/" + 
                                     cmdline.subDirs[Xsd2dbCommandLine.SQL_DIR] + 
                                     "/" + "schema.sql");
		schemaExporter.setDelimiter(";");
		schemaExporter.create(true, false);
        
        //File buildFile = new File("./CannedBuild.xml");
        //File movedBuildFile = new File(cmdline.dbSrcDir, "build.xml");
        //buildFile.renameTo(movedBuildFile);
        System.out.println("Build Finished!");
    }
	
	private static void addHibFiles(Configuration cfg, File hibDir)
    {
        HbmFilter hbmFilter = new HbmFilter();
        File fileList[] = hibDir.listFiles(hbmFilter);
        for(int i=0; i < fileList.length; i++)
        {
            if(!fileList[i].isDirectory())
                cfg.addFile(fileList[i]);
        }
    }
        
	public static void movefilesRecursive(File destDir, File srcDir, FilenameFilter filter)
	{
		File filesToMove[] = srcDir.listFiles(filter);
        for(int i=0; i < filesToMove.length; i++)
        {
            if (filesToMove[i].isDirectory())
                movefilesRecursive(destDir, filesToMove[i], filter);
            else
            {
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

	private static  class HbmFilter implements FilenameFilter
	{
		public boolean accept(File dir,
							  String name)
		{
			if(name.endsWith("hbm.xml") || (new File(dir+"/"+name)).isDirectory())
				return true;
			return false;
		}
	}


}


