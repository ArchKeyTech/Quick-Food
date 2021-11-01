
/**
 * This program creates a Restaurant object based on Customer's class,
 * asks for meal order from customer and returns an invoice with details
 * obtained from the customer. It stores the orders and new customers within 
 * the QuickFoodMS database
 */


/**
 * import necessary packages
 */
import java.util.Scanner;
import java.sql.*;



/**
 * Declaring the Main class where the Customer, Restaurant and SQL_Functions objects are called
 * to execute the complete program
 * @author dan-sampai
 *
 */
public class Main {

	/**
	 * declaring main method to run the program
	 * @param args
	 */
	public static void main(String[] args) {
		
		//initialize url, username and password
		String url = "jdbc:mysql://localhost:3306/QuickFoodMS";
		String uname = "root";
		String password = "password";
		
		/**
		 * try and catch statement to ensure connection to the database is established
		 */
		try {
			//declare variable to store number of rows changed by query
			int rowsAffected;
			// Create a direct line to the database for running our queries
			Connection connection = DriverManager.getConnection(url, uname, password);
			//initialize statement by allocating a Statement object inside the Connection
			Statement statement = connection.createStatement();
			
			//query to create restaurant table
			String restaurant_table = "CREATE TABLE restaurant ("
					+ "restaurant_id INT AUTO_INCREMENT,"
					+ "restaurant_name VARCHAR(30),"
					+ "restaurant_location VARCHAR(30),"
					+ "restaurant_number VARCHAR(10),"
					+ "PRIMARY KEY(restaurant_id))";
			
			//query to create menus table
			String menus_table = "CREATE TABLE menus("
					+ "restaurant_id INT,"
					+ "meal VARCHAR(30),"
					+ "cost DOUBLE(6,2),"
					+ "FOREIGN KEY(restaurant_id) REFERENCES restaurant(restaurant_id))";
			
			//query to create customer table
			String customer_table = "CREATE TABLE customer ("
					+ "customer_id INT AUTO_INCREMENT,"
					+ "customer_name VARCHAR(30),"
					+ "customer_address VARCHAR(50),"
					+ "customer_location VARCHAR(30),"
					+ "customer_email VARCHAR(30),"
					+ "customer_number VARCHAR(10),"
					+ "PRIMARY KEY(customer_id))";
			
			//query to create driver table
			String driver_table = "CREATE TABLE driver ("
					+ "driver_id INT AUTO_INCREMENT,"
					+ "driver_name VARCHAR(30),"
					+ "driver_location VARCHAR(30),"
					+ "driver_orders INT,"
					+ "PRIMARY KEY(driver_id))";
			
			//query to create orders table
			String orders_table = "CREATE TABLE orders("
					+ "order_number INT AUTO_INCREMENT,"
					+ "restaurant_id INT,"
					+ "instruction VARCHAR(30),"
					+ "total_amount DOUBLE(6,2),"
					+ "customer_id INT,"
					+ "driver_id INT,"
					+ "PRIMARY KEY(order_number),"
					+ "FOREIGN KEY(restaurant_id) REFERENCES restaurant(restaurant_id),"
					+ "FOREIGN KEY(driver_id) REFERENCES driver(driver_id),"
					+ "FOREIGN KEY(customer_id) REFERENCES customer(customer_id))";
			
			//query to create order_detail table
			String order_detail_table = "CREATE TABLE order_detail("
					+ "order_number INT,"
					+ "meal VARCHAR(30),"
					+ "qty INT,"
					+ "cost DOUBLE(6,2),"
					+ "FOREIGN KEY(order_number) REFERENCES orders(order_number))";
			
	
			/**
			 * try and catch statement to create tables in database
			 */
			try {
				//create restaurant table
				statement.executeUpdate(restaurant_table);
				//print that table has been created
				System.out.println("'restaurant' table created in database 'QuickFoodMS'");
				
				//create menus table
				statement.executeUpdate(menus_table);
				//print that table has been created
				System.out.println("'menus' table created in database 'QuickFoodMS'");
				
				//create customer table
				statement.executeUpdate(customer_table);
				//print that table has been created
				System.out.println("'customer' table created in database 'QuickFoodMS'");
				
				//create driver table
				statement.executeUpdate(driver_table);
				//print that table has been created
				System.out.println("'driver' table created in database 'QuickFoodMS'");
				
				//create orders table
				statement.executeUpdate(orders_table);
				//print that table has been created
				System.out.println("'orders' table created in database 'QuickFoodMS'");
				
				//create order_detail table
				statement.executeUpdate(order_detail_table);
				//print that table has been created
				System.out.println("'order_detail' table created in database 'QuickFoodMS'");
				
				
			}
			/**
			 * catch statement incase table already exists
			 */
			catch (SQLException e){
				System.out.println("tables already created\n\n\n");
			}
			//initialize resultset to execute SELECT query to check if rows in tables are present, meaning the tables have entries
			ResultSet rs = statement.executeQuery("SELECT *FROM restaurant");
			
			/**
			 * if the 1st row is empty, insert the data within the tables
			 */
			if (rs.next() == false) { 
				rowsAffected = statement.executeUpdate("INSERT INTO restaurant VALUES(1000, 'Ichiraku Ramen', 'Cape Town', '0812456624')");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'restaurant'.");
				rowsAffected = statement.executeUpdate("INSERT INTO restaurant VALUES(1001, 'Ocean Basket', 'Durban', '0791355711')");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'restaurant'.");
				
				rowsAffected = statement.executeUpdate("INSERT INTO menus VALUES(1000, 'Beef noodle', 75.00)");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'menus'.");
				rowsAffected = statement.executeUpdate("INSERT INTO menus VALUES(1001, 'Hake & Chips', 125.00)");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'menus'.");
				
				rowsAffected = statement.executeUpdate("INSERT INTO customer VALUES(3000, 'Aubrey Graham', 'Green Street Clement Ave', 'Cape Town', 'aubreyg@gmail.com', '0846998525')");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'customer'.");
				rowsAffected = statement.executeUpdate("INSERT INTO customer VALUES(3001, 'Malcolm McCormick', 'BlueSlide Park', 'Durban', 'macmill@gmail.com', '0722013432')");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'customer'.");
				
				rowsAffected = statement.executeUpdate("INSERT INTO driver VALUES(2000, 'Julie Carty', 'Cape Town', 6)");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'driver'.");
				rowsAffected = statement.executeUpdate("INSERT INTO driver VALUES(2001, 'Karol Dunn', 'Durban', 4)");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'driver'.");
				
				
				rowsAffected = statement.executeUpdate("INSERT INTO orders VALUES(1, 1000, 'Extra soy sauce', 75.00, 3000, 2000)");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'orders'.");
				rowsAffected = statement.executeUpdate("INSERT INTO orders VALUES(2, 1001, 'lemons on the side', 125.00, 3001, 2001)");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'orders'.");

				rowsAffected = statement.executeUpdate("INSERT INTO order_detail VALUES(1, 'Beef noodle', 1, 75.00)");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'order_detail'.");
				rowsAffected = statement.executeUpdate("INSERT INTO order_detail VALUES(2, 'Hake & Chips', 1, 125.00)");
				System.out.println("Query complete, " + rowsAffected + " rows added to 'order_detail'.");
				
				
				//printAllFromTable(statement);
				}
			//close resultset
			rs.close();
			
			//create SQL_Functions object to call its respective methods
			SQL_Functions func = new SQL_Functions();
			//initialize scanner to take user inputs
			Scanner input = new Scanner(System.in);
			
			//declare option variable
			int option;
			
			/**
			 * do-while statement to request option from user
			 * until certain condition is met
			 */
			do {
				//ask user to select an option to manipulate the database
				System.out.println("Quick-Food Data Managament System:\n\nSelect an option (Enter number):\n\n 1. Take New Order\n 2. Update Existing Customer\n 3. Search Existing Order\n 0. Exit\n");
				
				//initialize the option variable to get user's option
				option = Integer.parseInt(input.nextLine());
				
				/*
				 * switch statement to consider different options outputs/outcomes
				 */
				switch(option) {
				//take new order
				case 1:
					//call takeOrder function
					func.takeOrder(statement, connection, input);
					//call printAllFromTable to show table
					//printAllFromTable(statement);
					//break from switch statement
					break;
					
				//update customer
				case 2:
					//call updateCustomer function
					func.updateCustomer(statement, input);
					//call printAllFromTable to show table
					//printAllFromTable(statement);
					//break from switch statement
					break;
					
				//search for order
				case 3:
					//call searchOrder function
					func.searchOrder(statement, connection, input);
					//call printAllFromTable to show table
					//printAllFromTable(statement);
					//break from switch statement
					break;
					
					//exit 
				case 0:
					//print that program has ended
					System.out.println("Program exited");
					//close scanner object
					input.close();
					//break from switch statement
					break;
				
				//default statement if above conditions are not met
				default:
					//warn user that invalid number was entered
					System.out.println("You have entered an invalid number");
				}
				
			}
			/**
			 * repeat the block of code above until option = 0, which means program is exited
			 */
			while(option != 0 );

			
		}
		/**
		 * catch statement to ensure SQLExceptions are handled
		 */
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	
	}
	
	
}

