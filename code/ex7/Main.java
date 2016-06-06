package freo.me.rest;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {

	public static void main(String[] args) throws Exception {
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
		// This is fairly self-explanatory.
		// You can define the URL on which the server will listen.

		ResourceConfig config = new ResourceConfig(POResource.class);
		// This is where we identify that the class POResource is the JAX-RS
		// Resource (aka Service) that we want to expose.

		Server server = JettyHttpContainerFactory.createServer(baseUri, config);
		// Here is where we create the Jetty Server object.

		try {
			server.start();
			// This initiates the startup of the server.

			server.join();
			// Wait until the server finishes initiation

		} finally {
			server.destroy();
			// Obvious!
		}

	}

}
