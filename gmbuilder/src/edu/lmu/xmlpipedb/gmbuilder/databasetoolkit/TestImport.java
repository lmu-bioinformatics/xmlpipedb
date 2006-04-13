package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.util.utilities.ImportEngine;

public class TestImport {
	public static void main(String[] args) {
		final String hbmConfig = System.getProperty("user.dir")+ "/hbm";
		final String hibernateProperties = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/properties/hibernate.properties";
		final String xmlFile = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/xmlFiles/Sample.xml";
		File xmlToLoad = new File(xmlFile);
		try {
			ImportEngine xml2db = new ImportEngine("org.uniprot.uniprot", hbmConfig, hibernateProperties);
			xml2db.loadToDB(xmlToLoad);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			System.out.println("Wrong package defined!");
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}