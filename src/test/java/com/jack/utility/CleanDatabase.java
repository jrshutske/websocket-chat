package com.jack.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.jack.persistence.SessionFactoryProvider;

public class CleanDatabase {
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    public void runCleaner() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("TRUNCATE users CASCADE ").executeUpdate();
        session.createNativeQuery("TRUNCATE rooms CASCADE").executeUpdate();
        transaction.commit();
        session.close();
    }
}