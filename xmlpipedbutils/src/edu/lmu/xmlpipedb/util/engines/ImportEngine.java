/*
 * ImportEngine.java
 *
 * Created on March 15, 2006, 9:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * This class was written by David Hoffman
 */

package edu.lmu.xmlpipedb.util.engines;

import java.io.IOException;
import java.io.InputStream;

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
 * This class imports an xml file into a database.
 * @author David Hoffman
 */
public class ImportEngine {
    /**
     * Creates a new instance of ImportEngine
     * @param jaxbContextPath The jaxbContext path
     * @param hibernateConfiguration A hibernate configuration as the configuration to import
     * the xml to the database
     * @throws javax.xml.bind.JAXBException For JaxB Exceptions
     * @throws org.xml.sax.SAXException for SAX Exceptions
     * @throws java.io.IOException for IO exceptions
     * @throws org.hibernate.HibernateException all hibernate exceptions
     */
    public ImportEngine(String jaxbContextPath, Configuration hibernateConfiguration) throws JAXBException, SAXException, IOException, HibernateException {
        jaxbContext = JAXBContext.newInstance(jaxbContextPath);
        unmarshaller = jaxbContext.createUnmarshaller();
        sessionFactory = hibernateConfiguration.buildSessionFactory();
    }

    /**
     * This function call takes the xml off the input stream and loads it to the database
     * @param xml expecting xml on the input stream
     * @throws java.lang.Exception 
     */
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

    /**
     * Within the demo app this method was made public.
     * @return The session factory in case someone needs it.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private Unmarshaller unmarshaller;
    private JAXBContext jaxbContext;
    private SessionFactory sessionFactory;
}
