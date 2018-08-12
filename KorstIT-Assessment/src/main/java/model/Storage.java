/*
 * Storing all Brands
 */
package model;


import java.util.*;

public class Storage {

	private static List<Brand> brandList = new ArrayList<Brand>();
	private static Storage instance;
	
	
	//Storage Singleton
	public static Storage getInstance() {
        if(instance == null) {
        	instance = new Storage();
        }
        return instance;
    }
	
	
	//Adding A product into a brand class or making a new Brand
	public void addProduct(String brandName,Product product) {
		
		Brand currentBrand = null;
		for(Brand b : brandList) {  // Check if Brand exist
			if (b.getName().equals(brandName)) {
				currentBrand = b;
				if(!checkDuplicte(currentBrand,product)) {
					currentBrand.addProduct(product);
				}

			}
		}
		
		if(currentBrand == null) {
			currentBrand = new Brand(brandName);
			brandList.add(currentBrand);	
			if(!checkDuplicte(currentBrand,product)) {
				currentBrand.addProduct(product);
			}
		}	
	}
	
	//Check for duplicated entry of product
	public Boolean checkDuplicte(Brand brand, Product product) {
		if(brand.getList().isEmpty()) return false;
			
		boolean dupeName = false;
		boolean dupeValue = false;
		boolean dupeDuration = false;
		boolean dupelPlatform = false;
		boolean dupeCountry = false;
		
		for(Product p : brand.getList()) {
			if(p.getName().equals(product.getName())) { dupeName = true; }
			if(p.getValue().equals(product.getValue())) {dupeValue = true; }
			if(p.getDuration() == product.getDuration()) { dupeDuration = true; }
			if(p.getPlatform().equals(product.getPlatform())) { dupelPlatform = true; }
			if(p.getCountry().equals(product.getCountry())) { dupeCountry = true; }
			
			if(dupeName && dupeValue && dupeDuration && dupelPlatform && dupeCountry)
			{
				return true;
			}
			
		}
		

			return false;
	}
	//Get a list of all brand and product
	public List<Brand> getBrandList() {
		return brandList;
	}
	
	//Search a keyword from a product or brand
	public List<Brand> searchProduct(String productName) {
		if(brandList.isEmpty()){
			return null;
		}
		List<Brand> newBrandList = new ArrayList<Brand> ();
		
		for(Brand b : brandList) {			
			if(b.getName().toLowerCase().contains(productName.toLowerCase())) // Search for brand name
				newBrandList.add(b);
			else{															//when no brand name exist with the entered keyword it search the product 
				LinkedList<Product> l = b.searchtProduct(productName);
				if(!l.isEmpty()){
					Brand bra = new Brand(b.getName());
					
					for(Product p : l) {
						bra.addProduct(p);
					}
					newBrandList.add(bra);
				}
			}
		}		
		
		return newBrandList;
	}
	
	//updating brand name
	public boolean updateBrandName(int id, String name) {
		for(Brand b : brandList) {	
			if(b.getId() == id)
			{
				b.setName(name);
				return true;
			}
		}
		return false;
	}
	
	//Updating a product info
	public boolean updateProduct(int id, HashMap<String,String> productInfo) {
		for(Brand b : brandList) {	
			Product p = b.getProduct(id);
			if(p != null) {
				if(p.getID() == id) {
					
					if(!productInfo.get("name").isEmpty() && !p.getName().equals(productInfo.get("name")))	p.setName(productInfo.get("name"));
					Double value = Double.valueOf(String.valueOf(productInfo.get("value")));
					if(!p.getValue().equals(value)) {
						p.setValue(value);
					}
					int duration = Integer.valueOf(String.valueOf(productInfo.get("duration")));
					if(p.getDuration() != duration) {
						p.setDuration(duration);
					}
					if(!productInfo.get("platform").isEmpty() && !p.getPlatform().equals(productInfo.get("platform")))	p.setPlatform(productInfo.get("platform"));
					if(!productInfo.get("country").isEmpty() && !p.getCountry().equals(productInfo.get("country")))	p.setCountry(productInfo.get("country"));
				}
			return true;
			}
		}
		return false;
	}
	
	
	public boolean removeProduct(int id) {
		for(int i = 0; i < brandList.size(); i++) {		
			Product p =  brandList.get(i).getProduct(id);
				if(p != null && p.getID() == id) {		
					//remove product with same id
					boolean removed = brandList.get(i).removeProduct(id);
					// when brand does not contain product remove brand
					if(brandList.get(i).getList().size() == 0) { 
						brandList.remove(i);
					}
					return removed;
				}
		}
		return false;
	}
}
