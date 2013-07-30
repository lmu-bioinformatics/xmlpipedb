/********************************************************
 * Filename: UniprotXSDPostProcesor.java
 * Author: LMU
 * Program: uniprotdb
 * Description: Post processes uniprotdb files.  
 * Revision History:
 * 20060422: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.uniprotdb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFrame;

/**
 * UniprotXSDPostProcessor does post processing for the uniprot
 * database
 * 
 * @author   
 * @version  
 */
public class UniprotXSDPostProcessor extends JFrame {

	/**
	 * Private variables 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));

	/**
	 * This is it, the full post-processing "program."
	 */
	private PostProcessingStep[] postProcessingSteps = new PostProcessingStep[] {
	    new PostProcessingStep("uniprot.sql", ".sql", new FileProcessor[] {
	            new StringReplacer("varchar\\(255\\)", "varchar"),
	            new StringReplacer("End", "EndPosition"),
                new StringReplacer(" SourceType", " UniProtSourceType")
	    }),

	    new PostProcessingStep("LocationType.hbm.xml", ".xml", new FileProcessor[] {
                new StringReplacer("<column name=\"End\"/>", "<column name=\"EndPosition\"/>")
        }),

        new PostProcessingStep("CitationType.hbm.xml", ".xml", new FileProcessor[] {
                new StringReplacer(
                        "<any id-type=\"org.hibernate.type.LongType\" meta-type=\"org.hibernate.type.StringType\" name=\"Date\">",
                        "<property name=\"Date\">"
                    ),
                
                new StringReplacer("<column name=\"Date_Hjid\"/>", "<type name=\"org.hibernate.type.StringType\"/>"),
                new StringReplacer("</any>", "</property>")
        }),

        new PostProcessingStep("org.uniprot.uniprot.CitationType.java", ".java", new FileProcessor[] {
                new StringReplacer("java.io.Serializable getDate\\(\\);", "java.lang.String getDate();"),
                new StringReplacer(
                        "void setDate\\(java.io.Serializable value\\);",
                        "void setDate(java.lang.String value);"
                    )
        }),

        new PostProcessingStep("org.uniprot.uniprot.impl.CitationTypeImpl.java", ".java", new FileProcessor[] {
                new StringReplacer("protected java.io.Serializable _Date;", "protected java.lang.String _Date;"),
                new StringReplacer("public java.io.Serializable getDate\\(\\) \\{", "public java.lang.String getDate() {"),
                new StringReplacer(
                        "public void setDate\\(java.io.Serializable value\\) \\{",
                        "public void setDate(java.lang.String value) {"
                    ),

                // Special handling.
                new CitationTypeImplDateHandler(),
                
                new StringReplacer(
                        "_Date = javax.xml.bind.DatatypeConverter.parseDate\\(com.sun.xml.bind.WhiteSpaceProcessor.collapse\\(value\\)\\);",
                        "_Date = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);"
                    )
        }),

        new PostProcessingStep("SourceType.hbm.xml", ".xml", new FileProcessor[] {
                new StringReplacer("\"SourceType\"", "\"UniProtSourceType\"")
        })

	};

	/**
	 * A constructor for no sql file name given.
	 */
	public UniprotXSDPostProcessor() {
	    for (PostProcessingStep postProcessingStep: postProcessingSteps) {
	        postProcessingStep.processFile();
	    }

	    //
		// Notice for completion of post processing task.
		//
		JOptionPane.showMessageDialog(this,
			    "The files were processed and saved successfully!",
			    "Success!!!", 
			    JOptionPane.INFORMATION_MESSAGE);
		
		//
		// To be removed if code is integrated into another application.
		//
		System.exit(0);
	}

	/**
	 * A constructor with the given filenames, one per known post-processing step.  
	 * @throws IOException 
	 */
    public UniprotXSDPostProcessor(String... filenames) throws IOException {
        int index = 0;
        for (String filename: filenames) {
            postProcessingSteps[index].processFile(filename != null ? new File(filename) : null);
            index++;
        }
	}

