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
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.NodeCreateRule;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.impl.SimpleLog;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.dom.DocumentTypeImpl;

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
        _topLevelElement = "";
    }
    
    /**
     * @deprecated
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
    public ImportEngine(String jaxbContextPath, Configuration hibernateConfiguration, String topLevelElement) throws JAXBException, SAXException, IOException, HibernateException {
        jaxbContext = JAXBContext.newInstance(jaxbContextPath);
        unmarshaller = jaxbContext.createUnmarshaller();
        sessionFactory = hibernateConfiguration.buildSessionFactory();
        _topLevelElement = topLevelElement;
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
        saveEntry(xml);
    	
//		#2 Extra Crispy
//        digestXmlFile(xml);
        
        
        /*
         * The following code was moved down to the method saveEntry.
         */
//    	Object object = unmarshaller.unmarshal(xml);
//        Session saveSession = sessionFactory.openSession();
//        Transaction transaction = null;
//        try {
//            transaction = saveSession.beginTransaction();
//            saveSession.saveOrUpdate(object);
//            transaction.commit();
//        } catch(Exception ex) {
//            if (transaction != null)
//                transaction.rollback();
//            throw ex;
//        } finally {
//            saveSession.close();
//        }
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
    	
    	//Look Ma, I'm actually logging stuff (albeit not pretty)
		SimpleLog logger = new SimpleLog("ImportLogger");
		logger.setLevel(SimpleLog.LOG_LEVEL_DEBUG);

    	
    	Digester digester = new Digester();
//    	Digester dig2 = new Digester();
//    	NodeCreateRule topNcRule; 
//    	NodeCreateRule ncRule;
		try {
//			topNcRule = new NodeCreateRule();
//			ncRule = new NodeCreateRule();
			
			
			//FIXME: IF this works, use a variable here
			//digester.addRule("bookstore", topNcRule);
//			digester.addRule(_topLevelElement, ncRule);
			// NOTE-- this did not work out for me.
			//digester.addRule(_topLevelElement, new ObjectCreateRule("java.lang.StringBuffer"));
//			digester.addRule("bookstore", new TopEndOfRecordRule());
			digester.addRule(_topLevelElement, new EndOfRecordRule());
			digester.setValidating(false);
			digester.setLogger(logger);
			digester.parse(xml);
			
//			dig2.addRule(_topLevelElement, ncRule);
//			dig2.addRule(_topLevelElement, new EndOfRecordRule());
//			dig2.setValidating(false);
//			dig2.setLogger(logger);
//			dig2.parse(xml);

			
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	
    }
    
    /**
     * When end of the an element is reached, a custom, EndOfRecordRule
     * will be fired, which pops this Node from the stack as an Element
     * and passes it to the saveEntry method, which uses JAXB and Hibernate
     * to save the contents to the database.
     * 
     * @author Jeffrey Nicholas
     *
     */
    protected class TopEndOfRecordRule extends Rule{
    	public void end(String namespace, String name){
    		System.out.println("\nTopEndOfRecordRule.end()");
    		Digester mydigester = this.getDigester();
			_top = (Element) mydigester.pop();
			//saveEntry(_top);
    	}
    }
    
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
     * The following 2 methods (begin and body) were part of my aborted
     * attempt to create my own Object, rather than what the CreateNodeRule
     * gave me.
     */
    	
/*    	public void begin(String namespace,String name, Attributes attributes){
    		Digester myDigester = this.getDigester();
    		StringBuffer sb = new StringBuffer("");
    		sb.append(name);
    		sb.append(attributes.toString());
    		myDigester.push(sb);
    	}
    	
    	public void body(String namespace, String name, String text){
    		Digester myDigester = this.getDigester();
    		StringBuffer sb = (StringBuffer)myDigester.pop();
    		sb.append(name);
    		sb.append(text);
    		myDigester.push(sb);
    	}*/
    	
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
    		Digester tempDig = getDigester();
    		System.out.println("\n" + tempDig.getMatch() + " Record #: " + _recordCount);
//    		Digester mydigester = this.getDigester();
    		// this is not really needed, but you can only pop once, so
    		// this way, the results will be available below, if imanutjob
//    		Object o = mydigester.pop();
//    		org.w3c.dom.Element elem = (org.w3c.dom.Element) o;
//    		saveEntry(elem);
    		
    		/*
    		 * my little way of controlling whether I go into this code or not
    		 * :)
    		 */
//    		boolean imanutjob = false;
//    		
//    		if(imanutjob == true){
//    			Object o = null;
//    			//if(!mydigester.isEmpty(""))
//    			o = mydigester.pop();
//        		org.w3c.dom.Element elem = (org.w3c.dom.Element) o;
//        		 
//        		_top.appendChild(elem);
//    			saveEntry(elem);
//    			
//    			
//    			
//    			if(true) return;
//    			
//    			
//    			DocumentTypeImpl dti = new DocumentTypeImpl(null, "bookstore");
//	    		DocumentImpl doc;
	//    		DocumentBuilderFactory factory =
	//                DocumentBuilderFactory.newInstance();
	            //factory.setValidating(true);   
	            //factory.setNamespaceAware(true);
	            //try {
	              // DocumentBuilder builder = factory.newDocumentBuilder();
	             //  doc = builder.newDocument();
	             //  doc.appendChild((Element)mydigester.pop());
//	              doc = new DocumentImpl( dti );
	//              DefaultDocumentType ddt = new DefaultDocumentType();
	//              ddt.setElementName("bookstore");
	//              doc.setsetDocType(ddt);
	            //  Node n = (Node)mydigester.pop();
	             // doc.appendChild(n);
	
//	              org.w3c.dom.Element elem2 = doc.createElement(null);
//	              elem2 = (org.w3c.dom.Element)o;
//	              
	              //doc.addEventListener((Element)o);
	             
	               // should this be a documentroot object???
	               //   or some other special DOM element
	               // root = (Element) doc.createElement("bookstore"); 
	               //doc.appendChild(root);
	               //Element elem = (Element)mydigester.pop();
	               
	               //root.appendChild( elem );
	          // root.appendChild( document.createTextNode(" ")    );
	           //root.appendChild( document.createTextNode("text") );
	               
	              // doc.createElement("bookstore");
	              // Element topElem = doc.getElement("bookstore");
	          
	               //topElem.appendChild((Element)mydigester.pop());
	               //doc = builder.parse( "" );
	     
	              /* } catch (SAXException sxe) {
	               // Error generated during parsing)
	               Exception  x = sxe;
	               if (sxe.getException() != null)
	                   x = sxe.getException();
	               x.printStackTrace();
	
	            } catch (ParserConfigurationException pce) {
	                // Parser with specified options can't be built
	                pce.printStackTrace();
	
	            }*/ /*catch (IOException ioe) {
	               // I/O error
	               ioe.printStackTrace();
	            }*/
	    		
	    		//DOMDocument doc = new DOMDocument("bookstore");
	    		//doc.add((Element)mydigester.pop());
	    		//Element elem = (Element)mydigester.pop();
	    		
	    		//saveEntry(doc);
//    		}
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
    private String _topLevelElement; // this is used to capture each top level record in the xml file
	private int _recordCount = 0;
	private Element _top = null;
}
