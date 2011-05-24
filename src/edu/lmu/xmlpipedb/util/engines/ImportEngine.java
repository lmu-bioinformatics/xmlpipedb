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
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
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
 * 
 * @author David Hoffman, Jeffrey Nicholas
 */
public class ImportEngine {

    /**
     * Creates a new instance of ImportEngine
     * 
     * @param jaxbContextPath
     *            The jaxbContext path
     * @param hibernateConfiguration
     *            A hibernate configuration as the configuration to import the
     *            xml to the database
     * @throws javax.xml.bind.JAXBException
     *             For JaxB Exceptions
     * @throws org.xml.sax.SAXException
     *             for SAX Exceptions
     * @throws java.io.IOException
     *             for IO exceptions
     * @throws org.hibernate.HibernateException
     *             all hibernate exceptions
     */
    public ImportEngine(String jaxbContextPath, Configuration hibernateConfiguration) throws JAXBException, SAXException, IOException, HibernateException {
        jaxbContext = JAXBContext.newInstance(jaxbContextPath);
        unmarshaller = jaxbContext.createUnmarshaller();
        sessionFactory = hibernateConfiguration.buildSessionFactory();

        // Test the configuration by trying a transaction.
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        tx.rollback();
        session.close();
    }

    /**
     * Creates a new instance of ImportEngine for use with high-performance JAXB
     * processing. High-performance processing is stongly recommended files over
     * 1MB and required for large files (somewhere over 35MB). But processing
     * anything over 1MB with the normal constructor is simply foolish!
     * 
     * @param jaxbContextPath
     *            The jaxbContext path
     * @param hibernateConfiguration
     *            A hibernate configuration as the configuration to import the
     *            xml to the database
     * @param entryElement
     *            Used to parse each record from the XML input file. For a
     *            uniprot XML file, this would normally be "uniprot/entry"
     * @param rootElementName
     *            Map<String, String> containing a "head" and a "tail". This is
     *            used to surround the extracted record for processing The
     *            "head" must have the complete beginning tag (inclusive all
     *            namespace delcarations, attributes, etc.), e.g.: <bookstore
     *            xlsns:http://mybookstore.org/bookstore> The "tail" need only
     *            have the correct closing tag, e.g.: </bookstore>
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     * @throws HibernateException
     */
    public ImportEngine(String jaxbContextPath, Configuration hibernateConfiguration, String entryElement, Map<String, String> rootElementName) throws JAXBException, SAXException, IOException, HibernateException {
        this(jaxbContextPath, hibernateConfiguration);
        _rootElementName = rootElementName; // e.g. Name = "bookstore";
        _entryElement = entryElement; // e.g. "bookstore/book"
    }

    /**
     * This function call takes the xml off the input stream and loads it to the
     * database
     * 
     * @param xml
     *            expecting xml on the input stream
     * @throws java.lang.Exception
     */
    public void loadToDB(InputStream xml) throws Exception {

        // #1 Original
        if (_rootElementName == null) {
            saveEntry(new InputStreamReader(xml));
        } else {
            // #2 Extra Crispy
            digestXmlFile(xml);
        }

    }

    /**
     * Simple getter method to return the record count. The record count is
     * stored as a integer.
     * 
     * @return The record count as an integer
     */
    public int getRecordCount() {
        return _recordCount;
    }

