package freo.me.rest;
import org.json.JSONObject;
import org.junit.Test;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.tools.DocumentationTool.Location;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



public class Level2Test {
	
	String location = null;
	
    @Test public void testPOST_Level2() {
    
    	// TEST 1. 
    	// POST a valid JSON with the right elements to http://localhost:8080/purchase
    	// Response code is 201
    	// expect a header Location: url
    	// don't check any more
    	// use the Location for the further tests
    	
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
    	
    	assertEquals(response.getStatus(),Response.Status.CREATED); //201
    	assertTrue(response.getHeaders().containsKey("Location"));
    	
    	location = response.getHeaderString("Location");
    	
    }
    
    @Test public void TestGet_Level2() {
    	// Test get the order
    	WebTarget target = ClientBuilder.newClient().target(location);
    	
    	Response response = target.request(MediaType.APPLICATION_JSON).get();
    	assertEquals(response.getStatus(),Response.Status.OK); // 200
    	assertEquals(response.getMediaType(), MediaType.APPLICATION_JSON);
    	JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));
    	
    	// if you want a full JSON comparison, you could do that with JSONAssert
	    assertEquals(jsonResponse.get("poNumber"), "FREO0073");
	    	
    	// TEST GET failure
    	target = ClientBuilder.newClient().target("http://localhost:8080/purchase/blah");
    	response = target.request(MediaType.APPLICATION_JSON).get();
    	assertEquals(response.getStatus(),Response.Status.NOT_FOUND); // 404
    		
    }
    
    @Test public void TestPut_Level2() {
    	WebTarget target = ClientBuilder.
    			newClient().
    			target("location");
    	
    	// GET existing value and then update with new
    	Response response = target.request(MediaType.APPLICATION_JSON).get();
    	assertEquals(response.getStatus(),Response.Status.OK); // 200
    	assertEquals(response.getMediaType(), MediaType.APPLICATION_JSON);
    	JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));
    	
    	
    	String poNumber = jsonResponse.getString("poNumber");
    	int quantity = Integer.parseInt((String)jsonResponse.get("quantity"));
    	quantity++;
    	
    	jsonResponse.put("quantity",Integer.toString(quantity));
    	
    	
    	String jsonText = jsonResponse.toString();
    	
    	response = target.request(MediaType.APPLICATION_JSON)
    			.put(Entity.entity(jsonText,MediaType.APPLICATION_JSON));
    	
    	assertEquals(response.getStatus(),Response.Status.OK);
    	assertEquals(response.getMediaType(), MediaType.APPLICATION_JSON);
    	jsonResponse = new JSONObject(response.readEntity(String.class));
    	
    	assertEquals(jsonResponse.get("quantity"), quantity);
    	assertEquals(jsonResponse.get("poNumber"), poNumber);
    	
    	// validate sending incomplete or bad JSON
    	response = target.request(MediaType.APPLICATION_JSON)
    			.put(Entity.entity("{blah}",MediaType.APPLICATION_JSON));
    	assertEquals(response.getStatus(),Response.Status.BAD_REQUEST);
    }
    
    @Test public void TestDelete_Level2() {
    	WebTarget target = ClientBuilder.
    			newClient().
    			target("location");
    	
    	// DELETE 
    	Response response = target.request().delete();
    	assertEquals(response.getStatus(),Response.Status.OK); // 200
    	
    	// DELETE again
    	response = target.request().delete();
    	
    	assertEquals(response.getStatus(), Response.Status.GONE);
    
    	// Test Deleting resource that doesn't exist
    	target = ClientBuilder.
    			newClient().
    			target("http://localhost:8080/purchase/blah");
    	response = target.request().delete();
    	assertEquals(response.getStatus(), Response.Status.NOT_FOUND); // 404
    
    }
    
}
