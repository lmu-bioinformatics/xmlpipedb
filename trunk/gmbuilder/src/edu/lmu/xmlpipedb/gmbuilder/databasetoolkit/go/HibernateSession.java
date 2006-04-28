

/*
 * ImportEngine.java
 *
 * Created on March 15, 2006, 9:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;


import generated.Acc;
import generated.impl.AccImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;
import org.hibernate.Query;
import org.hibernate.dialect.Dialect;



/**
 *
 * @author Scott Spicer
 */
public class HibernateSession {
    private Unmarshaller unmarshaller;
    private JAXBContext jaxbContext;
    private SessionFactory sessionFactory;
    private Configuration hibernateConfiguration;
    private Session session;
    
    /**
     *  Crete a hibernate session
     * 
     * @param hibernateConfig
     * @param hibernateProp
     * @throws SAXException
     * @throws IOException
     * @throws HibernateException
     * @throws JAXBException
     */
    public HibernateSession(String hibernateConfig, String hibernateProp) throws SAXException, IOException, HibernateException, JAXBException {
        setHibernateConfig(hibernateConfig, hibernateProp);
    }
    
    /**
     * Opens a hibernate session
     * 
     * @return
     * 		hibernate session
     */
    public Session openSession() {
    	return sessionFactory.openSession();
    }
    
    /**
     * Defines hibernate configuration settings
     * 
     * @param hibernateConfig
     * @param hibernateProp
     * @throws IOException
     * @throws HibernateException
     */
    private void setHibernateConfig(String hibernateConfig, String hibernateProp) throws IOException, HibernateException {
        hibernateConfiguration = new Configuration();
        hibernateConfiguration.addDirectory(new File(hibernateConfig));
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(new FileInputStream(hibernateProp));
        hibernateConfiguration.setProperties(hibernateProperties);
        sessionFactory = hibernateConfiguration.buildSessionFactory();
    }
    

}





