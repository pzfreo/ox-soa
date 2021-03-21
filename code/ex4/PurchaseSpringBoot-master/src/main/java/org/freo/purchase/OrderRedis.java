package org.freo.purchase;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class OrderRedis {

	public static JedisPool pool = new JedisPool(new JedisPoolConfig(),
			System.getenv().containsKey("REDIS_HOST") ? System.getenv("REDIS_HOST") : "localhost");
	public static String oneuuid = UUID.randomUUID().toString();

	/*
	 * Instantiate the system with a new random order
	 */
	public OrderRedis() {

		OrderEntry entry = new OrderEntry(oneuuid, new OrderBean("" + "{'poNumber':'PO1'," + "'lineItem':'11111',"
				+ "'quantity':'1'," + "'date':'24/7/2012'," + "'customerNumber':'1', paymentReference:''}"));
		entry.setComplete();
		this.putOrderToRedis(oneuuid, entry);
	}

	/*
	 * This method will throw a JSONException if there is a problem with the
	 * JSON otherwise it will create an order and return a new UUID
	 */
	public String createOrder(String order) {
		String uuid = UUID.randomUUID().toString();
		OrderBean orderBean = new OrderBean(order);

		OrderEntry entry = new OrderEntry(uuid, orderBean);
		entry.setOrderTime(System.currentTimeMillis());
		entry.setComplete();

		if (this.isOrderInRedis(uuid))
			throw new RuntimeException("Serious UUID problem");
		putOrderToRedis(uuid, entry);
		return uuid;
	}

	/*
	 * This will update an order it can throw NotFoundException if that uuid is
	 * not present It can throw JSONException if the JSON is bad
	 */
	public void updateOrder(String uuid, String order) throws NotFoundException {
		if (uuid == null || !isOrderInRedis(uuid)) {
			throw new NotFoundException();
		}

		OrderEntry entry = getOrderFromRedis(uuid);
		OrderBean orderBean = new OrderBean(order);
		entry.setBean(orderBean);
		entry.setComplete();
		putOrderToRedis(uuid, entry);

	}

	// this method returns a JSON array of all orders
	// which may be empty
	// It does not list incomplete or deleted orders
	public String getOrders() throws JSONException {
		JSONArray array = new JSONArray();
		List<String> keys = getOrderIDs();

		Iterator<String> i = keys.iterator();
		while (i.hasNext()) {
			String uuid = i.next();

			try {
				OrderEntry entry = getOrderFromRedis(uuid);

				if (entry.isComplete() && !entry.isDeleted()) {
					JSONObject href = new JSONObject();

					href.put("href", uuid);
					array.put(href);

				}
			} catch (NotFoundException nfe) {
				// serious error here
			}
		}

		JSONObject json = new JSONObject();

		json.put("orders", array);
		return json.toString(3); // indent 3 for nicer printing!
	}

	/*
	 * This method returns NotFoundException if no order ever existed null if
	 * the order has been deleted Otherwise the JSON
	 */
	public String getOrder(String id) throws NotFoundException, JSONException {
		if (!isOrderInRedis(id))
			throw new NotFoundException();

		OrderEntry entry = getOrderFromRedis(id);
		if (entry.isDeleted())
			return null;

		else
			return entry.getOrder().toJSON().toString();
	}

	/*
	 * This method returns true if freshly deleted false if already deleted
	 * NotFoundException if it never existed
	 */
	public boolean deleteOrder(String id) throws NotFoundException {
		if (isOrderInRedis(id)) {
			OrderEntry entry = getOrderFromRedis(id);
			if (entry.isDeleted()) {
				return false;
			}
			entry.delete();
			putOrderToRedis(id, entry);
			return true;
		} else {
			throw new NotFoundException();
		}
	}

	public void putOrderToRedis(String uuid, OrderEntry order) {

		try (Jedis jedis = pool.getResource()) {
			jedis.set(uuid + ":complete", order.isComplete() ? "true" : "false");
			jedis.set(uuid + ":deleted", order.isDeleted() ? "true" : "false");
			jedis.set(uuid + ":json", order.getOrder().toString());
		}
	}

	public OrderEntry getOrderFromRedis(String uuid) throws NotFoundException {
		try (Jedis jedis = pool.getResource()) {
			String json = jedis.get(uuid + ":json");
			if (json == null) {
				throw new NotFoundException();
			}

			OrderBean order = new OrderBean(json);
			OrderEntry entry = new OrderEntry(uuid, order);
			String complete = jedis.get(uuid + ":complete");
			String deleted = jedis.get(uuid + ":deleted");
			if ("true".equals(complete))
				entry.setComplete();
			if ("true".equals(deleted))
				entry.delete();
			return entry;
		}
	}

	public boolean isOrderInRedis(String uuid) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.exists(uuid + ":json");
		}
	}

	public List<String> getOrderIDs() {
		try (Jedis jedis = pool.getResource()) {
			// note this logic does not cope with large sets of responses
			ScanParams params = new ScanParams();
			params.match("*:json");

			// Use "0" to do a full iteration of the collection.
			ScanResult<String> scanResult = jedis.scan("0", params);
			List<String> keys = scanResult.getResult();
			List<String> uuids = new LinkedList<String>();
			Iterator<String> i = keys.iterator();
			while (i.hasNext()) {
				String key = i.next();
				String uuid = key.substring(0, key.indexOf(":json"));
				uuids.add(uuid);
			}
			return uuids;
		}
	}
}
