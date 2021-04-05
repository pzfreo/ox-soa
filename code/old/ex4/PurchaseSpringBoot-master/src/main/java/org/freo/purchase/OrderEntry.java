package org.freo.purchase;

public class OrderEntry {
	private long orderTime = 0L;
	private String id = null;
	private OrderBean order = null;
	private boolean complete = false;
	private boolean deleted = false;
	
	
	public OrderEntry(String uuid, OrderBean bean) {
		super();
		this.id = uuid;
		this.order = bean;
		this.complete = false;
		this.orderTime = System.currentTimeMillis();
		
	}
	public OrderEntry() {
		super();
	}
	
	public void setOrderTime(long now) {
		this.orderTime = now;
	}

	public void setID(String uuid) {
		this.id = uuid;
	}

	public long getOrderTime() {
		return orderTime;
	}
	public String getID() {
		return id;
	}
	public void setBean(OrderBean order) {
		this.order = order;
	}

	public void setIncomplete() {
		complete = false;
	}
	public void setComplete() {
		complete = true;
	}
	
	public void delete() {
		deleted = true;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public OrderBean getOrder() {
		return order;
	}

}
