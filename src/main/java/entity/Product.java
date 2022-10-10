package entity;

public class Product {
	
	private Long id; // product_id
	
	private String productName; // product name
	
	private int price; // product price
	
	public Product(Long id, String product, int price) {
		this.id = id;
		this.productName = productName;
		this.price = price;
	}
	
	public Product() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
}
