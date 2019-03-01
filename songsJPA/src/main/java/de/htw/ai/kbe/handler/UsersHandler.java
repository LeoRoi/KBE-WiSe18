package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.User;
import de.htw.ai.kbe.utils.PostgreCloser;
import de.htw.ai.kbe.utils.Utils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.*;
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
@Singleton
public class UsersHandler implements IUsersHandler {
    private static Map<String, String> storage;
    private static User currentUser;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public UsersHandler() {
        PostgreCloser.addEntityManagerFactory(emf);
        storage = new ConcurrentHashMap<>();
        //initSomeUsers();
//        init();
        initFromDB();
    }

    private void initFromDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
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
            emf.close();
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

    @Override
    public Map<String, String> getStorage() {
        return storage;
    }

    @Override
    public void setCurrentUser(String token) {
        String userId = null;

        //Retrieving userId by the token from the Map<userId, token>  storage
        for (Map.Entry<String, String> entry : storage.entrySet()) {
            if (Objects.equals(token, entry.getValue())) {
                userId = entry.getKey();
            }
        }
        UsersHandler.currentUser = getUserByUserId(userId);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }


    @Override
    public User getUserByUserId(String uid) {
        EntityManager em = emf.createEntityManager();
        User user = null;

        //Retrieving User by the userId from DB
        try {
            TypedQuery<User> q = em.createQuery("SELECT u from User u where u.userId = :userId", User.class)
                    .setParameter("userId", uid);
            user =  q.getSingleResult();
        } finally {
            em.close();
        }
        return user;
    }

}