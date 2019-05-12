package com.jack.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.jack.persistence.SessionFactoryProvider;

/**
 * The type Clean database.
 */
public class CleanDatabase {
    /**
     * The Session factory.
     */
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Run cleaner.
     */
    public void runCleaner() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("TRUNCATE users CASCADE").executeUpdate();
        session.createNativeQuery("TRUNCATE characters CASCADE").executeUpdate();
        transaction.commit();
        session.close();
    }
}