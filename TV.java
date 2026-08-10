package assignment;

public class TV extends product
{
	private String screenType;
	private String resolution;
	private double displaySize;
	
	public TV(int itemNumber, String name, int quantity, double price, 
			String screenType, String resolution, double displaySize) 
	{
		super(itemNumber, name, quantity, price);
		this.screenType = screenType;
		this.resolution = resolution;
		this.displaySize = displaySize;
	}
	
	//Getters
	public String getScreenType() {
		return screenType;
	}
	
	public String getResolution() {
		return resolution;
	}
	
	public double getDisplaySize() {
		return displaySize;
	}
	
	//Setters
	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}
	
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	public void setDisplaySize(double displaySize) {
		this.displaySize = displaySize;
	}
	
	//Calculate stock value
	public double calculateStockValue() {
		return getInventoryValue();
	}
	
	@Override
	public String toString() {
		return "Item number : " + getItemNumber() + 
				"\nProduct name : " + getProductName() + 
				"\nScreen type : " + screenType + 
				"\nResolution : " + resolution + 
				"\nDisplay size : " + displaySize + 
				"\nQuantity available : " + getQuantity() + 
				"\nPrice (RM) : " + getPrice() + 
				"\nInventory value (RM) : " + calculateStockValue() + 
				"\nProduct status : " + (isStatus()? "Active" : "Discontinued");
	}

}
