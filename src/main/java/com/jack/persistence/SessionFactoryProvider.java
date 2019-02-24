package com.jack.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryProvider {

    private static SessionFactory sessionFactory;

    private SessionFactoryProvider() {

    }

    public static void createSessionFactory() {

        StandardServiceRegistry standardRegistry =
                new StandardServiceRegistryBuilder().configure().build();
        Metadata metaData =
                new MetadataSources(standardRegistry).getMetadataBuilder().build();
        sessionFactory = metaData.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;
    }
}
