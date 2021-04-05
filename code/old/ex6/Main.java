package freo.me.rest;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
	public static void main(String[] args) throws Exception {

		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8000).build();
		ResourceConfig config = new ResourceConfig(MyResource.class);
		Server server = JettyHttpContainerFactory.createServer(baseUri, config);

		try {
			server.start();
			server.join();
		} finally {
			server.destroy();
		}

	}
}