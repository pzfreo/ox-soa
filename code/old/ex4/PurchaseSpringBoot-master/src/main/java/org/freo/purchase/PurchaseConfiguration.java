package org.freo.purchase;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/")
public class PurchaseConfiguration extends ResourceConfig {
	public PurchaseConfiguration() {
	}

	@PostConstruct
	public void setUp() {
		register(Purchase.class);
	}
}