package assignment;

public abstract class product {
	private int itemNumber;
	private String productName;
	private int quantityAvailable;
	private double price;
	private boolean status=true; //activate by default
	
	//constructor
	public product(int itemNumber,String productName, int quantityAvailable, double price) {
		this.itemNumber = itemNumber;
		this.productName= productName;
		this.quantityAvailable=quantityAvailable;
		this.price=price;
	}
	
	public product() {
		this.itemNumber= 0;
		this.productName=" ";
		this.quantityAvailable= 0;
		this.price= 0.0;
	}
	
	//getter and setters
	public int getItemNumber() {
		return itemNumber;
	}
	
	public void setItemNumber(int itemNumber) {
		this.itemNumber=itemNumber;
	}
	
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName=productName;
	}
	
	public int getQuantity() {
		return quantityAvailable;
	}
	
	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable=quantityAvailable;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price=price;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status=status;
	}
	
	//calculate inventory value
	public double getInventoryValue() {
		return price*quantityAvailable;
	}
	
	//add stock
	public void addStock(int quantity) {
		if(!status) {
			System.out.println("Cannot add stock, product is discontinued.");
			return;
		}
		if(quantity<=0) {
			System.out.println("invalid quantity.");
			return;
		}
		quantityAvailable+=quantity;
		
	}
	
	
	//deduct stock
	public boolean deductStock(int quantity) {
		if (!status) {
		    System.out.println("Cannot deduct stock, product is discontinued.");
		    return false;
		}
		
		if(quantity<=0) {
			System.out.println("Invalid quantity");
			return false;
		}
		
		if(quantity>quantityAvailable) {
			System.out.println("Not enough stock.");
			return false;
		}
		quantityAvailable-=quantity;
		return true;
	}
	
	
	//toString
	public String toString() {
		
		String productStatus;
		
		if(status==true) {
			productStatus="Available";
		}
		else {
			productStatus="Not Available";
		}
		return "Item number: " + itemNumber + "\n" + "Product name: " + productName + "\n" + "Quantity Available: " + quantityAvailable + "\n" + "Price: " + price + "\n" + "Status: " + productStatus ; 
	}
	

}
