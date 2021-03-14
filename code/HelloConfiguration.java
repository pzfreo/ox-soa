package me.freo.hello;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/")
public class HelloConfiguration extends ResourceConfig {
	public HelloConfiguration() {
        // can be empty
	}

	@PostConstruct
	public void setUp() {
		register(Resource.class);
	}
}
