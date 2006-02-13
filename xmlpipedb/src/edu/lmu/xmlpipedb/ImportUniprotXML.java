/*
  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

  Web: http://sourceforge.net/projects/xmlpipedb
*/

package edu.lmu.xmlpipedb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;



public class ImportUniprotXML {
	
	private static Unmarshaller unmarshaller;
	private static JAXBContext jaxbContext;
	private static SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		try {
			ImportUniprotXML.setXSD();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			System.out.println("Wrong package defined!");
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ImportUniprotXML.loadXML(new File("xmlFiles/109.P_putida.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Tried to import improper XML File");
			e.printStackTrace();
		}
		
		
    	
    }
	
	public static void setXSD() throws JAXBException, SAXException {
		
		jaxbContext = JAXBContext.newInstance("org.uniprot.uniprot");
		unmarshaller = jaxbContext.createUnmarshaller();
	
	}
	
	
	public static void loadXML(final File xmlFile) throws Exception {

		// Unmarshall the document
		final Object object = unmarshaller.unmarshal(xmlFile);
		
		// Create a new configuration
	    final Configuration cfg = new Configuration();
	    
	    cfg.addDirectory(new File("hbm/org/uniprot/uniprot"));

	    // Create the properties for Hibernate
	    final Properties properties = new Properties();
	    properties.load(new FileInputStream("hibernate.properties"));
	    cfg.setProperties(properties);
	    
		//
	    //	EVERYTHING WORKS UP TO HERE.
	    //
	    
		// Open the session
		sessionFactory = cfg.buildSessionFactory();
		
		final Session saveSession = sessionFactory.openSession();
		saveSession.saveOrUpdate(object);
		saveSession.flush();
		
		// Close the session
		saveSession.close();
	}
}
