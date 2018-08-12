/*
 * The place for handling all REST calls 
 */
package Controller;


import model.Brand;
import model.Product;
import model.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;


@Path("/service")
public class Service {
	
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Brand> getCountries(){
		return Storage.getInstance().getBrandList();
	}
	
	@POST
	@Path("/addProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addProduct(JSONObject obj) {

			//convert JSON to hashmap
			@SuppressWarnings("unchecked")
			Map<String,Object> productJSONMap =  (Map<String,Object>)obj;
	
			
			@SuppressWarnings("unchecked")
			ArrayList<Object> ob2 = (ArrayList<Object>)productJSONMap.get("list");
			//extract product info list and make it into a Map
			@SuppressWarnings("unchecked")
			Map<String,String> hash = (Map<String,String>)ob2.get(0);
			//adding create a product inside the a brand
			Storage.getInstance().addProduct(productJSONMap.get("name").toString(), new Product(hash.get("name"), Double.valueOf(String.valueOf(hash.get("value"))), (Integer.parseInt(String.valueOf(hash.get("duration")))), hash.get("platform"), hash.get("country")));
			
			return Response.status(201).entity("Product Succesfully added").build();
	}
	
	@GET
	@Path("/searchProduct/{search}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Brand> searchProduct(@PathParam("search") String searchTerm){
		//Search a keyword from a brand or product inside the storage
		return Storage.getInstance().searchProduct(searchTerm);
	}
	
	@POST
	@Path("/{id}/updateBrandName")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateBrandName(JSONObject obj, @PathParam("id") int id) {
			@SuppressWarnings("unchecked")
			Map<String,Object> map =  (Map<String,Object>)obj;
			//update the brand name with the brand id
		    boolean changed = Storage.getInstance().updateBrandName(id, map.get("name").toString());
		    
			if(changed) {
				return Response.status(201).entity( "Brand Succesfully changed").build();
			}
			else
				return Response.status(201).entity( "Brand name didn't change").build();
	}
	
	@POST
	@Path("/{id}/updateProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateProduct(JSONObject obj, @PathParam("id") int id) {
			@SuppressWarnings("unchecked")
			//extract product info from JSON and make it into a Map
			HashMap<String,String> map =  (HashMap<String,String>)obj;
			boolean changed = Storage.getInstance().updateProduct(id, map);

		
			if(changed) {
				return Response.status(201).entity( "Product Succesfully changed").build();
		}
		else
			return Response.status(201).entity( "Product name didn't changeg").build();
	}
	
	@POST
	@Path("/{id}/removeProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response removeProduct(@PathParam("id") int id) {
		//extract product info from JSON and make it into a Map
		boolean changed = Storage.getInstance().removeProduct(id);
			
		if(changed) {
				return Response.status(201).entity( "Product Succesfully removed").build();
		}
		else
			return Response.status(201).entity( "Product didn't removed").build();
	}
}
