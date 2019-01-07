package de.htw.ai.kbe.utils;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PsqlCloser implements ServletContextListener {
    static private List<EntityManagerFactory> emfs = new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        for (EntityManagerFactory emf : emfs) {
            emf.close();
        }
    }

    static public void addEntityManagerFactory(EntityManagerFactory emf) {
        emfs.add(emf);
    }
}