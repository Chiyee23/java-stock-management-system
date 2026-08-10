package assignment;

public class Refrigerator extends product{
	private String doorDesign;
	private String color;
	private double capacity;
	
	//Constructor
	public Refrigerator(int itemNumber, String name, int quantity, double price, 
			String doorDesign, String color, double capacity) {
		super(itemNumber, name, quantity, price);
		this.doorDesign = doorDesign;
		this.color = color;
		this.capacity = capacity;
	}
	
	//Setter
	public void setDoorDesign (String doorDesign){
		this.doorDesign = doorDesign;
	}
	public void setColor (String color){
		this.color = color;
	}
	public void setCapacity (double capacity){
		this.capacity= capacity;
	}
	
	//Getter
	public String getDoorDesign(){
		return doorDesign;
	}
	public String getColor(){
		return color;
	}
	public double getCapacity(){
		return capacity;
	}
	
	public double calculateStockValue() {
		return getInventoryValue();
	}

	@Override
	public String toString(){
		return "Item number         : " + getItemNumber() + 
				"\nProduct name        : " + getProductName() +
				"\nDoor design         : " + doorDesign +
				"\nColor               : " + color +
				"\nCapacity (in Litres): " + capacity +
				"\nQuantity available  : " + getQuantity() +
				"\nPrice (RM)          : " + getPrice() +
				"\nInventory value (RM): " + calculateStockValue() +
				"\nProduct status      : " + (isStatus() ? "Active" : "Discontinued");
	}	

}

