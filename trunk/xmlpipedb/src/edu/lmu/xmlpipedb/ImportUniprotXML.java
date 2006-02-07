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
import java.io.IOException;
import java.io.Serializable;
import java.io.FilenameFilter;
import java.io.FileInputStream;
import java.util.Properties;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.MappingException;
import org.pf.file.DefaultFilenameFilter;

public class ImportUniprotXML {
	/**
	 * Takes a XML file and places it into
	 * the Uniprot database
	 */
	public void loadXML(final File file) throws Exception {
		final Document document = getSampleDocument(file);
		final Class sampleClass = getSampleClass(document);
		
		// Construct JAXB context
		final String implementingPackageName = sampleClass.getPackage().getName();
		final String packageName = implementingPackageName.substring(0, implementingPackageName.lastIndexOf('.'));
		final JAXBContext context = JAXBContext.newInstance(packageName);
		unmarshaller = context.createUnmarshaller();
		
		// Unmarshall the document
		final Object object = unmarshaller.unmarshal(document);
		
		// Open the session
		sessionFactory = buildSessionFactory();
		final Session saveSession = sessionFactory.openSession();
		final Serializable id = saveSession.save(object);
		saveSession.flush();
		
		// Close the session
		saveSession.close();
		
		// Open the seesion, load the object
		final Session loadSession = sessionFactory.openSession();
		final Object loadedObject = loadSession.load(sampleClass, id);
		
		// Close the session
		loadSession.close();
	}
	
	  /**
	   * Builds the session factory. This method loads all the <code>*.hbm.xml</code> files
	   * from the Hibernate directory and builds a session factory out of them.
	   *
	   * @return Initialized session factory.
	   * @throws Exception In case of problems building the session factory.
	   */
	  protected SessionFactory buildSessionFactory() throws Exception {
	    final Configuration cfg = new Configuration();

	    final Properties properties = new Properties();
	    properties.load(new FileInputStream(getHibernatePropertiesFile()));
	    cfg.setProperties(properties);
	    addDirectory(cfg, getHibernateDirectory(), true, new DefaultFilenameFilter("*.hbm.xml"));

	    return cfg.buildSessionFactory();
	  }

	  private Configuration addDirectory(final Configuration configuration,
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
	  public File getHibernateDirectory() {
	    return new File("hibernate");
	  }

	  /**
	   * Returns Hibernate properties file.
	   *
	   * @return Hibernate properties file.
	   */
	  public File getHibernatePropertiesFile() {
	    return new File(getHibernateDirectory(), "hibernate.properties");
	  }
	
	/**
	 * Loads a sample document from the given file.
	 * 
	 * @param file
	 *            sample file.
	 * @return Sample document.
	 * @throws SAXException
	 *             In case of parsing problems.
	 * @throws IOException
	 *             In case of I/O problems
	 */
	public Document getSampleDocument(final File file) throws SAXException,
			IOException {
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			final Document document = db.parse(file);
			clearWhitespace(document);
			
			return document;
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * Clears whitespace text nodes from the node.
	 * 
	 * @param node
	 *            a node to clear whitespace from.
	 */
	public void clearWhitespace(final Node node) {
		for (Node current = node.getLastChild(); current != null;) {
			final Node next = current.getPreviousSibling();
			if (current.getNodeType() == Node.TEXT_NODE) {
				final String nodeValue = current.getNodeValue();
				if (nodeValue.matches("\\s*")) {
					node.removeChild(current);
				}
			} else if (current.getNodeType() == Node.ELEMENT_NODE) {
				clearWhitespace(current);
			}
			current = next;
		}
	}
	
	/**
	 * Returns sample class used in the document.
	 * 
	 * @param document
	 *            document.
	 * @return Sample class.
	 * @throws ClassNotFoundException
	 *             In case spcified class could not be found.
	 */
	public Class getSampleClass(final Document document)
			throws ClassNotFoundException {
		final String className = document.getFirstChild().getNodeValue();
		if (className == null) {
			throw new ClassNotFoundException(
					"Object class is not specified in the sample."
							+ " Please include <?class my.class.name?> in the document prolog.");
		}
		return Class.forName(className);
	}
	
	/**
	 * Document builder.
	 */
	protected DocumentBuilder documentBuilder;
	
	/**
	 * Marshaller.
	 */
	protected Marshaller marshaller;

	/**
	 * Unmarshaller.
	 */
	protected Unmarshaller unmarshaller;
	
	/**
	 * Session Factory
	 */
	protected SessionFactory sessionFactory;
}
