/*
 * ImportEngine.java
 *
 * Created on March 15, 2006, 9:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
 * 
 * @author David Hoffman
 */
public class ImportEngine {
    /** Creates a new instance of ImportEngine */
    public ImportEngine(String jaxbContextPath, Configuration hibernateConfiguration) throws JAXBException, SAXException, IOException, HibernateException {
        jaxbContext = JAXBContext.newInstance(jaxbContextPath);
        unmarshaller = jaxbContext.createUnmarshaller();
        sessionFactory = hibernateConfiguration.buildSessionFactory();
    }

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

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private Unmarshaller unmarshaller;
    private JAXBContext jaxbContext;
    private SessionFactory sessionFactory;
}