    /**
     * Uses Jakarta Digester to handle xml parsing to DOM objects so we have
     * greater control over the import process. The rules cause the following:
     * 1. Each topLevelElement, based on what value is passed in to the class
     * when it is instantiated, will be pushed onto the Digester stack as a DOM
     * Node. 2. When end of the an element is reached, a custom, EndOfRecordRule
     * will be fired, which pops this Node from the stack as an Element and
     * passes it to the saveEntry method, which uses JAXB and Hibernate to save
     * the contents to the database.
     */
    private void digestXmlFile(InputStream xml) {
        Digester dig2 = new Digester();
        NodeCreateRule ncRule;

        try {
            // create a standard rule
            ncRule = new NodeCreateRule();
            // pass the rule from above and the entryElement, e.g.
            // bookstore/book or uniprot/entry
            dig2.addRule(_entryElement, ncRule);
            // pass the _entryElement again and an EndOfRecordRule, which is my
            // own
            // custom rule that fires before the node created by the first rule
            // is
            // popped off the stack. The rules must be added in this order,
            // since they
            // are processed in the order added and this step must come after
            // the
            // preceeding step.
            dig2.addRule(_entryElement, new EndOfRecordRule());
            dig2.setValidating(false);
            dig2.setLogger(_Log);
            dig2.parse(xml);

            // now that digesting is finished, we just need to be sure that
            // all records have been saved. So, we'll check _elements and
            // call the save function if need be.
            if (_elements.length() != 0) {
                saveEntry(new StringReader(prepForSaving()));// StringBufferInputStream(prepForSaving()));
                _Log.warn("\n Final Record #: " + _recordCount);
            }

        } catch(ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }// end digestXmlFile

    /**
     * When end of the an element is reached, a custom, EndOfRecordRule will be
     * fired, which pops this Node from the stack as an Element and passes it to
     * the saveEntry method, which uses JAXB and Hibernate to save the contents
     * to the database.
     * 
     * @author Jeffrey Nicholas
     * 
     */
    protected class EndOfRecordRule extends Rule {
        public void end(String namespace, String name) {
            _recordCount++;
            _Log.info("\n Record #: " + _recordCount);

            Digester mydigester = this.getDigester();

            Object o = mydigester.pop();
            mydigester.push(o);

            /*
             * _root = new nu.xom.Element(_rootElementName.get("rootname"),
             * _rootElementName.get("xmlns")); Iterator iter =
             * _rootElementName.keySet().iterator(); while(iter.hasNext()){
             * String attrName = (String) iter.next(); // rootname is not an
             * attribute, so skip it if( attrName.equalsIgnoreCase("rootname") )
             * continue;
             * 
             * _root.addAttribute(new nu.xom.Attribute(attrName,
             * _rootElementName.get(attrName))); }
             */

            org.w3c.dom.Element elem = (org.w3c.dom.Element)o;
            nu.xom.Element e = DOMConverter.convert(elem);
            _Log.info(e.toXML());
            _elements += e.toXML();

            /*
             * _root.appendChild(e); nu.xom.Document doc = new
             * nu.xom.Document(_root); _Log.info(doc.toXML());
             */

            // Saves every 25 records
            if (_recordCount % 25 == 0) {
                saveEntry(new StringReader(prepForSaving()));
                _Log.warn("\n Record #: " + _recordCount);
            }

            // This worked really well, until the unmarshaller threw a series of
            // "parse may not be called while parsing" errors -- oh well.
            // new SimpleThread(new StringBufferInputStream(doc)).start();

        }
    }

    /**
     * Saves the input stream passed in to the database using JAXB and
     * Hibernate.
     * 
     * @param xml
     *            An InputStream object that will be passed to the JAXB
     *            Unmarshaller's unmarshall method
     */
    private void saveEntry(Reader xml) {
        Transaction transaction = null;
        Session saveSession = null;

        try {
            Object object = unmarshaller.unmarshal(xml);
            saveSession = sessionFactory.openSession();
            transaction = saveSession.beginTransaction();
            saveSession.saveOrUpdate(object);
            transaction.commit();
        } catch(Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            try {
                throw ex;
            } catch(Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            saveSession.close();
        }
    } // end saveEntry

    /**
     * Gets the head and tail from the root element and wraps the _elements with
     * it. Then clears the _elements for later use.
     */
    private String prepForSaving() {
        String doc = _rootElementName.get("head") + _elements + _rootElementName.get("tail");
        // saveEntry(new StringBufferInputStream(doc.toXML()));
        _elements = "";
        return doc;
    }

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
                } catch(Exception e) {
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
     * 
     * @return The session factory in case someone needs it.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private Unmarshaller unmarshaller;
    private JAXBContext jaxbContext;
    private SessionFactory sessionFactory;
    private Map<String, String> _rootElementName = null; // this is used to
                                                         // capture each top
                                                         // level record in the
                                                         // xml file
    private String _entryElement = null; // this holds the
                                         // "uniprot/uniprot/entry" tag -- for
                                         // bookstore example, it holds
                                         // "bookstore/book"
    private int _recordCount = 0;
    nu.xom.Element _root;
    private String _elements = "";
    private static Log _Log = LogFactory.getLog(ImportEngine.class);
}
