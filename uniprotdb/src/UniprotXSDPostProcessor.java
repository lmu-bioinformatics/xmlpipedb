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
 * @author jjbarret
 *
 */
/**
 * @author jjbarret
 *
 */
public class UniprotXSDPostProcessor extends JFrame {

	/**
	 * Hmmm. Comments?
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String SQL_EXTENSION = ".sql";
	private static final String XML_EXTENSION = ".xml";
	
	private JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
	private final File sqlFile;
	private final File hbmFile;
	
	/**
	 * A constructor for no sql file name given.
	 */
	public UniprotXSDPostProcessor() {
		
		//Notice for selecting the uniprot sql file.
		JOptionPane.showMessageDialog(this,
		    "Please locate the uniprot.sql file.",
		    "Please select a file", 
		    JOptionPane.INFORMATION_MESSAGE);

		chooser.setFileFilter(new MyFilter(SQL_EXTENSION));
        chooser.showOpenDialog(this);
        sqlFile = chooser.getSelectedFile();
        if (sqlFile == null) {
        	//Error message
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
		
		//Notice for selecting the CitationType.hbm.xml file.
		JOptionPane.showMessageDialog(this,
			    "Please locate the CitationType.hbm.xml file.",
			    "Please select a file", 
			    JOptionPane.INFORMATION_MESSAGE);
		
		chooser.setFileFilter(new MyFilter(XML_EXTENSION));
        chooser.showOpenDialog(this);
        hbmFile = chooser.getSelectedFile();
        if (sqlFile == null) {
        	//Error message
        	JOptionPane.showMessageDialog(this,
        	    "Must select the CitationType.hbm.xml file.\nSystem will exit.",
        	    "No File Selected",
        	    JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
        }
        try {
			processHMBFile();
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
	}

	/**
	 * A constructor with a given sql file name.  No checks made to insure
	 * file exists.
	 * @throws IOException 
	 */
    public UniprotXSDPostProcessor(String sqlFile, String hbmFile) throws IOException {
		this.sqlFile = new File(sqlFile);
		this.hbmFile = new File(hbmFile);
	
		processSQLFile();
		processHMBFile();
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
		sqlFileBuffer = m.replaceAll("text");
		
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
	private void processHMBFile() throws IOException {
		
		//System.out.println("Opening file...");
		String hbmFileBuffer = openFile(hbmFile);
		
		//System.out.println("Processing file...");
		
		//System.out.println("EDITING: \"varchar(255)\" to \"varchar\"");
		Pattern p = Pattern.compile("<any id-type=\"org.hibernate.type.LongType\" meta-type=\"org.hibernate.type.StringType\" name=\"Date\">");
		Matcher m = p.matcher(hbmFileBuffer);
		hbmFileBuffer = m.replaceAll("<any id-type=\"org.hibernate.type.StringType\" meta-type=\"org.hibernate.type.StringType\" name=\"Date\">");
	
		
		//System.out.println("Writing file...");
		writeFile(hbmFileBuffer, hbmFile);
		
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
     * A tester main method for running the processor.
     * 
     * To run with arguments:
     * 
     * (1) argument is the full path to the uniprot.sql file.
     * (2) argument is the full path to the CitationType.hbm.xml file.
     * 
     */
	public static void main(final String[] args) {
		if(args.length == 0) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new UniprotXSDPostProcessor();
	            }
	        });
		} else {
			//NO CHECKS FOR CORRECT COMMAND LINE PARAMETERS!!!
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                try {
						new UniprotXSDPostProcessor(args[0], args[1]);
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        });
		}
		System.exit(0);
	}
}
