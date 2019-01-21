package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.User;

import java.util.Map;

public interface IUsersHandler {
    Map<String, String> getStorage();

    void setCurrentUser(String token);

    User getCurrentUser();

    User getUserByUserId(String uid);
}
