package freo.me.rest;

import java.util.concurrent.ConcurrentMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderInMemory {
	private static OrderInMemory instance = null; 
	private static ConcurrentMap<String, OrderEntry> orders = new ConcurrentHashMap<String, OrderEntry>();
	
	// singleton model for in memory
	public static OrderInMemory getInstance() {
        if (instance == null) {
                synchronized (OrderInMemory.class){
                        if (instance == null) {
                                instance = new OrderInMemory();
                        }
              }
        }
        return instance;
}
	
	/*
	 * Instantiate the system with a new random order
	 */
	public OrderInMemory() {
		String uuid = UUID.randomUUID().toString();
		OrderEntry entry = new OrderEntry(uuid, new OrderBean(""
				+ "{'poNumber':'PO1'," + "'lineItem':'11111',"
				+ "'quantity':'1'," + "'date':'24/7/2012',"
				+ "'customerNumber':'1', paymentReference:''}"));
		entry.setComplete();
		orders.put(uuid, entry);
	}

	/*
	 * This method will throw a JSONException if there is a problem with the JSON
	 * otherwise it will create an order and return a new UUID
	 */
	public String createOrder(String order) {
		OrderEntry entry = new OrderEntry();
		String uuid = UUID.randomUUID().toString();
		entry.setID(uuid);
		entry.setOrderTime(System.currentTimeMillis());
		OrderBean orderBean = new OrderBean(order);
		entry.setBean(orderBean);
		entry.setComplete();
		OrderEntry entryPut = orders.putIfAbsent(uuid, entry);
		if (entryPut != null) // problem - already an entry with that UUID!?
		{
			throw new RuntimeException("Serious UUID problem");
		}

		return uuid;

	}

	/*
	 * This will update an order 
	 * it can throw NotFoundException if that uuid is not present
	 * It can throw JSONException if the JSON is bad
	 */
	public void updateOrder(String uuid, String order) throws NotFoundException {
		if (uuid == null || !orders.containsKey(uuid)) {
			throw new NotFoundException();
		}

		OrderEntry entry = orders.get(uuid);
		OrderBean orderBean = new OrderBean(order);
		entry.setBean(orderBean);
		entry.setComplete();
		orders.replace(uuid, entry);
		
	}

	// this method returns a JSON array of all orders
	// which may be empty
	// It does not list incomplete or deleted orders
	public String getOrders() throws JSONException {
		JSONArray array = new JSONArray();
		Iterator<String> i = orders.keySet().iterator();

		while (i.hasNext()) {
			String key = i.next();
			OrderEntry entry = orders.get(key);
			if (entry.isComplete() && !entry.isDeleted()) {
				JSONObject href = new JSONObject();

				href.put("href", key);
				array.put(href);

			}
		}
		JSONObject json = new JSONObject();

		json.put("orders", array);
		return json.toString();
	}

	/*
	 * This method returns NotFoundException if no order ever existed
	 * null if the order has been deleted
	 * Otherwise the JSON
	 */
	public String getOrder(String id) throws NotFoundException, JSONException {
		if (!orders.containsKey(id)) throw new NotFoundException();
		
		OrderEntry entry = orders.get(id);
		if (entry.isDeleted()) return null;
		
		else return entry.getOrder().toJSON().toString();
	}
	
	
	/*
	 * This method returns true if freshly deleted
	 * false if already deleted
	 * NotFoundException if it never existed
	 */
	public boolean deleteOrder(String id) throws NotFoundException {
		if (orders.containsKey(id)) {
			OrderEntry entry = orders.get(id);
			if (entry.isDeleted()) {
				return false;
			}
			entry.delete();
			return true;
		} else {
			throw new NotFoundException();
		}
	}

}
