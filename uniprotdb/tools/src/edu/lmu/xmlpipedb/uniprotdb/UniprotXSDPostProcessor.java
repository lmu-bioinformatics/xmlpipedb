/********************************************************
 * Filename: UniprotXSDPostProcesor.java
 * Author: LMU
 * Program: TUnit
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
	
	private static final String SQL_EXTENSION = ".sql";
	private static final String XML_EXTENSION = ".xml";
	private static final String JAVA_EXTENSION = ".java";
	
	private static final String SQL_FILE = "uniprot.sql";
	private static final String HBM_FILE_1 = "LocationType.hbm.xml";
	private static final String HBM_FILE_2 = "CitationType.hbm.xml";
	private static final String JAVA_FILE_1 = "org.uniprot.uniprot.CitationType.java";
	private static final String JAVA_FILE_2 = "org.uniprot.uniprot.impl.CitationTypeImpl.java";

	private JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
	
	private File sqlFile;
	private File hbmFile1;
	private File hbmFile2;
	private File javaFile1;
	private File javaFile2;
	
	/**
	 * A constructor for no sql file name given.
	 */
	public UniprotXSDPostProcessor() {
		
		//
		// Notice for selecting the uniprot sql file.
		//
		locateFileDialog(SQL_FILE);
		chooser.setFileFilter(new MyFilter(SQL_EXTENSION));
		chooser.showOpenDialog(this);
		sqlFile = chooser.getSelectedFile();
		if (sqlFile == null) {
        	handleNoFileSelectedError(SQL_FILE);
        	return;
		}
		try {
			processSQLFile();
		} catch (IOException e) {
        	handleIOException(e);
        	return;
		}
		
		//
		// Notice for selecting the LocationType.hbm.xml file.
		//
		locateFileDialog(HBM_FILE_1);
		chooser.setFileFilter(new MyFilter(XML_EXTENSION));
		chooser.showOpenDialog(this);
		hbmFile1 = chooser.getSelectedFile();
		if (hbmFile1 == null) {
        	handleNoFileSelectedError(HBM_FILE_1);
        	return;
		}
		try {
			processHBMFile1();
		} catch (IOException e) {
			handleIOException(e);
			return;
		}
        
		//
		// Notice for selecting the CitationType.hbm.xml file.
		//
		locateFileDialog(HBM_FILE_2);
		chooser.setFileFilter(new MyFilter(XML_EXTENSION));
		chooser.showOpenDialog(this);
		hbmFile2 = chooser.getSelectedFile();
		if (hbmFile2 == null) {
        	handleNoFileSelectedError(HBM_FILE_2);
        	return;
		}
		try {
			processHBMFile2();
		} catch (IOException e) {
			handleIOException(e);
			return;
		}
		
		//
		// Notice for selecting the org.uniprot.uniprot.CitationType.java file.
		//
		locateFileDialog(JAVA_FILE_1);
		chooser.setFileFilter(new MyFilter(JAVA_EXTENSION));
		chooser.showOpenDialog(this);
		javaFile1 = chooser.getSelectedFile();
		if (javaFile1 == null) {
        	handleNoFileSelectedError(JAVA_FILE_1);
        	return;
		}
		try {
			processJavaFile1();
		} catch (IOException e) {
			handleIOException(e);
			return;
		}
		
		//
		// Notice for selecting the org.uniprot.uniprot.impl.CitationType.java file.
		//
		locateFileDialog(JAVA_FILE_2);
		chooser.setFileFilter(new MyFilter(JAVA_EXTENSION));
		chooser.showOpenDialog(this);
		javaFile2 = chooser.getSelectedFile();
		if (javaFile2 == null) {
        	handleNoFileSelectedError(JAVA_FILE_2);
        	return;
		}
		try {
			processJavaFile2();
		} catch (IOException e) {
			handleIOException(e);
			return;
		}
		
		//
		// Notice for completion of post processing task.
		//
		JOptionPane.showMessageDialog(this,
			    "The files were processed and saved successfully!",
			    "Please select a file", 
			    JOptionPane.INFORMATION_MESSAGE);
		
		//
		// To be removed if code is integrated into another application.
		//
		System.exit(0);
	}

	/**
	 * A constructor with a given sql file name and hibernate mapping file name.  
	 * @throws IOException 
	 */
    public UniprotXSDPostProcessor(String sqlFile, 
    		String hbmFile1, String hbmFile2, 
    		String javaFile1, String javaFile2) throws IOException {
		this.sqlFile = new File(sqlFile);
		this.hbmFile1 = new File(hbmFile1);
		this.hbmFile2 = new File(hbmFile2);
		this.javaFile1 = new File(javaFile1);
		this.javaFile2 = new File(javaFile2);
		processSQLFile();
		processHBMFile1();
		processHBMFile2();
		processJavaFile1();
		processJavaFile2();
	}


	/**
	 * Make changes to SQL file.  Used pattern and matcher for speed on large files.
	 * @throws IOException 
	 */
	private void processSQLFile() throws IOException {

		String sqlFileBuffer = openFile(sqlFile);
		
		Pattern p = Pattern.compile("varchar\\(255\\)");
		Matcher m = p.matcher(sqlFileBuffer);
		sqlFileBuffer = m.replaceAll("varchar");
		
		p = Pattern.compile("End");
		m = p.matcher(sqlFileBuffer);
		sqlFileBuffer = m.replaceAll("EndPosition");
		
		writeFile(sqlFileBuffer, sqlFile);
	}

	/**
	 * Make changes to HBM file.
	 * @throws IOException 
	 */
	private void processHBMFile1() throws IOException {
		
		String hbmFileBuffer = openFile(hbmFile1);
		
		Pattern p = Pattern.compile("<column name=\"End\"/>");
		Matcher m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("<column name=\"EndPosition\"/>");

		writeFile(hbmFileBuffer, hbmFile1);
	}
	
	/**
	 * Make changes to HBM file.
	 * @throws IOException 
	 */
	private void processHBMFile2() throws IOException {
		
		String hbmFileBuffer = openFile(hbmFile2);
		
		Pattern p = Pattern.compile("<any id-type=\"org.hibernate.type.LongType\" meta-type=\"org.hibernate.type.StringType\" name=\"Date\">");
		Matcher m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("<property name=\"Date\">");
		p = Pattern.compile("<column name=\"Date_Hjid\"/>");
		m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("<type name=\"org.hibernate.type.StringType\"/>");
		p = Pattern.compile("</any>");
		m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("</property>");
		
		writeFile(hbmFileBuffer, hbmFile2);
	}

	/**
	 * Make changes to Java file.
	 * @throws IOException 
	 */
	private void processJavaFile1()  throws IOException {
		
		String javaFileBuffer = openFile(javaFile1);
		
		Pattern p = Pattern.compile("java.io.Serializable getDate\\(\\);");
		Matcher m = p.matcher(javaFileBuffer);
		javaFileBuffer = m.replaceAll("java.lang.String getDate();");
		
		p = Pattern.compile("void setDate\\(java.io.Serializable value\\);");
		m = p.matcher(javaFileBuffer);
		javaFileBuffer = m.replaceAll("void setDate(java.lang.String value);");
		
		writeFile(javaFileBuffer, javaFile1);
	}
	
	/**
	 * Make changes to the Java file.
	 * @throws IOException 
	 */
	private void processJavaFile2()  throws IOException {
		
		String javaFileBuffer = openFile(javaFile2);
		
		Pattern p = Pattern.compile("protected java.io.Serializable _Date;");
		Matcher m = p.matcher(javaFileBuffer);
		javaFileBuffer = m.replaceAll("protected java.lang.String _Date;");
		
		p = Pattern.compile("public java.io.Serializable getDate\\(\\) \\{");
		m = p.matcher(javaFileBuffer);
		javaFileBuffer = m.replaceAll("public java.lang.String getDate() {");
		
		p = Pattern.compile("public void setDate\\(java.io.Serializable value\\) \\{");
		m = p.matcher(javaFileBuffer);
		javaFileBuffer = m.replaceAll("public void setDate(java.lang.String value) {");
		
		p = Pattern.compile("if \\(_Date!= null\\) \\{");
		m = p.matcher(javaFileBuffer);
		
		int startPosition = 0;
		
		if(m.find()) {
			startPosition = m.start();
		}
		
		p = Pattern.compile("context.endAttribute\\(\\);");
		m = p.matcher(javaFileBuffer);
		
		int endPosition = 0;
		
		if(m.find(startPosition)) {
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
		
		StringBuffer buffer = new StringBuffer(javaFileBuffer);
		
		buffer.replace(startPosition, endPosition, replacementString);
		javaFileBuffer = buffer.toString();
		
		p = Pattern.compile("_Date = javax.xml.bind.DatatypeConverter.parseDate\\(com.sun.xml.bind.WhiteSpaceProcessor.collapse\\(value\\)\\);");
		m = p.matcher(javaFileBuffer);
		javaFileBuffer = m.replaceAll("_Date = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);");
			
		writeFile(javaFileBuffer, javaFile2);
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
	private void handleNoFileSelectedError(String fileName) {
    	// Error message
    	JOptionPane.showMessageDialog(this,
    	    "Must select the " + fileName + " file.\nSystem will exit.",
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
