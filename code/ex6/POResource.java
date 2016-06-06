package freo.me.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// this will be the HTTP URL sub path from the Jetty server’s URI where this 
// resource/service will be available
@Path("purchase")
public class POResource {

// This method will handle GET requests
    @GET
// Specify the resulting content type
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "Hello!";
    }
}

