package assignment;

public class WashingMachine extends product
{
	private String LoadType;
	private int SpinSpeed;
	private int Capacity;
	
	//Constructor
	public WashingMachine(int itemNumber, String name, int quantity, double price,
            String LoadType, int SpinSpeed, int Capacity)
	{
		super(itemNumber, name, quantity, price);
		this.LoadType = LoadType;
		this.SpinSpeed = SpinSpeed;
		this.Capacity = Capacity;
	}
	
	//Getters
	public String getLoadType()
	{
		return LoadType;
	}
	
	public int getSpinSpeed()
	{
		return SpinSpeed;
	}
	public double getCapacity()
	{
		return Capacity;
	}
	
	//Setters
	public void setLoadType(String LoadType)
	{
		this.LoadType = LoadType;
	}
	
	public void setSpinSpeed(int SpinSpeed)
	{
		this.SpinSpeed = SpinSpeed;
	}
	
	public void setLoadSize(int Capacity)
	{
		this.Capacity = Capacity;
	}
	
	//calculate stock value
	public double calculateStockValue()
	{
		return getInventoryValue();
	}
	
	@Override
	public String toString()
	{
		return "Item number : " + getItemNumber() + 
				"\nProduct name : " + getProductName() + 
				"\nLoad type : " + LoadType + 
				"\nSpin speed (RPM): " + SpinSpeed + 
				"\nLoad size (kg): " + Capacity + 
				"\nQuantity available : " + getQuantity() + 
				"\nPrice (RM) : " + getPrice() + 
				"\nInventory value (RM) : " + calculateStockValue() + 
				"\nProduct status : " + (isStatus()? "Active" : "Discontinued");
	}

}
