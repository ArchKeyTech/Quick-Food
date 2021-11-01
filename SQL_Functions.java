
/**
 * importing necessary packages
 */
import java.sql.*;
import java.util.*;
import java.math.*;
/**
 * Class to store all functions to control QuickFoodMS database
 */
public class SQL_Functions {

	/**
	 * Object to be called in main program to use functions
	 */
	public SQL_Functions () {
		
	}
	
	/**
	 * Declaring insertCustomer method to insert new customer in database
	 * @param statement
	 * @param customername
	 * @param customeraddress
	 * @param customerlocation
	 * @param customeremail
	 * @param customernumber
	 * @throws SQLException
	 */
	public void insertCustomer(Statement statement, String customername, String customeraddress,
			String customerlocation, String customeremail, String customernumber) throws SQLException {
		
		
		//declare variable to store number of executed statements
		int rowsAffected;
		
		//initialize variable to store mysql command to insert new toy
		String insert =String.format("INSERT INTO customer(customer_name, customer_address, customer_location, customer_email, customer_number) VALUES('%s', '%s', '%s', '%s', '%s')", customername, customeraddress, customerlocation, customeremail, customernumber);  
		//initialize statement to be executed
		rowsAffected = statement.executeUpdate(insert);
		//print out statement
		System.out.println("Query complete, " + rowsAffected + " new customer added.\n");
		

	}
	
	/**
	 * method to insert order in order table
	 * @param statement
	 * @param con
	 * @param restaurant_id
	 * @param instruction
	 * @param total_amount
	 * @param customer_id
	 * @param driver_name
	 * @throws SQLException
	 */
	public void insertOrder(Statement statement, Connection con, int restaurant_id, String instruction,
			Double total_amount, int customer_id, String driver_name) throws SQLException {
		
		//storing query for driver ID in string variable
		String driverid_query = String.format("SELECT driver_id FROM driver WHERE driver_name = '%s'", driver_name);
		//resultset object to execute query to fetch driver ID
		ResultSet results = statement.executeQuery(driverid_query);
		
		//store driver ID value in int variable
		int driver_id = 0;
		
		/**
		 * while loop to extract the driver ID from the driver's table
		 */
		while(results.next()) {
			driver_id = results.getInt("driver_id");
		}
		//close the resultset 
		results.close();
		
		//declare variable to store number of executed statements
		int rowsAffected;
		
		//initialize variable to store sql command to insert new order
		String insert = "INSERT INTO orders(restaurant_id, instruction, total_amount, customer_id, driver_id) VALUES(?, ?, ?, ?, ?)";
		//precompiled statement for the insert query
		PreparedStatement pstmt = con.prepareStatement(insert);
		//setting the missing values of the insert query
		pstmt.setInt(1, restaurant_id);
		pstmt.setString(2, instruction);
		pstmt.setDouble(3, total_amount);
		pstmt.setInt(4, customer_id);
		pstmt.setInt(5, driver_id);
		
		//executing the insert statement
		rowsAffected = pstmt.executeUpdate();

		//print out statement
		System.out.println("\n" + rowsAffected + " new order added.\n");
		

	}
	
	
	/**
	 * Declaring updateCustomer method which will modify a customer chosen by the user
	 * @param statement
	 * @param input
	 * @throws SQLException
	 */
	public void updateCustomer(Statement statement, Scanner input) throws SQLException {
					
			//ask user to enter name of customer they want to update
			System.out.println("Enter name of customer to update:");
			//store name of customer to be updated
			String oldcustomer_name = input.nextLine();
			
			//ask user to choose attribute of customer they want to edit
			System.out.println("Which attribute of the customer would you like to update (Enter number)?:\n\n 1. Customer Name\n 2. Customer Address\n 3. Customer Location\n 4. Customer Email\n 5. Customer Number\n");
			int attribute = Integer.parseInt(input.nextLine());
			
			//declare variable to store number of executed statements
			int rowsAffected;
			
			//declare variable to update customer
			String update;
			
			/**
			 * switch statement to evaluate different scenarios based on selected attribute
			 */
			switch(attribute) {
			
			//update name
			case 1:
				//ask user to enter new name for customer
				System.out.println("\n Enter new name :");
				//store new toy's name
				String newcustomer_name = input.nextLine();
				
				//initialize variable to store mysql command to update customer's name
				update = String.format("UPDATE customer SET customer_name = '%s' WHERE customer_name = '%s'" , newcustomer_name, oldcustomer_name); 
				
				//initialize statement to be executed
				rowsAffected = statement.executeUpdate(update);
				
				//print out statement to indicate customer's name has been updated
				System.out.println("Query complete, " + rowsAffected + " customer's name updated.\n");
				//break from switch statement
				break;
			
			//update address
			case 2:
				//ask user to enter new address
				System.out.println("\n Enter new address of customer:");
				//store new customer's address
				String address = input.nextLine();
				
				//initialize variable to store mysql command to update customer's address
				update = String.format("UPDATE customer SET customer_address = '%s' WHERE customer_name = '%s'", address, oldcustomer_name); 
				
				//initialize statement to be executed
				rowsAffected = statement.executeUpdate(update);
				
				//print out statement indicating that customer's address was updated
				System.out.println("Query complete, " + rowsAffected + " customer's address updated.\n");
				//break from switch statement
				break;
				
			//update location
			case 3:
				
				//ask user to enter new customer location
				System.out.println("\n Enter new location/city of customer:");
				//store new location
				String location = input.nextLine();
				
				//initialize variable to store mysql command to update customer's location
				update = String.format("UPDATE customer SET customer_location = '%s' WHERE customer_name = '%s'", location, oldcustomer_name);
				//initialize statement to be executed
				rowsAffected = statement.executeUpdate(update);
				
				//print out statement indicating that customer's location was updated
				System.out.println("Query complete, " + rowsAffected + " customer's location updated.\n");
				//break from switch statement
				break;
				
			
			//update email
			case 4:
				//ask user to enter new customer email
				System.out.println("\n Enter new email of customer:");
				//store new email
				String email = input.nextLine();
				
				//initialize variable to store mysql command to update customer's email
				update = String.format("UPDATE customer SET customer_email = '%s' WHERE customer = '%s'", email, oldcustomer_name);
				//initialize statement to be executed
				rowsAffected = statement.executeUpdate(update);
				
				//print out statement indicating that customer's email was updated
				System.out.println("Query complete, " + rowsAffected + " customer's email updated.\n");
				//break from switch statement
				break;
				
				
			//update number
			case 5:
				//ask user to enter new customer number
				System.out.println("\n Enter new number of customer:");
				//store new number
				String number = input.nextLine();
				
				//initialize variable to store mysql command to update customer's number
				update = String.format("UPDATE customer SET customer_number = '%s' WHERE customer = '%s'", number, oldcustomer_name);
				//initialize statement to be executed
				rowsAffected = statement.executeUpdate(update);
				
				//print out statement indicating that customer's number was updated
				System.out.println("Query complete, " + rowsAffected + " toy's quantity updated.");
				//break from switch statement
				break;
				
			//default statement if above conditions are not met
			default:
				//warn user that invalid number was entered
				System.out.println("You have entered an invalid number");
				
			}
			
			
		}
	
