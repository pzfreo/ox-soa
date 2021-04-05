package org.freo.purchase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PurchaseApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class PurchaseApplicationTests {

	ClientConfig config = new ClientConfig();

	private static final String BASEURL = "http://localhost:8080";

	private static final String PROXY_URI = "http://localhost:8000";
	// private static final String PROXY_URI = null;

	@Test
	public void testPOST_Level1() {

		// TEST 1.
		// POST a valid JSON with the right elements to http://localhost:8080/purchase
		// Response code is 201
		// expect a header Location: url
		// expect a JSON response with matching values

		if (PROXY_URI != null)
			config.property(ClientProperties.PROXY_URI, PROXY_URI);
		config.connectorProvider(new ApacheConnectorProvider());
		WebTarget target = ClientBuilder.newClient(config).target(BASEURL).path("purchase");

		Map<String, String> map = new HashMap<String, String>();
		map.put("customerNumber", "00002");
		map.put("date", "15/05/2017");
		map.put("lineItem", "LI0056");
		map.put("quantity", "3");
		map.put("poNumber", "FREO0073");
		JSONObject json = new JSONObject(map);
		String jsonText = json.toString();

		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(jsonText, MediaType.APPLICATION_JSON));

		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus()); // 201

		assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
		assertTrue(response.getHeaders().containsKey("Location"));

		JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));

		// if you want a full JSON comparison, you could do that with JSONAssert
		assertEquals(json.get("poNumber"), jsonResponse.get("poNumber"));

		// test JSON failure
		// POST an invalid JSON to http://localhost:8080/purchase
		// Response is an HTTP Error 400 (Bad Request)
		response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity("bad input", MediaType.APPLICATION_JSON));

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus()); // 400

	}

	@Test
	public void testPOST_Level2() {

		// TEST 1.
		// POST a valid JSON with the right elements to http://localhost:8080/purchase
		// Response code is 201
		// expect a header Location: url
		// don't check any more
		// use the Location for the further tests
		if (PROXY_URI != null)
			config.property(ClientProperties.PROXY_URI, PROXY_URI);
		config.connectorProvider(new ApacheConnectorProvider());
		WebTarget target = ClientBuilder.newClient(config).target(BASEURL).path("purchase");

		Map<String, String> map = new HashMap<String, String>();
		map.put("customerNumber", "00002");
		map.put("date", "15/05/2017");
		map.put("lineItem", "LI0056");
		map.put("quantity", "3");
		map.put("poNumber", "FREO0073");
		JSONObject json = new JSONObject(map);
		String jsonText = json.toString();

		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(jsonText, MediaType.APPLICATION_JSON));

		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus()); // 201
		assertTrue(response.getHeaders().containsKey("Location"));

	}

	@Test
	public void TestGets_Level2() {
		// Test get the order
		// Need to post an order first
		if (PROXY_URI != null)
			config.property(ClientProperties.PROXY_URI, PROXY_URI);
		config.connectorProvider(new ApacheConnectorProvider());
		WebTarget target = ClientBuilder.newClient(config).target(BASEURL).path("purchase");

		Map<String, String> map = new HashMap<String, String>();
		map.put("customerNumber", "00002");
		map.put("date", "15/05/2017");
		map.put("lineItem", "LI0056");
		map.put("quantity", "3");
		map.put("poNumber", "FREO0073");

		JSONObject json = new JSONObject(map);
		String jsonText = json.toString();

		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(jsonText, MediaType.APPLICATION_JSON));

		String location = response.getHeaderString("Location");
		target = ClientBuilder.newClient(config).target(location);
		response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus()); // 200
		assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
		JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));

		// if you want a full JSON comparison, you could do that with JSONAssert
		assertEquals("FREO0073", jsonResponse.get("poNumber"));

		// TEST GET failure
		target = ClientBuilder.newClient(config).target("http://localhost:8080/purchase/blah");
		response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus()); // 404

	}

	@Test
	public void TestPut_Level2() {
		// Need to post an order first
		if (PROXY_URI != null)
			config.property(ClientProperties.PROXY_URI, PROXY_URI);
		config.connectorProvider(new ApacheConnectorProvider());
		WebTarget target = ClientBuilder.newClient(config).target(BASEURL).path("purchase");

		Map<String, String> map = new HashMap<String, String>();
		map.put("customerNumber", "00002");
		map.put("date", "15/05/2017");
		map.put("lineItem", "LI0056");
		map.put("quantity", "3");
		map.put("poNumber", "FREO0073");

		JSONObject json = new JSONObject(map);
		String jsonText = json.toString();

		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(jsonText, MediaType.APPLICATION_JSON));

		String location = response.getHeaderString("Location");

		// GET existing value and then update with new
		target = ClientBuilder.newClient(config).target(location);
		response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus()); // 200
		assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
		JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));

		String poNumber = jsonResponse.getString("poNumber");
		int quantity = Integer.parseInt((String) jsonResponse.get("quantity"));
		quantity++;

		jsonResponse.put("quantity", Integer.toString(quantity));

		jsonText = jsonResponse.toString();

		response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(jsonText, MediaType.APPLICATION_JSON));

		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
		jsonResponse = new JSONObject(response.readEntity(String.class));

		assertEquals(Integer.toString(quantity), jsonResponse.get("quantity"));
		assertEquals(poNumber, jsonResponse.get("poNumber"));

		// validate sending incomplete or bad JSON
		response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity("{blah}", MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void TestDelete_Level2() {
		if (PROXY_URI != null)
			config.property(ClientProperties.PROXY_URI, PROXY_URI);
		config.connectorProvider(new ApacheConnectorProvider());
		WebTarget target = ClientBuilder.newClient(config).target(BASEURL).path("purchase");

		Map<String, String> map = new HashMap<String, String>();
		map.put("customerNumber", "00002");
		map.put("date", "15/05/2017");
		map.put("lineItem", "LI0056");
		map.put("quantity", "3");
		map.put("poNumber", "FREO0073");

		JSONObject json = new JSONObject(map);
		String jsonText = json.toString();

		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(jsonText, MediaType.APPLICATION_JSON));

		String location = response.getHeaderString("Location");

		target = ClientBuilder.newClient(config).target(location);

		// DELETE
		response = target.request().delete();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus()); // 200

		// DELETE again
		response = target.request().delete();

		assertEquals(Response.Status.GONE.getStatusCode(), response.getStatus());

		// Test Deleting resource that doesn't exist
		target = ClientBuilder.newClient(config).target("http://localhost:8080/purchase/blah");
		response = target.request().delete();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus()); // 404

	}

	@Test
	public void testGET_Level3() {

		// start by creating a new entry
		if (PROXY_URI != null)
			config.property(ClientProperties.PROXY_URI, PROXY_URI);
		config.connectorProvider(new ApacheConnectorProvider());
		WebTarget target = ClientBuilder.newClient(config).target(BASEURL).path("purchase");

		Map<String, String> map = new HashMap<String, String>();
		map.put("customerNumber", "00002");
		map.put("date", "15/05/2017");
		map.put("lineItem", "LI0056");
		map.put("quantity", "3");
		map.put("poNumber", "FREO0073");
		JSONObject json = new JSONObject(map);
		String jsonText = json.toString();

		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(jsonText, MediaType.APPLICATION_JSON));

		// same target applies
		response = target.request(MediaType.APPLICATION_JSON).get();
		// should be 200 OK
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		// validate our HATEOAS - try "getting" all the URIs returned as hrefs
		// and see we get a 200 for each.
		json = new JSONObject(response.readEntity(String.class));

		JSONArray orders = (JSONArray) json.get("orders");

		for (Object o : orders) {
			JSONObject order = (JSONObject) o;
			String href = order.getString("href");
			target = ClientBuilder.newClient(config).target(BASEURL).path("purchase").path(href);
			response = target.request(MediaType.APPLICATION_JSON).get();
			assertEquals(Status.OK.getStatusCode(), response.getStatus());

		}

	}

}