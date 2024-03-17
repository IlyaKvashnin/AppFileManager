package com.sample.servlet.infrastructure.services;

import com.sample.servlet.infrastructure.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbService {
    private static SessionFactory factory;

    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null) {
            factory = new Configuration()
                    .configure("/hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }

        return factory;
    }
}

