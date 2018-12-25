package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.User;
import de.htw.ai.kbe.utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static de.htw.ai.kbe.utils.Constants.PERSISTENCE_UNIT_NAME;

/*
* drop and create table with entries here?
* or convert from db?
*
*/

public class UsersHandler implements IUsersHandler {
    private static Map<String, String> storage;

    public UsersHandler() {
        storage = new ConcurrentHashMap<>();
        //initSomeUsers();
//        init();
        initFromDB();
    }

    private void initFromDB() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            Query q = em.createQuery("SELECT u FROM User u");
            @SuppressWarnings("unchecked")
            List<User> userList = q.getResultList();

            for (User user : userList) {
                storage.put(user.getUserId(), "x");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            factory.close();
        }
    }

    private void init() {
        ClassLoader classLoader = getClass().getClassLoader();
        String pathToUsers = Objects.requireNonNull(classLoader.getResource("users.json")).getPath();
        try {
            List<User> users = Utils.readJSONToUsers(pathToUsers);
            for (User i : users) storage.put(i.getUserId(), "x");   //TODO replace x with something more reasonable
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getStorage() {
        return storage;
    }
}