/********************************************************
 * Filename: UniprotXSDPostProcessor.java
 * Author: LMU
 * Program: uniprotdb
 * Description: This file performs post processing on the
 * sql and hibernate mappings files that are created for
 * the uniprot database.   
 * Revision History:
 * 20060322: Initial Revision.   
 * 20060328: Added ability to change LocationType.hbm.xml
 * file.   
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
	
	private static final String sqlFileString = "uniprotSQLFile";
	private static final String hbmFileString1 = "locationTypeFile";
	private static final String hbmFileString2 = "citationTypeFile";
	
	private static final String help = "help";
	private static final String usage = "usage: uniprotdb [--" + sqlFileString + "=filename] " + "[--" + hbmFileString1 + "=filename]" + "[--" + hbmFileString2 + "=filename] [-" + help + "]";
	private static final String helpMsg = "--" + sqlFileString + "=filename -- " + "Full path to the uniprot sql file\n" + "--" + hbmFileString1 + "=filename -- " + "Full path to the LocationType.hbm.xml file\n" + "--" + hbmFileString2 + "=filename -- " + "Full path to the CitationType.hbm.xml file\n" + "-" + help + " -- Displays this help and exits\n";
	
	private JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
	private final File sqlFile;
	private final File hbmFile1;
	private final File hbmFile2;
	
	private static Options options;
	
	/**
	 * A constructor for no sql file name given.
	 */
	public UniprotXSDPostProcessor() {
		
		// Notice for selecting the uniprot sql file.
		JOptionPane.showMessageDialog(this,
		    "Please locate the uniprot sql file.",
		    "Please select a file", 
		    JOptionPane.INFORMATION_MESSAGE);

		chooser.setFileFilter(new MyFilter(SQL_EXTENSION));
		chooser.showOpenDialog(this);
		sqlFile = chooser.getSelectedFile();
		if (sqlFile == null) {
        	// Error message
        	JOptionPane.showMessageDialog(this,
        	    "Must select the uniprot.sql file.\nSystem will exit.",
        	    "No File Selected",
        	    JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
      }
		try {
			processSQLFile();
		} catch (IOException e) {
        	//Error message
        	JOptionPane.showMessageDialog(this,
        		e.getMessage().toString() + "\nSystem will exit.",
        	    "File I/O Error",
        	    JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
		}
		
		//Notice for selecting the LocationType.hbm.xml file.
		JOptionPane.showMessageDialog(this,
			    "Please locate the LocationType.hbm.xml file.",
			    "Please select a file", 
			    JOptionPane.INFORMATION_MESSAGE);
		
		chooser.setFileFilter(new MyFilter(XML_EXTENSION));
      chooser.showOpenDialog(this);
      hbmFile1 = chooser.getSelectedFile();
      if (hbmFile1 == null) {
        	//Error message
        	JOptionPane.showMessageDialog(this,
        	    "Must select the LocationType.hbm.xml file.\nSystem will exit.",
        	    "No File Selected",
        	    JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
      }
      try {
			processHBMFile1();
		} catch (IOException e) {
        	//Error message
        	JOptionPane.showMessageDialog(this,
        		e.getMessage().toString() + "\nSystem will exit.",
        	    "File I/O Error",
        	    JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
		}
        
		//Notice for selecting the CitationType.hbm.xml file.
		JOptionPane.showMessageDialog(this,
			    "Please locate the CitationType.hbm.xml file.",
			    "Please select a file", 
			    JOptionPane.INFORMATION_MESSAGE);
		
		chooser.setFileFilter(new MyFilter(XML_EXTENSION));
      chooser.showOpenDialog(this);
      hbmFile2 = chooser.getSelectedFile();
      if (hbmFile2 == null) {
        	//Error message
        	JOptionPane.showMessageDialog(this,
        	    "Must select the CitationType.hbm.xml file.\nSystem will exit.",
        	    "No File Selected",
        	    JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
      }
      try {
			processHBMFile2();
		} catch (IOException e) {
        	//Error message
        	JOptionPane.showMessageDialog(this,
        		e.getMessage().toString() + "\nSystem will exit.",
        	    "File I/O Error",
        	    JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
		}
		
		//Notice for completion of post processing task.
		JOptionPane.showMessageDialog(this,
			    "The files were processed and saved successfully!",
			    "Please select a file", 
			    JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	/**
	 * A constructor with a given sql file name and hibernate mapping file name.  
	 * @throws IOException 
	 */
    public UniprotXSDPostProcessor(String sqlFile, String hbmFile1, String hbmFile2) throws IOException {
		this.sqlFile = new File(sqlFile);
		this.hbmFile1 = new File(hbmFile1);
		this.hbmFile2 = new File(hbmFile2);
		processSQLFile();
		processHBMFile1();
		processHBMFile2();
		System.exit(0);
	}

	/**
	 * Make changes to SQL file.  Used pattern and matcher for speed on large files.
	 * @throws IOException 
	 */
	private void processSQLFile() throws IOException {
    	//System.out.println("Opening file...");
		String sqlFileBuffer = openFile(sqlFile);
		
		//System.out.println("Processing file...");
		
		//System.out.println("EDITING: \"varchar(255)\" to \"varchar\"");
		Pattern p = Pattern.compile("varchar\\(255\\)");
		Matcher m = p.matcher(sqlFileBuffer);
		sqlFileBuffer = m.replaceAll("varchar");
		
		//System.out.println("EDITING: \"End\" to \"EndPosition\"");
		p = Pattern.compile("End");
		m = p.matcher(sqlFileBuffer);
		sqlFileBuffer = m.replaceAll("EndPosition");
		
		//System.out.println("Writing file...");
		writeFile(sqlFileBuffer, sqlFile);
		
		//System.out.println("Finished.");
	}

	/**
	 * Make changes to HBM file.
	 * @throws IOException 
	 */
	private void processHBMFile1() throws IOException {
		
		//System.out.println("Opening file...");
		String hbmFileBuffer = openFile(hbmFile1);
		
		//System.out.println("Processing file...");
		
		Pattern p = Pattern.compile("<column name=\"End\"/>");
		Matcher m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("<column name=\"EndPosition\"/>");

		//System.out.println("Writing file...");
		writeFile(hbmFileBuffer, hbmFile1);
		
		//System.out.println("Finished.");
	}
	
	/**
	 * Make changes to HBM file.
	 * @throws IOException 
	 */
	private void processHBMFile2() throws IOException {
		
		//System.out.println("Opening file...");
		String hbmFileBuffer = openFile(hbmFile2);
		
		//System.out.println("Processing file...");
		
		Pattern p = Pattern.compile("<any id-type=\"org.hibernate.type.LongType\" meta-type=\"org.hibernate.type.StringType\" name=\"Date\">");
		Matcher m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("<property name=\"Date\">");
		p = Pattern.compile("<column name=\"Date_Hjid\"/>");
		m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("<type name=\"org.hibernate.type.StringType\"/>");
		p = Pattern.compile("</any>");
		m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("</property>");
		
		//System.out.println("Writing file...");
		writeFile(hbmFileBuffer, hbmFile2);
		
		//System.out.println("Finished.");
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
    	public MyFilter(String extension) {
    		this.EXTENSION = extension;
    	}
		public boolean accept(File pathname) {			
		    if (pathname.isDirectory()) return true;
			return pathname.getName().endsWith(this.EXTENSION);
		}
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
     * Prints error message and then exists the program
     * @param s 	the message to print
     */
    private static void printErrorMsgAndExit(String s) {
        System.out.print(s);
        System.exit(0);
    }
	
   /**
   * Adds the acceptable command line options
   * 
   */
   private static void addOptions() {
      options.addOption(help, false, null);
      options.addOption(OptionBuilder.withLongOpt(sqlFileString).withValueSeparator('=').hasArg().create());
      options.addOption(OptionBuilder.withLongOpt(hbmFileString1).withValueSeparator('=').hasArg().create());
      options.addOption(OptionBuilder.withLongOpt(hbmFileString2).withValueSeparator('=').hasArg().create());
    }
	
	/**
     * A tester main method for running the processor.
     * 
     * To run with arguments:
     * 
     * (1) argument is the full path to the uniprot.sql file.
     * (2) argument is the full path to the CitationType.hbm.xml file.
     * 
     */
	public static void main(final String[] args) {
      CommandLineParser parser = new PosixParser();
      CommandLine line = null;
      final String sqlFilePath;
      final String hbmFilePath1;
      final String hbmFilePath2;
        
		if(args.length == 0) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new UniprotXSDPostProcessor();
	            }
	        });
		} else {
		      options = new Options();
            addOptions(); 
            
            try {
               line = parser.parse(options, args);
            } catch(ParseException e) {
               printErrorMsgAndExit(e.getMessage() + "\n\n" + usage);
            }
		
            if(line.hasOption(help)) {
               printErrorMsgAndExit(usage + "\n\n" + helpMsg);
		      } else {
		         if(args.length != 3) {
		            printErrorMsgAndExit(usage + "\n\n" + helpMsg);
		         }
		         
		         sqlFilePath = line.hasOption(sqlFileString) ? line.getOptionValue(sqlFileString) : "";
		         hbmFilePath1 = line.hasOption(hbmFileString1) ? line.getOptionValue(hbmFileString1) : "";
		         hbmFilePath2 = line.hasOption(hbmFileString2) ? line.getOptionValue(hbmFileString2) : "";
		         
		         if(sqlFilePath.equals("") || hbmFilePath1.equals("") || hbmFilePath2.equals(""))
		            printErrorMsgAndExit(usage + "\n\n" + helpMsg);
		        
		         
		         SwingUtilities.invokeLater(new Runnable() {
		        	 public void run() {
		        		 try {
		        			 new UniprotXSDPostProcessor(sqlFilePath, hbmFilePath1, hbmFilePath2);
		        		 } catch (IOException e) {
		        			 e.printStackTrace();
		        		 }
		        	 }
		         });
		      }
		}
      }
}