	/**
	 * Declare takeOrder method which takes in order details to store details within database
	 * @param statement
	 * @param connection
	 * @param input
	 * @throws SQLException
	 */
	public void takeOrder(Statement statement, Connection connection, Scanner input)throws SQLException{
		System.out.println("Welcome to Quick-Food's ordering portal !\n\n");
		
		
		//ask for customer's full name
		System.out.println("\nEnter Customer's full name:");
		//read customer's full name
		String customername = input.nextLine();
		
		//ask for customer's email
		System.out.println("\nEnter Customer's email:");
		//read customer's email
		String customeremail = input.nextLine();
		
		//ask for customer's number
		System.out.println("\nEnter Customer's number:");
		//read customer's number
		String customernumber = input.nextLine();
		
		//ask for customer's 
		System.out.println("\nEnter Customer's city:");
		//read customer's city
		String customerlocation = input.nextLine();
		
		//ask for customer's instructions
		System.out.println("\nEnter Customer's meal instructions:");
		//read customer's instructions
		String customerinstruction = input.nextLine();
		
		//ask for customer's address
		System.out.println("\nEnter Customer's address:");
		//read customer's address
		String customeraddress = input.nextLine();
		
		//ask for restaurant's name
		System.out.println("\nEnter Restaurant's name:");
		//read restaurant's name
		String restaurantname = input.nextLine();
		
		//ask for restaurant's city
		System.out.println("\nEnter Restaurant's city:");
		//read restaurant's city
		String restaurantlocation = input.nextLine();
		
		//ask for restaurant's number
		System.out.println("\nEnter Restaurant's number:");
		//read restaurant's number
		String restaurantnumber = input.nextLine();
		
		//creating Customer object
		Customer c = new Customer (customername, customeremail, customernumber, customerlocation, customerinstruction,
				customeraddress);
		
		//creating Restaurant object with inherited Customer's object attributes
		Restaurant restaurant = new Restaurant (c.getName(), c.getEmail(), c.getContactNumber(), c.getCustomerLocation(),
				c.getInstruction(), c.getAddress() , restaurantname, restaurantlocation, restaurantnumber);
		
		//calling insertCustomer method to add new customer based on info entered by user
		insertCustomer(statement, customername, customeraddress, customerlocation, customeremail, customernumber);
		
		//Calling the takeOrder method from the Restaurant class to obtain invoice
		restaurant.takeOrder(customername, statement, connection, input);
		
		//Calling addLoad() method from Restaurant class which updates the driver's load
		restaurant.addLoad(statement);
		
	
	}
	
