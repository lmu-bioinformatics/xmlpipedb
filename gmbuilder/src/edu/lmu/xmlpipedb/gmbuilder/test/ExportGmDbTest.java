package edu.lmu.xmlpipedb.gmbuilder.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import shag.dialog.ModalDialog;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMaPP;
import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.ImportEngine;
import edu.lmu.xmlpipedb.util.engines.QueryEngine;

public class ExportGmDbTest extends TestCase {


	/**
	 * 
	 */
	public ExportGmDbTest(String name){
		super(name);
	}
	
	
	protected void setUp() throws FileNotFoundException{
		try {
			ExportToGenMaPP.exportToGenMaPP(new File("./src/edu/lmu/xmlpipedb/gmbuilder/test/gmb_unit_test.mdb"));
		} catch (HibernateException e) {
			ModalDialog.showErrorDialog("HIBERNATE error.");
			e.printStackTrace();
		} catch (SAXException e) {
			ModalDialog.showErrorDialog("SAX error.");
			e.printStackTrace();
		} catch (JAXBException e) {
			ModalDialog.showErrorDialog("JAXB error.");
			e.printStackTrace();
		} catch (SQLException e) {			
            ModalDialog.showErrorDialog("SQL error.");
            e.printStackTrace();
		} catch (IOException e) {
			ModalDialog.showErrorDialog("I/O error.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			ModalDialog.showErrorDialog("Database driver error.");
			e.printStackTrace();
		} catch (Exception e) {
			ModalDialog.showErrorDialog(e.toString());
			e.printStackTrace();
		}
	}
	

	
	
	public void testExportCounts(){
		// initialize expected test results

	} // end testImportCounts

	
	
}
