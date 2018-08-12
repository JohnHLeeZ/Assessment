package model;


public class Product{

	private String name;
	private Double value;
	private int duration;
	private String platform;
	private String country;
	private static int idCount = 100000;
	private int productId = -1;
	
	public Product() {
		
	}
	public Product(String name, Double value, int duration, String platform, String country) {
		this.name = name;
		this.value = value;
		this.duration = duration;
		this.platform = platform;
		this.country = country;
		if(productId == -1) { 
			productId = idCount;
			idCount++;
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getID() {
		return productId;
	}
	public String getName() {
		return name;
	}
	
	public Double getValue() {
		return value;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public String getPlatform() {
		return platform;
	}
	
	public String getCountry() {
		return country;
	}
	
	
	public String toString(){
		return name+"::"+value+"::"+duration+"::"+platform+"::"+country;
	}
	
}
