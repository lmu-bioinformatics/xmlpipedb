/** Revision History
 *  ??/??/?? Scott. File orignaly created. 
 *  03/15/06 Adam Carasso.  Added xjc and hyperjaxb2 addin exectution 
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

public class Xsd2db {
    public static final String xjcArgs[] = { "-debug", //  HyperJaxb invokes xjc in debug mode for 
            //  ..some reason 
            "-nv", "-extension", //  Enables extensions so we can use hyperjaxb2
            "-Xequals", //  equals extension is required for hyperjaxb2
            "-XhashCode", //  hashCode extension is requred for hb2
            "-Xhyperjaxb" };

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
        System.out.println("Build Finished!");
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
}
