// package freo.me.rest;
// 
// import org.json.JSONArray;
// import org.json.JSONObject;
// import org.junit.Test;
// 
// import static org.junit.Assert.*;
// 
// import java.util.HashMap;
// import java.util.Map;
// 
// import javax.ws.rs.client.ClientBuilder;
// import javax.ws.rs.client.Entity;
// import javax.ws.rs.client.WebTarget;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
// import javax.ws.rs.core.Response.Status;
// 
// public class Level3Test {
// 
// 	private static final String BASEURL = "http://localhost:8080";
// 
// 	@Test
// 	public void testGET_Level3() {
// 
// 		// start by creating a new entry
// 		WebTarget target = ClientBuilder.newClient().target(BASEURL).path("purchase");
// 
// 		Map<String, String> map = new HashMap<String, String>();
// 		map.put("customerNumber", "00002");
// 		map.put("date", "15/05/2017");
// 		map.put("lineItem", "LI0056");
// 		map.put("quantity", "3");
// 		map.put("poNumber", "FREO0073");
// 		JSONObject json = new JSONObject(map);
// 		String jsonText = json.toString();
// 
// 		Response response = target.request(MediaType.APPLICATION_JSON)
// 				.post(Entity.entity(jsonText, MediaType.APPLICATION_JSON));
// 
// 		// same target applies
// 		response = target.request(MediaType.APPLICATION_JSON).get();
// 		// should be 200 OK
// 		assertEquals( Status.OK.getStatusCode(), response.getStatus());
// 
// 		// validate our HATEOAS - try "getting" all the URIs returned as hrefs
// 		// and see we get a 200 for each.
// 		json = new JSONObject(response.readEntity(String.class));
// 
// 		JSONArray orders = (JSONArray) json.get("orders");
// 
// 		for (Object o : orders) {
// 			JSONObject order = (JSONObject) o;
// 			String href = order.getString("href");
// 			target = ClientBuilder.newClient().target(BASEURL).path("purchase").path(href);
// 			response = target.request(MediaType.APPLICATION_JSON).get();
// 			assertEquals(Status.OK.getStatusCode(), response.getStatus());
// 
// 		}
// 
// 	}
// 
// }
