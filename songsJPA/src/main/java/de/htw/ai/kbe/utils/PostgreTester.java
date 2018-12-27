package de.htw.ai.kbe.utils;

import de.htw.ai.kbe.data.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

import static de.htw.ai.kbe.utils.Constants.PERSISTENCE_UNIT_NAME;

public class PostgreTester {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        example();
//        createDefaultUsers();
//        printUsers();
    }

    private static void printUsers() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            Query q = em.createQuery("SELECT u FROM User u");
            @SuppressWarnings("unchecked")
            List<User> userList = q.getResultList();

            System.out.println("\tPostgreTester.main: All users - size: " + userList.size());
            for (User u : userList) {
//                System.out.println("Id: " + u.getId() + " with firstName: " + u.getFirstName());
                System.out.println(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            factory.close();
        }
    }

    private static void createDefaultUsers() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(new User("mmuster", "Maxime", "Muster"));
            em.persist(new User("eschuler", "Elena", "Schuler"));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            factory.close();
        }
    }

    private static void example() {

        // Datei persistence.xml wird automatisch eingelesen, beim Start der Applikation
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        // EntityManager bietet Zugriff auf Datenbank
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            System.out.println("\tPostgreTester.main:  // Create new user");
            User bob = new User("bsmith", "Bob", "Smith");
            em.persist(bob);

            System.out.println("\tPostgreTester.main:  // Create new user");
            User cat = new User("copycat", "rob", "brown");
            em.persist(cat);

            System.out.println("\tPostgreTester.main: // Alle User aus der DB lesen mit JPQL");
            Query q = em.createQuery("SELECT u FROM User u");
            @SuppressWarnings("unchecked")
            List<User> userList = q.getResultList();

            System.out.println("\tPostgreTester.main: All users - size: " + userList.size());
            for (User u : userList) {
//                System.out.println("Id: " + u.getId() + " with firstName: " + u.getFirstName());
                System.out.println(u);
            }

            // Read
            int bobId = bob.getId();
            User bobUserFromDB = em.find(User.class, bobId);
            System.out.println("Found bobUser: " + bobUserFromDB);

            // Update
            bobUserFromDB.setFirstName("Prof. Dr. Bob");

            System.out.println("\tPostgreTester.main: Check that update happened");
            q = em.createQuery("SELECT u FROM User u where id = " + bobUserFromDB.getId());
            userList = q.getResultList();
            for (User u : userList) {
                System.out.println("after update Id: " + u.getId() + " with firstName: " + u.getFirstName());
            }

            System.out.println("\tPostgreTester.main: // Delete");
            em.remove(bobUserFromDB);
            em.remove(em.find(User.class, cat.getId()));

            System.out.println("\tPostgreTester.main: // Check that delete happened");
            q = em.createQuery("SELECT u FROM User u");
            userList = q.getResultList();
            System.out.println("after delete - size: " + userList.size());

            // commit transaction
            em.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // EntityManager nach Datenbankaktionen wieder freigeben
            em.close();
            // Freigabe am Ende der Applikation
            factory.close();
        }
    }
}