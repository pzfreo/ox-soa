package freo.me.rest;
import org.json.JSONObject;
import org.junit.Test;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;


import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Level1Test {
    @Test public void testPOST_Level1() {
    
    	// TEST 1. 
    	// POST a valid JSON with the right elements to http://localhost:8080/purchase
    	// Response code is 201
    	// expect a header Location: url
    	// expect a JSON response with matching values
    	
    	WebTarget target = ClientBuilder.
    			newClient().
    			target("http://localhost:8080").
    			path("purchase");
    	
    	
    	Map<String, String> map = new HashMap<String,String>();
    	map.put("customerNumber","00002");
    	map.put("date","15/05/2017");
    	map.put("lineItem","LI0056");
    	map.put("quantity","3");
    	map.put("poNumber","FREO0073");    	
    	JSONObject json = new JSONObject(map);
    	String jsonText = json.toString();
    	
    	Response response = target.request(MediaType.APPLICATION_JSON)
    			.post(Entity.entity(jsonText,MediaType.APPLICATION_JSON));
    	
    	assertEquals(response.getStatus(),Response.Status.CREATED); // 201
    	assertEquals(response.getMediaType(), MediaType.APPLICATION_JSON);
    	assertTrue(response.getHeaders().containsKey("Location"));
    	
    	JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));
    	
    	// if you want a full JSON comparison, you could do that with JSONAssert
    	assertEquals(jsonResponse.get("poNumber"), json.get("poNumber"));
    	
    
    	// test JSON failure
    	// POST an invalid JSON  to http://localhost:8080/purchase
    	// Response is an HTTP Error 400 (Bad Request)
    	response = target.request(MediaType.APPLICATION_JSON)
    			.post(Entity.entity("bad input",MediaType.APPLICATION_JSON));
    	
    	assertEquals(response.getStatus(),Response.Status.BAD_REQUEST); // 400
    	
    }
    
}
