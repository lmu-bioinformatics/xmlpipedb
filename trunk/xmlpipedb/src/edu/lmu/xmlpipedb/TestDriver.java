package edu.lmu.xmlpipedb;

import java.io.File;

public class TestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImportUniprotXML iux = new ImportUniprotXML();
		
		File myFile = new File( "c:/!temp/109.P_putida.xml" );
		
		try {
			if( myFile.exists() && myFile.canRead() )
				iux.loadXML( myFile );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
