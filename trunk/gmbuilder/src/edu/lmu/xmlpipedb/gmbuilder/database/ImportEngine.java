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

package edu.lmu.xmlpipedb.gmbuilder.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

/**
 *
 * @author David Hoffman
 */
public class ImportEngine {
    private Unmarshaller unmarshaller;
    private JAXBContext jaxbContext;
    private SessionFactory sessionFactory;
    private Configuration hibernateConfiguration;

    /** Creates a new instance of ImportEngine */
    public ImportEngine(String contextPath, String hibernateConfig, String hibernateProp) throws JAXBException, SAXException, IOException, HibernateException {
        jaxbContext = JAXBContext.newInstance(contextPath);
        unmarshaller = jaxbContext.createUnmarshaller();
        setHibernateConfig(hibernateConfig, hibernateProp);
    }

    private void setHibernateConfig(String hibernateConfig, String hibernateProp) throws IOException, HibernateException {
        hibernateConfiguration = new Configuration();
        hibernateConfiguration.addDirectory(new File(hibernateConfig));
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(new FileInputStream(hibernateProp));
        hibernateConfiguration.setProperties(hibernateProperties);
        sessionFactory = hibernateConfiguration.buildSessionFactory();

    }

    public void loadToDB(File xmlFile) throws Exception {
        Object object = unmarshaller.unmarshal(xmlFile);
        Session saveSession = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = saveSession.beginTransaction();
            saveSession.saveOrUpdate(object);
            transaction.commit();
        } catch(Exception ex) {
            if (transaction != null)
                transaction.rollback();
            throw ex;
        } finally {
            saveSession.close();
        }
    }
    
    public SessionFactory getSessionFactory(){	return sessionFactory;	}
    
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
