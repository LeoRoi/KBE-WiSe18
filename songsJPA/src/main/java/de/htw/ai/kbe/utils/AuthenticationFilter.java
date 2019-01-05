package de.htw.ai.kbe.utils;

import de.htw.ai.kbe.handler.IUsersHandler;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
    private IUsersHandler handler;

    @Inject
    public AuthenticationFilter(IUsersHandler userHandler) {
        super();
        handler = userHandler;
    }

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        System.out.println("Filter up and running");
        if (!requestContext.getUriInfo().getPath().contains("auth")) {
            System.out.println("this is not /auth");
            String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (!authenticate(token)) {
                Response.ResponseBuilder responseBuilder = Response.serverError();
                Response response = responseBuilder.status(401, "Token not in the DB").build();
                requestContext.abortWith(response);
            }
        }
    }

    private boolean authenticate(String token) {
        if (token == null) token = "-";
        //Map<String, String> tokenMap = UsersHandler.getInstance().getStorage();
        Map<String, String> tokenMap = handler.getStorage();
        if (tokenMap.containsValue(token)) {
            System.out.println("authenticated");
            return true;
        }
        return false;
    }
}