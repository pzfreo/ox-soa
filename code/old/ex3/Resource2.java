package me.freo.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class Resource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response sayHello() {
        return Response.status(201).entity("{ \"hello\": \"world\"} ").build();
    }
}
