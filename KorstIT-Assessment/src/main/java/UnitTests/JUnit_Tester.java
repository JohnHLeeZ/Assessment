package UnitTests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.everyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class JUnit_Tester {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8081;	
	}

	@Test
	public void addProductsTest() {
		addProduct("Google", "GooglePlay", 50.0, 3000, "Android", "Netherlands");
		addProduct("Google", "GooglePlay", 80.0, 3000, "Android", "Netherlands");
		addProduct("Apple", "Itunes", 20.0, 3000, "Apple", "Netherlands");
		addProduct("Apple", "Itunes", 50.0, 3000, "Apple", "Netherlands");
		addProduct("Mars", "Quantocom", 100.0, 3000, "1D", "Antartica");
	}
	

	
	@Test
	public void showProductTest() {
		given().when().get("KorstIT-Assessment/rest/service/list").then().body("name",hasItem("Google"));//show brand
		given().when().get("KorstIT-Assessment/rest/service/list").then().body("name",hasItem("Apple"));//show brand	}
	}
	
	@Test
	public void searchProductTest() {

		addProduct("SearchUnit", "SearchObject", 150.0, 10000, "PC", "Laos");//product for searching 
		given().when().get("KorstIT-Assessment/rest/service/searchProduct/chObj").then().body("list.name", everyItem(hasItem("SearchObject")));//search product
		given().when().get("KorstIT-Assessment/rest/service/searchProduct/chUni").then().body("name", hasItem("SearchUnit"));//search part of brand

	}
	@Test
	public void updateProduct() {
		addProduct("TestUnit", "proUpdate", 150.0, 10000, "PC", "Laos");
		Map<String,String> product = new HashMap<String, String>();
		product.put("name", "proUpdate");
		product.put("value", "99.99");
		product.put("duration", "11111");
		product.put("platform", "Switch");
		product.put("country", "Kenya");
		
		//get id from product
		String  id = get("KorstIT-Assessment/rest/service/searchProduct/proUpdate").path("list.id").toString();
		id = id.replace("[", "").replace("]", "");//remove the arrays
		
		given()
		.contentType("application/json")
		.body(product)
		.when().post("/KorstIT-Assessment/rest/service/" + id + "/updateProduct").then()
        .body(containsString("Product Succesfully changed"));
		
		//removing item for the sake of repeatable testing
		given()
		.contentType("application/json")
		.when().post("/KorstIT-Assessment/rest/service/" + id + "/removeProduct").then()
        .body(containsString("Product Succesfully removed"));
	}
	
	@Test
	public void removeProduct() {
		addProduct("RemovableProduct", "RRB", 122.0, 1000, "PC", "Lich");
		
		Map<String,String> product = new HashMap<String, String>();
		product.put("name", "RemovableBrand");		
		//get id from brand
		String  id = get("KorstIT-Assessment/rest/service/searchProduct/RemovableProduct").path("list.id").toString();
		id = id.replace("[", "").replace("]", "");//remove the arrays
		
		given()
		.contentType("application/json")
		.body(product)
		.when().post("/KorstIT-Assessment/rest/service/" + id + "/removeProduct").then()
        .body(containsString("Product Succesfully removed"));
	}
	
	@Test
	public void updateBrand() {
		addProduct("ChangeableBrand", "CDSerial", 122.0, 1000, "PC", "Laos");
		
		Map<String,String> product = new HashMap<String, String>();
		product.put("name", "NewBra");		
		//get id from brand
		String  id = get("KorstIT-Assessment/rest/service/searchProduct/ChangeableBrand").path("id").toString();
		id = id.replace("[", "").replace("]", "");//remove the arrays
		
		given()
		.contentType("application/json")
		.body(product)
		.when().post("/KorstIT-Assessment/rest/service/" + id + "/updateBrandName").then()
        .body(containsString("Brand Succesfully changed"));
		

	}
	

	
	public void addProduct(String brand, String name, Double value, int duration, String platform, String country) {
		Map<String,String> product = new HashMap<String, String>();
		product.put("name", name);
		product.put("value", value.toString());
		product.put("duration", String.valueOf(duration));
		product.put("platform", platform);
		product.put("country", country);
		ArrayList<Object> ar = new ArrayList<Object>(); 
		ar.add(product);
		
		Map<String,Object> brandMap = new HashMap<String, Object>();
		brandMap.put("name", brand);
		brandMap.put("list", ar);
		given()
		.contentType("application/json")
		.body(brandMap)
		.when().post("KorstIT-Assessment/rest/service/addProduct").then()
        .body(containsString("Product Succesfully added"));
	}
}