	/**
	 * Declaring searchOrder method to search for specific order based on order number or customer name
	 * @param statement
	 * @param con
	 * @param input
	 * @throws SQLException
	 */
	public void searchOrder(Statement statement, Connection con, Scanner input) throws SQLException {
		
		//ask user whether they want to search for an order using order number or customer name
		System.out.println("Search order by:\n\n 1. Order number\n 2. Customer name");
		//store the user's option in int variable
		int option = Integer.parseInt(input.nextLine());
		
		//declare variable to search for order
		String search;
		//declare resultset to execute query
		ResultSet search_result;
		
		/**
		 * switch statement to evaluate different scenarios based on selected attribute
		 */
		switch(option) {
		
		//search order by order number
		case 1:
			//ask user to enter order number they want to find
			System.out.println("\n Enter order number :");
			//store order number entered by user
			int order_num = Integer.parseInt(input.nextLine());
			
			//initialize search variable to store mysql command to find order based on given order number
			search = String.format("SELECT * FROM orders WHERE order_number = %d", order_num); 
			//initialize resultset to execute the search query above
			search_result = statement.executeQuery(search);
			
			//while loop to extract next row from order table
			if (search_result.next()) {
				//print out the results from the query
				System.out.println("\n" +"Order found:\n\n"
						+search_result.getInt("order_number") + ", "
						+ search_result.getInt("restaurant_id") + ", "
						+ search_result.getString("instruction") + ", "
						+ "R " +search_result.getDouble("total_amount") + ", "
						+ search_result.getInt("customer_id") + ", "
						+ search_result.getInt("driver_id")+ "\n\n");
			}
			/**
			 * else, if no order is found
			 */
			else {
				System.out.println("Sorry, no order exists for this order number.\n\n");
			}
			//close search result
			search_result.close();
			//break from switch statement
			break;
					
		//search order by customer's name
		case 2:
			
			//ask user to enter name of customer with respective order number
			System.out.println("\nEnter name of customer to search:");
			//store name of customer to search
			String cust_name = input.nextLine();
			
			//initialize search variable to store mysql query to search for order with given name
			search = String.format("SELECT * FROM orders WHERE customer_id = ( "
					+ "SELECT customer_id "
					+ "FROM customer "
					+ "WHERE customer_name = '%s')", cust_name);
			
			//initialize statement to execute the above query
			search_result = statement.executeQuery(search);
			/**
			 * if results has lines to show from query extract each value
			 */
			if (search_result.next()) {
				//print out the result from the query
				System.out.println("\n" +"Order found:\n\n"
						+ search_result.getInt("order_number") + ", "
						+ search_result.getInt("restaurant_id") + ", "
						+ search_result.getString("instruction") + ", "
						+ "R " +search_result.getDouble("total_amount") + ", "
						+ search_result.getInt("customer_id") + ", "
						+ search_result.getInt("driver_id") + "\n\n");
			}
			/**
			 * else, if the name is not found
			 */
			else {
				System.out.println("Sorry, no order exists for this name.\n\n");
			}
			//close results
			search_result.close();
			
			break;
			
		default:
			//warn user that invalid number was entered
			System.out.println("You have entered an invalid number");
		}
	
	}
			
		
}
