package edu.lmu.xmlpipedb.util.app;

//import org.logicalcobwebs.logging.Log;
//import org.logicalcobwebs.logging.LogFactory;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;

import java.util.Iterator;
import java.util.Vector;
/**
 * This class maintains a set of hibernate utilities. The initial class and the closeSession() and currentSession() methods were downloaded from <a href="http://www.hibernate.org/hib_docs/reference/en/html_single/">http://www.hibernate.org/hib_docs/reference/en/html_single/</a>
 * @author Babak Naffas
 *
 */
public class HibernateUtil {

    //private static Log log = LogFactory.getLog(HibernateUtil.class);

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            //log.error("Initial SessionFactory creation failed.", ex);
        	System.err.println( "Initial SessionFactory creation failed.\n" + ex );
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static final ThreadLocal session = new ThreadLocal();

    /**
     * Returns an instance of the current Hibernate Session.
     * @return Session reference.
     * @throws HibernateException
     */
    public static Session currentSession() throws HibernateException {
        Session s = (Session) session.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    /**
     * Closses the hibernate session. Modified by Babak Naffas to catch HibernateException within the method and handle it here. 
     * 
     * @version 1.0 Originally downloaded implementation
     * @version 1.1 Modified by Babak Naffas to catch HibernateException within the method and handle it here.
     */
    public static void closeSession() {
    	try{
    		Session s = (Session) session.get();
    		session.set(null);
    		if (s != null)
    			s.close();
    	}
    	catch( HibernateException he ){
    		System.err.println( he.getMessage() );
    	}
    }
    
    /**
     * Executes an HQL (Hibernate Wuery Language) query.
     * @param hql	The query to execute.
     * @return	A java.util.Vector<Object> storing each returned object. 
     */
    public static Vector<Object> executeHQL( String hql ){
    	
    	Session session = null;
    	Query query = null;
    	Vector<Object> ret = new Vector<Object>(0);
    	try{
    		session = currentSession();
    		query = session.createQuery( hql );
    		
    		Iterator it = query.iterate();
     		while( it.hasNext() ){
    			ret.addElement( it.next() );
    		}
    	}
    	catch( HibernateException he ){
    		System.err.println( he.getMessage() );
    	}
    	
    	closeSession();
    	return ret;
    	
    }
}
