package edu.lmu.xmlpipedb.util.engines;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * This class maintains a set of hibernate utilities. The initial class and the
 * closeSession() and currentSession() methods were downloaded from <a
 * href="http://www.hibernate.org/hib_docs/reference/en/html_single/">http://www.hibernate.org/hib_docs/reference/en/html_single/</a>
 * 
 * @author Babak Naffas
 * 
 */
public class QueryEngine {
    public QueryEngine(Configuration hibernateConfiguration) throws HibernateException {
    	sessionFactory = hibernateConfiguration.buildSessionFactory();

        // Test the configuration by trying a transaction.
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        tx.rollback();
        session.close();
    }

    /**
     * Returns an instance of the current Hibernate Session.
     * 
     * @return Session reference.
     * @throws HibernateException
     */
    public Session currentSession() throws HibernateException {
        Session s = (Session)session.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    /**
     * Closses the hibernate session. Modified by Babak Naffas to catch
     * HibernateException within the method and handle it here.
     * 
     * @version 1.0 Originally downloaded implementation
     * @version 1.1 Modified by Babak Naffas to catch HibernateException within
     *          the method and handle it here.
     */
    public void closeSession() {
        try {
            Session s = (Session)session.get();
            session.set(null);
            if (s != null) {
                s.close();
            }
        } catch(HibernateException he) {
            _Log.error(he);
        }
    }

    /**
     * Executes an HQL (Hibernate Query Language) query.
     * 
     * @param hql
     *            The query to execute.
     * @return The resulting java.util.Iterator referencing the results of the
     *         query.
     */
    @SuppressWarnings("rawtypes")
    public Iterator executeHQL(String hql) {
        Session session = null;
        Query query = null;
        Iterator iter = null;
        try {
            session = currentSession();
            query = session.createQuery(hql);
            iter = query.iterate();
        } catch(HibernateException he) {
            _Log.error(he);
        } catch(Exception e) {
            _Log.error(e);
        }

        // closeSession();
        return iter;
    }

    private static final Log _Log = LogFactory.getLog(QueryEngine.class);

    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();

    private SessionFactory sessionFactory;
}
