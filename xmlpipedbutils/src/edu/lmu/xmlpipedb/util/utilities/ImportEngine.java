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
import java.util.Properties;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
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
    public ImportEngine(String contextPath, String hibernateConfig, String hibernateProp) throws JAXBException, SAXException, IOException{
        jaxbContext = JAXBContext.newInstance(contextPath);
        unmarshaller = jaxbContext.createUnmarshaller(); 
        setHibernateConfig(hibernateConfig, hibernateProp); 
        
        
    }
    private void setHibernateConfig(String hibernateConfig, String hibernateProp) throws IOException
    {
        hibernateConfiguration = new Configuration();
        hibernateConfiguration.addDirectory(new File(hibernateConfig)); 
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(new FileInputStream(hibernateProp)); 
        hibernateConfiguration.setProperties(hibernateProperties); 
        sessionFactory = hibernateConfiguration.buildSessionFactory(); 
        
    }
    public void loadToDB(File xmlFile)throws Exception
    {
        Object object = unmarshaller.unmarshal(xmlFile); 
        Session saveSession = sessionFactory.openSession();
        Transaction transaction = null; 
        try{
        transaction = saveSession.beginTransaction(); 
        saveSession.saveOrUpdate(object); 
        transaction.commit(); 
        }
        catch(Exception ex)
        {
            if(transaction!=null)
                transaction.rollback();
            throw ex; 
        }
        finally
        {
            saveSession.close(); 
        }
    }
   
    
}