	/**
	 * Asks the user to locate a particular file to post process
	 * @param fileName
	 */
	private void locateFileDialog(String fileName) {
		// Message dialog
		JOptionPane.showMessageDialog(this,
		    "Please locate the " + fileName + " file.",
		    "Please select a file", 
		    JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Error handling when no file is selected to post process
	 * @param fileName
	 */
	private void handleNoFileSelected(String fileName) {
    	// Notification message
    	JOptionPane.showMessageDialog(this,
    	    fileName + " not selected.\nSkipping it.",
    	    "No File Selected",
    	    JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Exception handling for file errors
	 * @param e
	 */
	private void handleIOException(IOException e) {
    	//Error message
    	JOptionPane.showMessageDialog(this,
    		e.getMessage().toString() + "\nSystem will exit.",
    	    "File I/O Error",
    	    JOptionPane.ERROR_MESSAGE);
	}


	/**
     * Creates a new output file and saves the changes to it.
	 * @throws IOException 
     */
	private void writeFile(String fileBuffer, File ouputFile) throws IOException {
        BufferedWriter out = new BufferedWriter(
            new FileWriter(ouputFile));
        out.write(fileBuffer);
        out.close();
	}

	/**
     * A file filter for files that end with the desired suffix.
     */
    private static class MyFilter extends FileFilter {
    	private final String EXTENSION;
    	
    	/**
    	 * File filtering
    	 * @param extension
    	 */
    	public MyFilter(String extension) {
    		this.EXTENSION = extension;
    	}
    	
    	/**
    	 * Gets the files with the appropriate extension
    	 */
		public boolean accept(File pathname) {			
		    if (pathname.isDirectory()) return true;
			return pathname.getName().endsWith(this.EXTENSION);
		}
		
		/**
		 * Gets the file with the appropriate extension
		 */
	    public String getDescription() {
	        return this.EXTENSION;
	    }
    }

	/**
     * Opens a file and imports it.
	 * @throws IOException 
	 * @throws FileNotFoundException 
     */
	private String openFile(File file) throws FileNotFoundException, IOException {	

		StringBuffer buffer = new StringBuffer();
		
		BufferedReader in = new BufferedReader(
		    new FileReader(file.getCanonicalPath()));
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line).append("\n");
        }
        in.close();
        return buffer.toString();
	}

	/**
	 * Common interface for post-processing activities.
	 */
	interface FileProcessor {
	    String process(String content);
	}

	/**
	 * Encapsulates one of the primary post-processing activities:
	 * string replacement.
	 */
	private class StringReplacer implements FileProcessor {
	    private String pattern;
	    private String replacement;

	    public StringReplacer(String pattern, String replacement) {
	        this.pattern = pattern;
	        this.replacement = replacement;
	    }
	    
	    @Override
	    public String process(String content) {
	        Pattern p = Pattern.compile(pattern);
	        Matcher m = p.matcher(content);
	        return m.replaceAll(replacement);
	    }
	    
	}

	private class CitationTypeImplDateHandler implements FileProcessor {

	    public String process(String content) {
	        Pattern p = Pattern.compile("if \\(_Date!= null\\) \\{");
	        Matcher m = p.matcher(content);
	        
	        int startPosition = 0;
	        
	        if (m.find()) {
	            startPosition = m.start();
	        }
	        
	        p = Pattern.compile("context.endAttribute\\(\\);");
	        m = p.matcher(content);
	        
	        int endPosition = 0;
	        
	        if (m.find(startPosition)) {
	            endPosition = m.end();
	        }
	        
	        String replacementString = "if (_Date!= null) {" + System.getProperty("line.separator") +
	            "context.startAttribute(\"\", \"date\");" + System.getProperty("line.separator") +
	            "if (_Date instanceof java.lang.String) {" + System.getProperty("line.separator") +
	            "try {" + System.getProperty("line.separator") +
	            "context.text(((java.lang.String) _Date), \"Date\");" + System.getProperty("line.separator") +
	            "} catch (java.lang.Exception e) {" + System.getProperty("line.separator") +
	            "org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);" + System.getProperty("line.separator") +
	            "}" + System.getProperty("line.separator") +
	            "}" + System.getProperty("line.separator") +
	            "context.endAttribute();";
	        
	        StringBuilder buffer = new StringBuilder(content);
	        buffer.replace(startPosition, endPosition, replacementString);
	        return buffer.toString();
	    }
	}

	/**
	 * A complete file processing step, including UI for determining
	 * the file to process and the steps to perform in order to process
	 * it.
	 */
	private class PostProcessingStep {
	    private String filename;
	    private String fileExtension;
	    private FileProcessor[] fileProcessors;
	    private String newFilename = null;

	    public PostProcessingStep(String filename, String fileExtension, FileProcessor[] fileProcessors) {
	        this.filename = filename;
	        this.fileExtension = fileExtension;
	        this.fileProcessors = fileProcessors;
	    }

        @SuppressWarnings("unused")
        public PostProcessingStep(String filename, String fileExtension, FileProcessor[] fileProcessors, String newFilename) {
            this(filename, fileExtension, fileProcessors);
            this.newFilename = newFilename;
        }

	    /**
	     * Intermediate step in case file choosing is needed.
	     */
	    public void processFile() {
	        locateFileDialog(filename);
	        chooser.setFileFilter(new MyFilter(fileExtension));
	        chooser.setDialogTitle("Locate the " + filename + " file");
	        chooser.showOpenDialog(UniprotXSDPostProcessor.this);
	        processFile(chooser.getSelectedFile());
	    }
	    
	    /**
	     * The actual processing step, with the given File.
	     * @param selectedFile
	     */
	    public void processFile(File selectedFile) {
            if (selectedFile == null) {
                handleNoFileSelected(filename);
            } else {
                try {
                    String content = openFile(selectedFile);
                    for (FileProcessor fileProcessor: fileProcessors) {
                        content = fileProcessor.process(content);
                    }
                    writeFile(content, selectedFile);
                    
                    // If specified, rename the file.
                    if (newFilename != null) {
                        File newFile = new File(selectedFile.getParentFile(), newFilename);
                        selectedFile.renameTo(newFile);
                    }
                } catch (IOException e) {
                    handleIOException(e);
                }
            }
	    }
	}

	/**
     * A tester main method for running the processor.
     */
	public static void main(final String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UniprotXSDPostProcessor();
            }
        });

	}

}
