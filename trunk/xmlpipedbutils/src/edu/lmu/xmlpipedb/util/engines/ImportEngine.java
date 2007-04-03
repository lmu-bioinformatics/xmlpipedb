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
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import nu.xom.converters.DOMConverter;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.NodeCreateRule;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;


/**
 * This class imports an xml file into a database.
 * @author David Hoffman, Jeffrey Nicholas
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
     * Creates a new instance of ImportEngine
     * @param jaxbContextPath The jaxbContext path
     * @param hibernateConfiguration A hibernate configuration as the configuration to import
     * the xml to the database
     * @param topLevelElement Used to parse each top level record from the XML input file. 
     * For a uniprot XML file, this would normally be "uniprot/entry"
     * @throws javax.xml.bind.JAXBException For JaxB Exceptions
     * @throws org.xml.sax.SAXException for SAX Exceptions
     * @throws java.io.IOException for IO exceptions
     * @throws org.hibernate.HibernateException all hibernate exceptions
     */
    public ImportEngine(String jaxbContextPath, Configuration hibernateConfiguration, String entryElement, HashMap<String, String> rootElementName) throws JAXBException, SAXException, IOException, HibernateException {
        jaxbContext = JAXBContext.newInstance(jaxbContextPath);
        unmarshaller = jaxbContext.createUnmarshaller();
        sessionFactory = hibernateConfiguration.buildSessionFactory();
        _rootElementName = rootElementName;  // e.g. Name = "bookstore";
        _entryElement = entryElement;	// e.g.  "bookstore/book"
    }

    /**
     * This function call takes the xml off the input stream and loads it to the database
     * @param xml expecting xml on the input stream
     * @throws java.lang.Exception 
     */
    public void loadToDB(InputStream xml) throws Exception {
        /*
         * This method used to hold the block of code that is commented below.
         * As noted, this has been moved to a method called saveEntry.
         * I've created two version of saveEntry. 
         * 
         * (1) takes an InputStream
         * and works just like the code below did. This allows backward 
         * compatibility and allows this code to be checked in while still
         * in development. To use this path, leave "//#1 Original" uncommented,
         * below.
         * 
         * (2) takes an Element object. This actually also works the same way
         * as the previous code, but simply uses a different unmarshall method
         * (one that takes a DOM Element rather than an InputStream. To use
         * this path, leave "//#2 Extra Crispy" uncommented, below.
         * 
         * OBVIOUSLY, the path not being used must be commented.
         * 
         * 
         */
    	
//    	#1 Original
//        saveEntry(xml);
    	
//		#2 Extra Crispy
        digestXmlFile(xml);
        
    }
    
    /**
     * Simple getter method to return the record count. The record count is
     * stored as a integer.
     * @return The record count as an integer
     */
    public int getRecordCount(){
    	return _recordCount;
    }
    
    /**
     * Uses Jakarta Digester to handle xml parsing to DOM objects
     * so we have greater control over the import process. The rules
     * cause the following:
     * 1. Each topLevelElement, based on what value is passed in to the class
     * when it is instantiated, will be pushed onto the Digester stack as a 
     * DOM Node.
     * 2. When end of the an element is reached, a custom, EndOfRecordRule
     * will be fired, which pops this Node from the stack as an Element
     * and passes it to the saveEntry method, which uses JAXB and Hibernate
     * to save the contents to the database.
     */
    private void digestXmlFile(InputStream xml){
    	/*	NOTES: General Game Plan		 
    	 * The code in this method is pretty much done, unless we come up
    	 * with a novel way of using the rules to do what we want. All the
    	 * trouble is in the processing Required by the EndOfRecordRule, below.
    	 * 
    	 * SEE ADDITIONAL NOTES IN EndOfRecordRule
    	 * 
    	 * add a node create rule to get the top element "bookstore"
    	 * add a start of record rule to pop this and create a doc from it
    	 * Then keep this in the class and add each record to it, as needed
    	 * OR think of a better way to do this
    	 * 
    	 * 
    	*/			
    	
    	Digester dig2 = new Digester();
    	NodeCreateRule ncRule;
    	
		try {
			ncRule = new NodeCreateRule();

			dig2.addRule(_entryElement, ncRule);
			dig2.addRule(_entryElement, new EndOfRecordRule());
			dig2.setValidating(false);
			dig2.setLogger(_Log);
			dig2.parse(xml);

			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	
    }// end digestXmlFile
    
    /**
     * When end of the an element is reached, a custom, EndOfRecordRule
     * will be fired, which pops this Node from the stack as an Element
     * and passes it to the saveEntry method, which uses JAXB and Hibernate
     * to save the contents to the database.
     * 
     * @author Jeffrey Nicholas
     *
     */
    protected class EndOfRecordRule extends Rule{
     	/*
    	 * This is where all the trouble lies.
    	 * 
    	 * The top of the method has the clean version of the code. Comment
    	 * bits and pieces for experimentation. The bottom is all the various
    	 * experiments I've tried. The ultimate problem is that DOM is a 
    	 * terrible mess.
    	 * 
    	 * My next step is to look into an XML API recommended to me by a
    	 * source, whom I shall only name Mr. Y. (Mr. X has been over used).
    	 * Mr. Y. recommended checking into XOM (http://www.xom.nu/). I am
    	 * doing that presently, but wanted to check this in first.
    	 */
    	public void end(String namespace, String name){
    		_recordCount++;
    		System.out.println("\n Record #: " + _recordCount);
    		
    		Digester mydigester = this.getDigester();
    			
    		Object o = mydigester.pop();

    		
    		/*_root = new nu.xom.Element(_rootElementName.get("rootname"), _rootElementName.get("xmlns"));
    		Iterator iter = _rootElementName.keySet().iterator();
    		while(iter.hasNext()){
    			String attrName = (String) iter.next();
    			// rootname is not an attribute, so skip it
    			if( attrName.equalsIgnoreCase("rootname") )
    				continue;
    			
    			_root.addAttribute(new nu.xom.Attribute(attrName, _rootElementName.get(attrName)));
    		}*/

    		org.w3c.dom.Element elem = (org.w3c.dom.Element) o;
    		nu.xom.Element e = DOMConverter.convert(elem);
    		_Log.info(e.toXML());
    		elements += e.toXML();
    		
    		

    		/*_root.appendChild(e);
    		nu.xom.Document doc = new nu.xom.Document(_root);
    		_Log.info(doc.toXML());*/
    		
    		if( _recordCount%25 == 0 ){
	    		String doc = _rootElementName.get("head") + elements + _rootElementName.get("tail");
	    		//saveEntry(new StringBufferInputStream(doc.toXML()));
	    		elements = "";
	    		saveEntry(new StringBufferInputStream(doc));
    		
    		}
    		
    		// This worked really well, until the unmarshaller threw a series of
    		//"parse may not be called while parsing" errors -- oh well.
    		//new SimpleThread(new StringBufferInputStream(doc)).start();
 
    	}
    }
    
    /**
     * Saves the element passed in to the database using JAXB and Hibernate.
     * 
     * @param elem
     */
    private void saveEntry(org.w3c.dom.Node node){
    	Transaction transaction = null;
    	Session saveSession = null;
    	
    	try {
    		/*
    		 * The Element passed in must be complete. I.e. it must be:
    		 * bookstore/book OR uniprot/entry
    		 * 
    		 * BTW: I have done the experiment if I create my rule to
    		 * look for only "bookstore" then the Element created will
    		 * unmarshal correctly! :D -- always good to know, right
    		 */
    		Object object = unmarshaller.unmarshal(node);
    		saveSession = sessionFactory.openSession();
            transaction = saveSession.beginTransaction();
            saveSession.saveOrUpdate(object);
            transaction.commit();
        } catch(Exception ex) {
            if (transaction != null)
                transaction.rollback();
            try {
				throw ex;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } finally {
            saveSession.close();
        }
    } // end saveEntry
    
    /**
     * Saves the input stream passed in to the database using JAXB and Hibernate.
     * 
     * @param xml An InputStream object that will be passed to the 
     * JAXB Unmarshaller's unmarshall method
     */
    private void saveEntry(InputStream xml){
    	Transaction transaction = null;
    	Session saveSession = null;
    	
    	try {
    		Object object = unmarshaller.unmarshal(xml);
    		saveSession = sessionFactory.openSession();
            transaction = saveSession.beginTransaction();
            saveSession.saveOrUpdate(object);
            transaction.commit();
        } catch(Exception ex) {
            if (transaction != null)
                transaction.rollback();
            try {
				throw ex;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } finally {
            saveSession.close();
        }
    } // end saveEntry
    
    
    class SimpleThread extends Thread {
        public SimpleThread(InputStream xml) {
            _xml = xml;
        }
        public void run() {
        	Transaction transaction = null;
        	Session saveSession = null;
        	
        	try {
        		Object object = unmarshaller.unmarshal(_xml);
        		saveSession = sessionFactory.openSession();
                transaction = saveSession.beginTransaction();
                saveSession.saveOrUpdate(object);
                transaction.commit();
            } catch(Exception ex) {
                if (transaction != null)
                    transaction.rollback();
                try {
    				throw ex;
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            } finally {
                saveSession.close();
            }
        }
        InputStream _xml;
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
    private Map<String, String> _rootElementName; // this is used to capture each top level record in the xml file
    private String _entryElement; // this holds the "uniprot/uniprot/entry" tag -- for bookstore example, it holds "bookstore/book"
    private int _recordCount = 0;
    nu.xom.Element _root;
    private String elements = "";
	private static Log _Log = LogFactory.getLog(TallyEngine.class);
}
