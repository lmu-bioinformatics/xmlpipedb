/********************************************************
 * Filename: GodbPostProcessor.java
 * Author: LMU
 * Program: Godb
 * Description: This file performs post processing on the
 * sql and hibernate mappings files that are created for
 * the go database.   
 * Revision History:
 * 20060322: Initial Revision.   
 * 20060328: Added ability to change LocationType.hbm.xml
 * 20060401: Initial release for go post processor
 * 20060426: Implement new changes according to uniprot 
 *           post processing tool
 * file.   
 * *****************************************************/
 
package edu.lmu.xmlpipedb.godb;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFrame;

/**
 * GodbXSDPostProcessor does post processing for the Godb
 * database. This file replaces the varchar(50) in the sql
 * schema definition file with varchar.  It also replaces 
 * the instaces of the "To" table in the schema file and the
 * To.hbm.xml file with "To_". Same GUI and design as the
 * uniprot postprocessor.
 * 
 * @author   Roberto Ruiz
 * @version  
 */
public class GodbPostProcessor extends JFrame {

	/**
	 * Private variables 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String SQL_EXTENSION = ".sql";
	private static final String XML_EXTENSION = ".xml";
		
	private static final String SQL_FILE = "godb.sql";
	private static final String SQL_DIALOG_MESSAGE = "Open godb.sql";
	private static final String HBM_FILE_1 = "To.hbm.xml";
	private static final String HBM_DIALOG_MESSAGE = "Open To.hbm.xml";
	
	private JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));

	private File sqlFile;
	private File hbmFile1;
	
	/**
	 * A constructor for no sql file name given.
	 */
	public GodbPostProcessor() {
		
		// Notice for selecting the godb sql file.
		locateFileDialog(SQL_FILE);
		chooser.setDialogTitle(SQL_DIALOG_MESSAGE);
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

		//Notice for selecting the To.hbm.xml file.
		locateFileDialog(HBM_FILE_1);
		chooser.setDialogTitle(HBM_DIALOG_MESSAGE);
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
    public GodbPostProcessor(String sqlFile, String hbmFile1) throws IOException {
		this.sqlFile = new File(sqlFile);
		this.hbmFile1 = new File(hbmFile1);

		processSQLFile();
		processHBMFile1();
	}
    
	/**
	 * Make changes to SQL file.  Used pattern and matcher for speed on large files.
	 * replaces varchar(255) and To with varchar and To_ respectively.
	 * @throws IOException 
	 */
	private void processSQLFile() throws IOException {
		
		String sqlFileBuffer = openFile(sqlFile);

		Pattern p = Pattern.compile("varchar\\(255\\)");
		Matcher m = p.matcher(sqlFileBuffer);
		sqlFileBuffer = m.replaceAll("varchar");

		p = Pattern.compile("To");
		m = p.matcher(sqlFileBuffer);
		sqlFileBuffer = m.replaceAll("To_");
		
		writeFile(sqlFileBuffer, sqlFile);		
	}
	
	/**
	 * Make changes to HBM file.
	 * Replaces the table name To with To_ 
	 * @throws IOException 
	 */
	private void processHBMFile1() throws IOException {
		
		String hbmFileBuffer = openFile(hbmFile1);
		
		Pattern p = Pattern.compile("table=\"To\"");
		Matcher m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("table=\"To_\"");

		writeFile(hbmFileBuffer, hbmFile1);
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
     * A main method for running the processor.
     */
	public static void main(final String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GodbPostProcessor();
            }
        });
     }
}
