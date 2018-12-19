package de.htw.ai.kbe;

import de.htw.ai.kbe.data.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class App {

    // entspricht <persistence-unit name="songDB-PU" transaction-type="RESOURCE_LOCAL"> in persistence.xml
    private static final String PERSISTENCE_UNIT_NAME = "songDB-PU";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        // Datei persistence.xml wird automatisch eingelesen, beim Start der Applikation
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        // EntityManager bietet Zugriff auf Datenbank
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();
            System.out.println("\tApp.main:  // Create: neuen User anlegen");
            User user = new User("bsmith", "Bob", "Smith");
            em.persist(user);

            System.out.println("\tApp.main: // Alle User aus der DB lesen mit JPQL");
            Query q = em.createQuery("SELECT u FROM User u");
            @SuppressWarnings("unchecked")
            List<User> userList = q.getResultList();
            System.out.println("\tApp.main: All users - size: " + userList.size());
            for (User u : userList) {
//                System.out.println("Id: " + u.getId() + " with firstName: " + u.getFirstName());
                System.out.println(u);
            }

            // Read
            int bobId = user.getId();
            User bobUserFromDB = em.find(User.class, bobId);
            System.out.println("Found bobUser: " + bobUserFromDB);

            // Update
            bobUserFromDB.setFirstName("Prof. Dr. Bob");

            System.out.println("\tApp.main: Check that update happened");
            q = em.createQuery("SELECT u FROM User u where id = " + bobUserFromDB.getId());
            userList = q.getResultList();
            for (User u : userList) {
                System.out.println("after update Id: " + u.getId() + " with firstName: " + u.getFirstName());
            }

            System.out.println("\tApp.main: // Delete");
            em.remove(bobUserFromDB);

            System.out.println("\tApp.main: // Check that delete happened");
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