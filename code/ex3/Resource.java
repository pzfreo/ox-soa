package me.freo.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;


@Path("/hello")
@Component
public class Resource {
 
    @GET
    public String sayHello()
    {
        return "Hello World";
    }
}
