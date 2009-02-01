package edu.lmu.xmlpipedb.util.engines;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.SimpleLog;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.util.exceptions.HibernateQueryException;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;
import edu.lmu.xmlpipedb.util.exceptions.XpdException;

public class TallyEngine {

	public TallyEngine(HashMap<String, Criterion> criteria) {
		_criteria = criteria;
	}

	public Map<String, Criterion> getXmlFileCounts(InputStream xmlFile)
			throws XpdException {
		// validate params
		if (xmlFile == null) {
			// TODO: Log exception
			throw new InvalidParameterException(
					"The InputStream passed must not be null.");
		}
		
		digestXmlFile(xmlFile);
		return _criteria;
	}

	public void getDbCounts(QueryEngine qe) throws XpdException {
		// validate params
		if (qe == null)
			throw new InvalidParameterException(
					"The QueryEngine passed must not be null.");

		// HashMap<String, Criterion> retMap = new HashMap<String, Criterion>();
		// setup query engine
		Connection conn = qe.currentSession().connection();
		PreparedStatement query = null;
		ResultSet results = null;

		Set set = _criteria.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Criterion crit = _criteria.get(iter.next());
			// check if there is a valid table entry, if not go on to the next
			// record
			if (crit.getQuery() == null || crit.getQuery().equals("")) {
				continue;
			}
			try {
				query = conn.prepareStatement(crit.getQuery());
				results = query.executeQuery();

				results.next();
				crit.setDbCount(results.getInt("count"));
				// retMap.put(crit.getDigesterPath(), crit);

			} catch (SQLException sqle) {
				// TODO: Log exception
				// came from HQLPanel -- probably not needed here
				// qe.currentSession().reconnect();
				throw new HibernateQueryException(sqle.getMessage());
				// Need to clean up connection after SQL exceptions
			} catch (Exception e) {
				// TODO: Log exception
				throw new XpdException(e.getMessage());
			} finally {
				try {
					results.close();
					query.close();

					// We need to be sure to NOT close the connection or the
					// session here. Leave it open!
				} catch (Exception e) {
					// TODO: Log exception

				} // Ignore the errors here, nothing we can do anyways.
			}
		}

		// return retMap;
	}

	public void setCriteria(Map<String, Criterion> criteria) {
		_criteria.putAll(criteria);
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
		// Look Ma, I'm actually logging stuff (albeit not pretty)
		SimpleLog logger = new SimpleLog("TallyEngineLogger");
		logger.setLevel(SimpleLog.LOG_LEVEL_ERROR);
		
		Digester digester = new Digester();
		try {
			Set set = _criteria.keySet();
			Iterator iter = set.iterator();
			while (iter.hasNext()) {
				Criterion crit = _criteria.get(iter.next());
				digester.addRule(crit.getDigesterPath(), new EndOfRecordRule());
				// initialize the count to 0, since I am trying to count this
				// item
				crit.setXmlCount(0);
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
	 * When end of the an element is reached, a custom, EndOfRecordRule will be
	 * fired, which pops this Node from the stack as an Element and passes it to
	 * the saveEntry method, which uses JAXB and Hibernate to save the contents
	 * to the database.
	 * 
	 * @author Jeffrey Nicholas
	 * 
	 */
	protected class EndOfRecordRule extends Rule {
		
		public void begin(String namespace, String name, Attributes attributes) {
			
			Digester tempDig = getDigester();
			Criterion tempCrit = _criteria.get(tempDig.getMatch());
			
			if(tempCrit != null && tempCrit.getAttributeAware()
					&& attributes != null && attributes.getLength() > 0) {
		
				// We only care about Criterion that wants us to look
				// further within the node
				HashMap<String, String> knownAttr = tempCrit.getAtrributes();
				String value = null;
				
				for(String key : knownAttr.keySet()) {
					value = knownAttr.get(key);
				
					// Get the value from the found attributes using key
					// as the type
					if(value.equals(attributes.getValue(key))) {
						tempCrit.setXmlCount(tempCrit.getXmlCount() + 1);
						_Log.debug("matched path: " + tempCrit.getDigesterPath() +
								", attribute_name: " + key + ", attribute_type: " + value);						
					}
				}
			}
			
		}

		public void end(String namespace, String name) {
			
			_Log.debug("end of record: " + namespace + ", " + name);
			Digester tempDig = getDigester();
			Criterion tempCrit = _criteria.get(tempDig.getMatch());
		
			// We already now that if we wanted were looking for
			// attributes, then we would catch them at the beginning
			// of the match
			if(tempCrit != null && !tempCrit.getAttributeAware())
				tempCrit.setXmlCount(tempCrit.getXmlCount() + 1);
		}
	}

	private static Log _Log = LogFactory.getLog(TallyEngine.class);

	// CLASS MEMBERS
	HashMap<String, Criterion> _criteria;

} // end class
