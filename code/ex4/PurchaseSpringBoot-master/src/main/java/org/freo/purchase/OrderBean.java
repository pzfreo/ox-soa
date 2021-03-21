package org.freo.purchase;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class OrderBean {
	private String poNumber;
	private String lineItem;
	private String quantity;
	private String date;
	private String customerNumber;
	private String paymentReference;

	
	
	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineitem) {
		this.lineItem = lineitem;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public OrderBean() {
	
	}

	public OrderBean(String json) throws JSONException {
		
			JSONObject j = new JSONObject(new JSONTokener(json));
			this.setCustomerNumber( j.getString("customerNumber"));
			this.setDate(j.getString("date"));
			this.setQuantity(j.getString("quantity"));
			this.setPoNumber(j.getString("poNumber"));
			this.setLineItem(j.getString("lineItem"));
			
		
	}

	public JSONObject toJSON() {
		JSONObject j = new JSONObject();
		try {
			j.put("customerNumber", this.getCustomerNumber());
			j.put("lineItem", this.getLineItem());
			j.put("quantity", this.getQuantity());
			j.put("date", this.getDate());
			j.put("poNumber", this.getPoNumber());
			j.put("paymentReference", this.getPaymentReference());
			return j;
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return null;
	}

	public String toString() {
		return this.toJSON().toString();
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

}
