package freo.me.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

// this will be the HTTP URL sub path from the Jetty server’s URI where this 
// resource/service will be available
@Path("/purchase")
public class POResource {
	
	OrderInMemory orderSingleton = null;
	 public POResource() {
	     orderSingleton = OrderInMemory.getInstance();
	}


// This method will handle GET requests
    @GET
// Specify the resulting content type
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "Hello!";
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String post(String input) {
    	
    	boolean success = true;
    	String orderId = null;
    	try {
    		orderId = orderSingleton.createOrder(input);
    	}
    	catch (JSONException je) {
    		success = false;
    	}
    	
    	Map<String, String> map = new HashMap<String, String>();
    	if (success) {
     	map.put("orderId", orderId);
    	map.put("returnCode", "0");
    	}
    	else
    	{
    	map.put("returnCode", "1");
    	}
    	JSONObject json = new JSONObject(map);
    	return json.toString();
    	
    }
    
}

