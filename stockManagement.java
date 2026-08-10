package assignment;

import java.util.*;

public class stockManagement {
	static product[] products;
	static int productCount = 0; 
	
	//Input validation for integer data type variables
    public static int validNextInt(Scanner sc) {
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a whole number.");
                System.out.print("Try again: ");
            }
        }
    }
 
    // Input validation for double data type variables
    public static double validNextDouble(Scanner sc) {
        while (true) {
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
                System.out.print("Try again: ");
            }
        }
    }
	
	//Get maximum products
	public static int getMaxProducts(Scanner sc) {
	int Max; 
	do {
		System.out.print("Enter number of products to store:");
		Max = validNextInt(sc);
		if (Max < 0 )
			System.out.println("Enter 0 or positive number only!");
	}while (Max < 0);
	return Max; 
	}
	
	public static int selectProduct(product[] products, Scanner sc) {
		if (productCount ==0) {
			System.out.println("No products available!");
			return -1;
		}
	
	
	for (int i = 0; i< productCount; i++) {
		System.out.println(i + "-"+products[i].getProductName()); 
	
	}
	
	int choice; 
	do {
		System.out.print("Select product index:"); 
		choice = validNextInt(sc);
		
		if (choice < 0 || choice >= productCount)
            System.out.println("Invalid index! Enter between 0 and " + (productCount - 1));
		
	} while (choice<0 || choice >= productCount);
	
	return choice;
	
	}
	
	//menu display
	public static int menu(Scanner sc) {
		int choice;
		do {
			System.out.println("1. View products");
			System.out.println("2. Add stocks");
			System.out.println("3. Deduct stock");
			System.out.println("4. Discontinue product");
			System.out.println("0. Exit");
			System.out.println("Enter choice: ");
			choice=sc.nextInt();
			
			if(choice < 0 || choice > 4)
				System.out.println("Invalid choice! Enter a number between 0 and 4.");
		}
		while (choice<0||choice>4);
		return choice;
	}
	
	// 4️. Add stock
    public static void addStock(product[] products, Scanner sc){
        int index = selectProduct(products, sc);
        if(index == -1) return;

        int qty;
        do{
            System.out.print("Enter quantity to add: ");
            qty = validNextInt(sc);
            if(qty < 0)
            	System.out.println("Quantity must be 0 or greater.");
            
        }while(qty < 0);

        products[index].addStock(qty);
    }

    // 5️. Deduct stock
    public static void deductStock(product[] products, Scanner sc){
        int index = selectProduct(products, sc);
        if(index == -1) return;

        int qty;
        do{
            System.out.print("Enter quantity to deduct: ");
            qty = validNextInt(sc);
            if(qty < 0)
            	System.out.println("Quantity must be 0 or greater.");
        }while(qty < 0 );

        products[index].deductStock(qty);
    }

    // 6️. Discontinue product
    public static void discontinueProduct(product[] products, Scanner sc){
        int index = selectProduct(products, sc);
        if(index == -1) return;

        products[index].setStatus(false);
        System.out.println("Product discontinued successfully.");
    }

    // 7️. Add Refrigerator OR TV OR Washing Machine
    public static void addProduct(product[] products, Scanner sc){
        if(productCount >= products.length){
            System.out.println("Product list FULL !");
            return;
        }

        int type;
        do{
            System.out.println("1. Refrigerator");
            System.out.println("2. TV");
            System.out.println("3. Washing Machine");
            System.out.print("Select product type: ");
            type = validNextInt(sc);
            if(type < 1 || type > 3)
                System.out.println("Only number 1, 2, or 3 allowed!");
        }while(type < 1 || type > 3);

        switch (type) {
        case 1: addRefrigerator(products, sc); break;
        case 2: addTV(products, sc);           break;
        case 3: addWashingMachine(products, sc); break;
        }
    }

    // 8️. Add Refrigerator
    public static void addRefrigerator(product[] products, Scanner sc){
        sc.nextLine(); // clear buffer

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Door design: ");
        String door = sc.nextLine();

        System.out.print("Color: ");
        String color = sc.nextLine();

        System.out.print("Capacity: ");
        double cap = validNextDouble(sc);

        System.out.print("Quantity: ");
        int qty = validNextInt(sc);

        System.out.print("Price: ");
        double price = validNextDouble(sc);

        System.out.print("Item number: ");
        int item = validNextInt(sc);

        products[productCount++] =
            new Refrigerator(item,name,qty,price,door,color,cap);
        System.out.println("Refrigerator added successfully!");
    }

    // 9️. Add TV
    public static void addTV(product[] products, Scanner sc){
        sc.nextLine(); // clear buffer

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Screen type: ");
        String screen = sc.nextLine();

        System.out.print("Resolution: ");
        String res = sc.nextLine();

        System.out.print("Display size: ");
        double size = validNextDouble(sc);

        System.out.print("Quantity: ");
        int qty = validNextInt(sc);

        System.out.print("Price: ");
        double price = validNextDouble(sc);

        System.out.print("Item number: ");
        int item = validNextInt(sc);

        products[productCount++] =
            new TV(item,name,qty,price,screen,res,size);
        System.out.println("TV added successfully!");
    }

    // 10. Add Washing Machine
    public static void addWashingMachine(product[] products, Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
 
        System.out.print("Load Type (e.g. Front load / Top load): ");
        String load = sc.nextLine();
 
        System.out.print("Spin speed (RPM): ");
        int spin = validNextInt(sc);
 
        System.out.print("Capacity (kg): ");
        int cap = validNextInt(sc);
 
        System.out.print("Quantity: ");
        int qty = validNextInt(sc);
 
        System.out.print("Price (RM): ");
        double price = validNextDouble(sc);
 
        System.out.print("Item number: ");
        int item = validNextInt(sc);
 
        products[productCount++] = new WashingMachine(item, name, qty, price, load, spin, cap);
        System.out.println("Washing Machine added successfully!");
    }
    
    // 11. View all products
    public static void viewProducts(product[] products){
        if(productCount == 0){
            System.out.println("No products to display.");
            return;
        }

        for(int i=0;i<productCount;i++){
            System.out.println(products[i]); // calls toString()
        }
    }

    // 1️2. Execute menu using switch-case
    public static void executeMenu(int choice, product[] products, Scanner sc){
        switch(choice){
            case 1: viewProducts(products); break;
            case 2: addStock(products, sc); break;
            case 3: deductStock(products, sc); break;
            case 4: discontinueProduct(products, sc); break;
        }
    }

    // 1️3. MAIN METHOD
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println ("============================================");
        System.out.println ("   Welcome to the Stock Management System   ");
        System.out.println ("============================================");
        
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter fmt = 
        		java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Date and Time: " + now.format(fmt));
        
        System.out.println("\nGroup Members:");
        System.out.println("1. Cheng Qin yi");
        System.out.println("2. Guok Siok Wen");
        System.out.println("3. Toh Ke Xuan");
        System.out.println("4. Wong Chi Yee");
        System.out.println("==============================================\n");
        
        UserInfo user = new UserInfo();
        user.getName();
        user.generateUserID();
        
        int numToAdd = getMaxProducts(sc);
        product[] products = new product[numToAdd];
        if (numToAdd == 0) {
        	System.out.println("No product added. Exiting...");
        }
        else {
        	for (int i = 0; i<numToAdd; i++) {
        		System.out.println("\nAdding product " + (i + 1) + " of " + numToAdd);
        		addProduct(products,sc);
        	}
        	int choice;
        	do{
        		choice = menu(sc);
        		executeMenu(choice, products, sc);
        		}
        	while(choice != 0);
        }

        System.out.println ("\n============================================");
        System.out.println ("Thank you for using SMS!");
        System.out.println ("User ID : " + user.getUserID());
        System.out.println ("Name    : " + user.getFullName());
        System.out.println ("============================================");
        
        sc.close();
    }

}
