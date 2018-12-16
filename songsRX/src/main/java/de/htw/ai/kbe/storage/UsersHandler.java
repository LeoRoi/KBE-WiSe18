package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.User;
import de.htw.ai.kbe.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class UsersHandler implements IUsersHandler {

    //private static UsersHandler instance = null;
    private static Map<String, String> storage;


    public UsersHandler() {
        storage = new ConcurrentHashMap<>();
        //initSomeUsers();
        init();
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

   /* private void initSomeUsers() {

        String token1 = "x";
        String userId1 = "mmuster";
        storage.put(userId1, token1);

        String token2 = "bla";
        String userId2 = "eschueler";
        storage.put(userId2, token2);
    }*/

    public Map<String, String> getStorage() {
        return storage;
    }

   /* public synchronized static UsersHandler getInstance() {
        if (instance == null) {
            instance = new UsersHandler();
        }
        return instance;
    }*/
}