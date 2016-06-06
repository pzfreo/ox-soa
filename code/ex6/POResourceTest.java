//package freo.me.rest;
//import org.junit.Test;
//
//
//import static org.junit.Assert.*;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//public class POResourceTest {
//    private static final String BASEURL = "http://localhost:8080";
//
//	@Test public void testGet() {
//    
//    	Client client = ClientBuilder.newClient();
//    	WebTarget target = client.target(BASEURL).path("purchase");
//    	
//    	Response response = target.request(MediaType.TEXT_PLAIN).get();
//    	assertEquals(response.getStatus(),Response.Status.OK); // 200
//    	assertEquals(response.readEntity(String.class),"Hello!");
//    	
//    }
//    
//}
