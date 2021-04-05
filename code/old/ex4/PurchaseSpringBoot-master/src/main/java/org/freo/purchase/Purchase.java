package org.freo.purchase;


import org.json.JSONException;
import org.springframework.stereotype.Component;

@Component
public class Purchase {

	OrderRedis backend = new OrderRedis();
	
	// this is not right :-)
	// your job is to fix and annotate this so that it passes the Level1_POST test
	public String createOrder(String input) {

		String orderId = null;
		try {
			// this returns a simple UUID string
			orderId = backend.createOrder(input);
		} catch (JSONException je) {
			// else deal with this
		}
		return orderId;
		
	}

	// For level 2 and 3 you will need these examples

	// other backend examples
    // try {
	// 	backend.updateOrder(id, input);
	//  // this updates an order - takes a UUID and a JSON string
	// } catch (JSONException je) {
	// } catch (NotFoundException nfe) {
	// }

	// String orderJSON;
	// try {
	// 	orderJSON = backend.getOrder(id);
	// 	// returns a JSON string based on a UUID
	// } catch ( NotFoundException e) {
	// }

	// try {
	// 	boolean deleted = backend.deleteOrder(id);
	// 	if (deleted) {
	// 		// successfully deleted
	// 	}
	// 	else
	// 	{
	// 		// previously deleted
	// 	}
	// } catch ( NotFoundException e) {
	// 	// UUID has never existed
	// }

	// String allOrders = backend.getOrders();
	// // returns a JSON array containing hrefs to all the UUIDs in the database

}