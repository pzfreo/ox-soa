package freo.me.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;

// this will be the HTTP URL sub path from the Jetty server’s URI where this 
// resource/service will be available
@Path("purchase")
public class POResource {

	OrderInMemory orderSingleton = null;

	public POResource() {
		orderSingleton = OrderInMemory.getInstance();
	}

//	// This method will handle GET requests
//	@GET
//	// Specify the resulting content type
//	@Produces(MediaType.TEXT_PLAIN)
//	public String get() {
//		return "Hello!";
//	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrder(String input, @Context UriInfo uriInfo) {

		boolean success = true;
		String orderId = null;
		try {
			orderId = orderSingleton.createOrder(input);
		} catch (JSONException je) {
			success = false;
		}

		if (success) {
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(orderId);
			try {
				return Response.created(builder.build()).entity(orderSingleton.getOrder(orderId)).build();
			} catch (IllegalArgumentException | UriBuilderException | JSONException | NotFoundException e) {
				// something really freaky happened here
				return Response.serverError().build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateOrder(String input, @PathParam("id") String id) {



		try {
			orderSingleton.updateOrder(id, input);
			// return the server's representation
			return Response.ok(orderSingleton.getOrder(id)).build();
		} catch (JSONException je) {
			return Response.status(Status.BAD_REQUEST).build();
		} catch (NotFoundException nfe) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getOrder(@PathParam("id") String id)
	{
		    String orderJSON;
			try {
				orderJSON = orderSingleton.getOrder(id);
			} catch ( NotFoundException e) {
				return Response.status(Status.NOT_FOUND).build();
			}
			if (orderJSON == null) {
				return Response.status(Status.GONE).build();
			}
			return Response.ok(orderJSON).build();
	
	}
	@DELETE
	@Path("{id}")
	public Response deleteOrder(@PathParam("id") String id)
	{
		   try {
				boolean deleted = orderSingleton.deleteOrder(id);
				if (deleted) {
					return Response.ok().build();
				}
				else
				{
					return Response.status(Status.GONE).build();
				}
			} catch ( NotFoundException e) {
				return Response.status(Status.NOT_FOUND).build();
			}
			
	}
	@GET
	public Response getAllOrders()
	{
		String allOrders = orderSingleton.getOrders();
		return Response.ok(allOrders).build();
	}
}
