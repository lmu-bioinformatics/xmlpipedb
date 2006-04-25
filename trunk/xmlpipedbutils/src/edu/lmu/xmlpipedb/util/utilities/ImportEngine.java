/*
 * ImportEngine.java
 *
 * Created on March 15, 2006, 9:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.lmu.xmlpipedb.util.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public ImportEngine(String jaxbContextPath, Configuration hibernateConfiguration) throws JAXBException, SAXException, IOException, HibernateException {
        jaxbContext = JAXBContext.newInstance(jaxbContextPath);
        unmarshaller = jaxbContext.createUnmarshaller();
        //setHibernateConfig(hibernateMappingDirectory, hibernatePropertiesFileName);
        this.hibernateConfiguration = hibernateConfiguration; 
        sessionFactory = hibernateConfiguration.buildSessionFactory();
    }

    /*private void setHibernateConfig(String hibernateMappingDirectory, String hibernatePropertiesFileName) throws IOException, HibernateException {
        hibernateConfiguration = new Configuration();
        hibernateConfiguration.addJar(new File(hibernateMappingDirectory));
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(new FileInputStream(hibernatePropertiesFileName));
        hibernateConfiguration.setProperties(hibernateProperties);
        sessionFactory = hibernateConfiguration.buildSessionFactory();

    }*/

    /*public void loadToDB(File xmlFile) throws Exception {
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
    }*/
    public void loadToDB(InputStream xml) throws Exception {
        Object object = unmarshaller.unmarshal(xml);
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
}
