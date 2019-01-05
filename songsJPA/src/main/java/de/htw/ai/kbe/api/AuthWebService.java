package de.htw.ai.kbe.api;

import de.htw.ai.kbe.storage.IUsersHandler;
import de.htw.ai.kbe.utils.Utils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/auth")
public class AuthWebService {
    private IUsersHandler handler;

    @Inject
    public AuthWebService(IUsersHandler userHandler) {
        super();
        this.handler = userHandler;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkToken(@QueryParam("userId") String userId) {
        //Map <String, String>tokenBook = UsersHandler.getInstance().getStorage();
        Map<String, String> tokenBook = handler.getStorage();
        if (tokenBook.containsKey(userId)) {
            if (tokenBook.get(userId).equals("x")) {        //TODO maybe change x to something more reasonable
                String token = Utils.generateToken();
                tokenBook.replace(userId, token);
                return Response.status(200, "Here is a new Token").entity(token).build();
            } else {
                String alreadyExistingToken = tokenBook.get(userId);
                return Response.status(200, "There is already a Token in use : " + alreadyExistingToken)
                        .entity("There is already a Token in use : " + alreadyExistingToken)
                        .build();
            }
        }
        return Response.status(403, "No Authorization").build();
    }
}