

/*
 * ImportEngine.java
 *
 * Created on March 15, 2006, 9:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;


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
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;
import edu.lmu.xmlpipedb.gmbuilder.GenMAPPBuilder;



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
    public HibernateSession() throws SAXException, IOException, HibernateException, JAXBException {
    	hibernateConfiguration 	= GenMAPPBuilder.createHibernateConfiguration();
    	sessionFactory 			= hibernateConfiguration.buildSessionFactory();
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

}





