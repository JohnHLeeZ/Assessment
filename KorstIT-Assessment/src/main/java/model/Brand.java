package model;

import java.util.LinkedList;
import java.util.List;


public class Brand {
	private String brand;
	private static int idCount = 0;
	private int brandId = -1;
	private LinkedList<Product> listProduct;
	
	public Brand(String name) {
		listProduct = new LinkedList<Product>();
		this.brand = name;
		if(brandId == -1) { 
			brandId = idCount;
			idCount++;
		}
	
	}
	
	public void setName(String name) {
		this.brand = name;
	}
	
	public String getName() {
		return brand;
	}
	public int getId() {
		return brandId;
	}
	
	public void addProduct(Product product) {
		listProduct.add(product);
	}
	
	public List<Product> getList(){
		return listProduct;
	}
	
	public LinkedList<Product> searchtProduct(String name) {
		if(listProduct.isEmpty()){
			return null;
		}
		LinkedList<Product>newProductList = new LinkedList<Product>();
		for(Product p : listProduct) {			
			if(p.getName().toLowerCase().contains(name.toLowerCase()))
				newProductList.add(p);
		}		

		return newProductList;
	}
	
	public Product getProduct(int id) {
		for(Product p : listProduct) {		
			if(p.getID() == id) return p;
		}
		
		return null;
	}
	public boolean removeProduct(int id) {
		for(int i = 0; i < listProduct.size(); i++) {
			if(listProduct.get(i).getID() == id) {
				listProduct.remove(i);
				return true;
			}
		}
		return false;
	
	}

}
