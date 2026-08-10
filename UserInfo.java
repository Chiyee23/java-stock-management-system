package assignment;
import java.util.Scanner;

public class UserInfo {
	//Declare variables
		private String name;
		private String userID;
		
		public String getUserID() {
			return userID;
		}
		
		public String getFullName() {
			return name;
		}
		
		//Get name method
		public void getName()
		{
			Scanner input = new Scanner(System.in);
			System.out.println("Enter first name and surname: ");
			name = input.nextLine();
		}
		
		//Check if name contains space method
		public boolean hasSpace()
		{
			return name.contains(" ");
		}
		
		//Generate user ID method
		public void generateUserID()
		{
			if (hasSpace())
			{
				String[] parts = name.split(" ");
				String firstName = parts[0];
				String surname = parts[parts.length - 1];
				userID = firstName.substring(0,1).toUpperCase() + surname.substring(0,1).toUpperCase() + surname.substring(1);
			}
			else
			{
				userID = "guest";
			}
		}
		
		//Display output method
		public void display()
		{
			System.out.println("Full name: " + name);
			System.out.println("User ID: " + userID);
		}
		
		public static void main(String[] args)
		{
			UserInfo user = new UserInfo();
			user.getName();
			user.generateUserID();
			user.display();
		}

}
