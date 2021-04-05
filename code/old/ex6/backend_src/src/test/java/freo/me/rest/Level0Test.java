// package freo.me.rest;
// import org.json.JSONObject;
// import org.junit.Test;
// 
// 
// import static org.junit.Assert.*;
// 
// import java.util.HashMap;
// import java.util.Map;
// 
// 
// import javax.ws.rs.client.ClientBuilder;
// import javax.ws.rs.client.Entity;
// import javax.ws.rs.client.WebTarget;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
// 
// public class Level0Test {
//    private static final String BASEURL = "http://localhost:8080";
// 
// @Test public void testPOST_Level0() {
//    
//    
//    	// TEST 1. 
//    	// POST a valid JSON with the right elements to http://localhost:8080/purchase
//    	// Response is a JSON like this:
//    	// { orderId:"....", returnCode:"0"}
//    	
//    	WebTarget target = ClientBuilder.
//    			newClient().
//    			target(BASEURL).
//    			path("purchase");
//    	
//    	
//    	Map<String, String> map = new HashMap<String,String>();
//    	map.put("customerNumber","00002");
//    	map.put("date","15/05/2017");
//    	map.put("lineItem","LI0056");
//    	map.put("quantity","3");
//    	map.put("poNumber","FREO0073");    	
//    	JSONObject json = new JSONObject(map);
//    	String jsonText = json.toString();
//    	
//    	Response response = target.request(MediaType.APPLICATION_JSON)
//    			.post(Entity.entity(jsonText,MediaType.APPLICATION_JSON));
//    	
//    	assertEquals(200,response.getStatus());
//    	assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
//    	
//    	JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));
//    	
//    	assertEquals("0", jsonResponse.get("returnCode"));
//    	assertTrue(jsonResponse.has("orderId"));
//    	
//    	
//    	// test JSON failure
//    	// POST an invalid JSON  to http://localhost:8080/purchase
//    	// Response is a JSON like this:
//    	// { returnCode:"1"}
//    	response = target.request(MediaType.APPLICATION_JSON)
//    			.post(Entity.entity("bad input",MediaType.APPLICATION_JSON));
//    	
//    	
//    	assertEquals(200, response.getStatus());
//    	jsonResponse = new JSONObject(response.readEntity(String.class));
//    	assertEquals("1",jsonResponse.get("returnCode"));
//    	
//    }
//    
// }
