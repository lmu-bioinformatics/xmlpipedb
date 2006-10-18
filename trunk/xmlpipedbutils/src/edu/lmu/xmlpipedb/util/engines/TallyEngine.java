package edu.lmu.xmlpipedb.util.engines;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.impl.SimpleLog;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.util.engines.ImportEngine.EndOfRecordRule;

public class TallyEngine {

	public TallyEngine(HashMap<String, Criterion> criteria) {
		_criteria = criteria;
	}
	
	public HashMap getXmlFileCounts(InputStream xmlFile){
		digestXmlFile(xmlFile);
		return _criteria;
	}
	
	public HashMap getDbCounts(){
		
		return _criteria;
	}

	public void setCriteria( HashMap<String, Criterion> criteria ){
		_criteria = criteria;
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
    	//Look Ma, I'm actually logging stuff (albeit not pretty)
		SimpleLog logger = new SimpleLog("ImportLogger");
		logger.setLevel(SimpleLog.LOG_LEVEL_ERROR);

    	Digester digester = new Digester();
		try {
			Set set = _criteria.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				Criterion crit = _criteria.get(iter.next());
				digester.addRule(crit.getDigesterPath(), new EndOfRecordRule());
			}
			digester.setValidating(false);
			digester.setLogger(logger);
			digester.parse(xml);
			

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
    protected class EndOfRecordRule extends Rule{

    	public void end(String namespace, String name){
    		Digester tempDig = getDigester();
    		Criterion tempCrit = _criteria.get(tempDig.getMatch());
    		tempCrit.setCount(tempCrit.getCount() + 1 );
    	}
    }
    

	
	// CLASS MEMBERS
	HashMap<String, Criterion> _criteria;
	
} // end class
