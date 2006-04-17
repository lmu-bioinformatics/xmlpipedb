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
 
package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class XMLPreProcessor extends JFrame {
	
	final static String xmlFile = System.getProperty("user.dir")+ "/xml/18.E_coli_K12.xml";
	final static File outputFile = new File(System.getProperty("user.dir")+ "/xml/output.xml");
	private static File xmlToLoad = new File(xmlFile);
	
	public static void main(final String[] args) {
		

		
		try {
			System.out.println("start");
			String file = openFile(xmlToLoad);
			processSQLFile(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	




	/**
	 * Make changes to SQL file.  Used pattern and matcher for speed on large files.
	 * @throws IOException 
	 */
	private static void processSQLFile(String sqlFile) throws IOException {

		List<String> stringList = new ArrayList<String>();
		
		
		Pattern p = Pattern.compile("<entry");
		Matcher m = p.matcher(sqlFile);
		
		int totalFindCount = 0;
		while(m.find()) {
			totalFindCount++;
		}
//		Pattern p = Pattern.compile("<citation type=");
//		Matcher m = p.matcher(sqlFile);
//		
//		Pattern p2 = Pattern.compile(">");
//		
//		
//		int totalFindCount = 0;
//		while(m.find()) {
//			totalFindCount++;
//			Matcher m2 = p2.matcher(sqlFile);
//			
//			if(m2.find(m.start())) {
//				String found = sqlFile.substring(m.start(), m2.end());
//				boolean foundFlag = false;
//				for(String s : stringList) {
//					if(s.equals(found)) {
//						foundFlag = true;
//					}
//				}
//				if(!foundFlag) {
//					stringList.add(found);
//				}
//			}
//		}
		
		StringBuffer output = new StringBuffer();
//		System.out("TOTAL FIND COUNT: " + totalFindCount).append("\n");
		
		//output.append("DISTINCT COUNT: " + stringList.size()).append("\n");
		
		//for(String s : stringList) {
		//	output.append(s).append("\n");
		//}

		//System.out.println("Writing file...");
		//writeFile(output.toString(), outputFile);
		
		//System.out.println("Finished.");
	}

	/**
     * Creates a new output file and saves the changes to it.
	 * @throws IOException 
     */
	private static void writeFile(String fileBuffer, File ouputFile) throws IOException {

        BufferedWriter out = new BufferedWriter(
            new FileWriter(ouputFile));
        out.write(fileBuffer);
        out.close();
	}

	/**
     * Opens a file and imports it.
	 * @throws IOException 
	 * @throws FileNotFoundException 
     */
	private static String openFile(File file) throws FileNotFoundException, IOException {	

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

}
