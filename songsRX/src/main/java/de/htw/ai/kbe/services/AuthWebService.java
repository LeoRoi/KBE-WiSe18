package de.htw.ai.kbe.services;

import de.htw.ai.kbe.storage.IUsersHandler;
import de.htw.ai.kbe.storage.UsersHandler;
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
        Map <String, String>tokenBook = handler.getStorage();             //TODO freaking di
        if (tokenBook.containsKey(userId)) {
            if (tokenBook.get(userId).equals("x")) {        //TODO maybe change x to something more reasonable
                String token = Utils.generateToken();
                tokenBook.replace(userId, token);
                return Response.status(200, "Here is a new Token").entity(token).build();
            } else {
                return Response.status(204, "There is already a Token in use").build();
            }
        }
        return Response.status(403, "No Authorization").build();
    }

}




//TODO dependency injection because UnsatisfiedDependencyException, can't find UsersHandler even tho it is bind() in dependency binder
//TODO tests
//TODO check on get if the id exists because 404 is better then 204