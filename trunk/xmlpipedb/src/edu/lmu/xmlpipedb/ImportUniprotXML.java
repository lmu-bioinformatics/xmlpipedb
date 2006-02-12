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

import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class ImportUniprotXML {
	
	private static Unmarshaller unmarshaller;
	private static JAXBContext jaxbContext;
	private static Schema schema;
	private static SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		try {
			ImportUniprotXML.setXSD(new File("schema/uniprot.xsd"), "generated.java.org.uniprot.uniprot");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			System.out.println("1");
			e.printStackTrace();
		}
		
		try {
			ImportUniprotXML.loadXML(new File("xmlFiles/109.P_putida.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("2");
			e.printStackTrace();
		}
		
		
    	
    }
	
	public static void setXSD(final File xsdFile, final String classesLocation) throws JAXBException {
		
		jaxbContext = JAXBContext.newInstance("generated.java.org.uniprot.uniprot");
		unmarshaller = jaxbContext.createUnmarshaller();
		
        // create a SchemaFactory that conforms to W3C XML Schema
        //SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // parse the purchase order schema
        //schema = sf.newSchema(xsdFile);

        //unmarshaller.setSchema(schema);
        // set your error handler to catch errors during schema construction
        // we can use custom validation event handler
        //unmarshaller.setEventHandler(new MyValidationEventHandler());
		
		
	}
	
	
	public static void loadXML(final File xmlFile) throws Exception {
		//final Document document = getSampleDocument(file);
		//final Class sampleClass = getSampleClass(document);
		
		// Construct JAXB context
		//final String implementingPackageName = sampleClass.getPackage().getName();
		//final String packageName = implementingPackageName.substring(0, implementingPackageName.lastIndexOf('.'));
		
		

		
		// Unmarshall the document
		final Object object = unmarshaller.unmarshal(xmlFile);
		
		
	    final Configuration cfg = new Configuration();

	    final Properties properties = new Properties();
	    properties.load(new FileInputStream(getHibernatePropertiesFile()));
	    cfg.setProperties(properties);
	    //addDirectory(cfg, getHibernateDirectory(), true, new DefaultFilenameFilter("*.hbm.xml"));
		
		
		
		// Open the session
		sessionFactory = cfg.buildSessionFactory();
		final Session saveSession = sessionFactory.openSession();
		final Serializable id = saveSession.save(object);
		saveSession.save(id);
		saveSession.flush();
		
		// Close the session
		saveSession.close();
		
		// Open the seesion, load the object
		//final Session loadSession = sessionFactory.openSession();
		//final Object loadedObject = loadSession.load(sampleClass, id);
		
		// Close the session
		//loadSession.close();
	}
	

	  private static Configuration addDirectory(final Configuration configuration,
	                                     final File directory, final boolean recurse, final FilenameFilter filenameFilter)
	      throws IOException, MappingException {
	    Configuration extendedConfiguration = configuration;
	    if (!directory.isDirectory()) {
	      throw new IOException("Passed file handle [" +
	          directory.getAbsolutePath() + "] is not a directory.");
	    }
	    final File[] files = directory.listFiles();
	    for (int index = 0; index < files.length; index++) {
	      final File file = files[index];
	      if (recurse && file.isDirectory()) {
	        extendedConfiguration = addDirectory(extendedConfiguration,
	            file, recurse, filenameFilter);
	      }
	      else if (file.isFile() &&
	          filenameFilter.accept(directory, file.getName())) {
	        extendedConfiguration = extendedConfiguration.addFile(file);
	      }
	    }
	    return configuration;
	  }

	  /**
	   * Returns the directory containing Hibernate mapping.
	   *
	   * @return Directory containing Hibernate mapping.
	   */
	  public static File getHibernateDirectory() {
	    return new File("hibernate");
	  }

	  /**
	   * Returns Hibernate properties file.
	   *
	   * @return Hibernate properties file.
	   */
	  public static File getHibernatePropertiesFile() {
	    return new File(getHibernateDirectory(), "hibernate.properties");
	  }
	
}
