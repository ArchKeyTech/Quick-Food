
/**
 * importing all necessary packages
 */
import java.io.*;
import java.util.*;
import java.sql.*;
import java.math.*;
import java.time.LocalDate;

/**
 * Class to represent a restaurant's order for each customer
 * It is inheriting from the Customer superclass
 * @author dan-sampai
 *
 */
public class Restaurant extends Customer {
	
	//declaring variables/attributes:
	
	//variable to store restaurant's name
	private String rest_name;
	//variable to store restaurant's location
	private String rest_location;
	//variable to store restaurant's number
	private String rest_number;
	//variable to store restaurant's invoice
	String invoice;
	
	//declaring arraylist variables for drivers data manipulation:
	
	//arraylist to store drivers list
	ArrayList<String> driverlist = new ArrayList<>();
	//arraylist to store drivers location list
	ArrayList<String> locationlist = new ArrayList<>();
	//arraylist to store drivers loads list
	ArrayList<Integer> loadlist = new ArrayList<>();
	
	//arraylist to store nearest drivers
	ArrayList<String> neardrivers = new ArrayList<>();
	//arraylist to store nearest drivers loads
	ArrayList<Integer> neardriversLoad = new ArrayList<>();
	
	
	
	/**
	 * Restaurant object constructor to make restaurant object for each customer order
	 * it invokes the Customer superclass's attributes
	 * @param name
	 * @param email
	 * @param contact_number
	 * @param location
	 * @param instruction
	 * @param address
	 * @param rest_name
	 * @param rest_location
	 * @param rest_number
	 */
	public Restaurant (String name, String email, String contact_number, String location, String instruction,
			String address, String rest_name, String rest_location, String rest_number) {
		super(name, email, contact_number, location, instruction, address);
		
		//associate the attributes of the class with parameters to the Restaurant object
		this.setRestaurantName(rest_name);
		this.setRestaurantLocation(rest_location);
		this.rest_number = rest_number;
		this.setRestaurantNumber(rest_number);
		
	}
	
	
	/**
	 * Getter method to return the restaurant's name
	 * @return
	 */
	public String getRestaurantName() {
		return rest_name;
	}
	
	/**
	 * Setter method to assign the restaurant's name
	 * @param rest_name
	 */
	public void setRestaurantName(String rest_name) {
		this.rest_name = rest_name;
	}
	
	/**
	 * Getter method to return the restaurant's location value
	 * @return
	 */
	public String getRestaurantLocation() {
		return rest_location;
	}
	
	/**
	 * Setter method to assign the restaurant's location value
	 * @param rest_location
	 */
	public void setRestaurantLocation(String rest_location) {
		this.rest_location = rest_location;
	}
	
	/**
	 * Getter method to return the restaurant's number
	 * @return
	 */
	public String getRestaurantNumber() {
		return rest_number;
	}
	
	/**
	 * Setter method to assign the restaurant's number
	 * @param rest_number
	 */
	public void setRestaurantNumber(String rest_number) {
		this.rest_number = rest_number;
	}
	
	/**
	 * Method to find the nearest driver with the least delivery loads
	 * @param city
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	public String getDriver(String city, Statement statement)throws SQLException {
		

		//arraylist to store drivers locations
		ArrayList<String> driverlocations = new ArrayList<>();
		//variable to store query to find all locations from driver table
		String locationquery = "SELECT driver_location FROM driver";
		
		//initialize resultset to execute query to get all drivers' locations
		ResultSet locationresult =statement.executeQuery(locationquery);
		//declare resultset for drivers found
		ResultSet driversresult;
		//declare variable to store query to find nearest driver
		String driverfound ="";
		
		/**
		 * while loop to extract each location in the next row of driver table
		 */
		while(locationresult.next()) {
			//add each location from the table to the driverlocations arraylist
			driverlocations.add(locationresult.getString("driver_location"));
		}
		//close resultset
		locationresult.close();

		/**
		 * if the customer's city is within the list of drivers locations...
		 */
		if (driverlocations.contains(city)) {
			
			//query to get the drivers with same location as customer's city and lowest order load
			String driverquery = String.format("SELECT driver_name FROM driver WHERE driver_location = '%s' AND "
					+ "driver_orders = ("
					+ "SELECT MIN(driver_orders) "
					+ "FROM driver "
					+ "WHERE driver_location ='%s')", city, city);
			
			//execute the query to find nearest driver with lowest order load
			driversresult =statement.executeQuery(driverquery);
			
			//while loop to extract name of driver who meets the given criteria
			while(driversresult.next()) {
				//store the driver found in this variable
				driverfound = driversresult.getString("driver_name");
			}
			//close resultset
			driversresult.close();
			

		}
		/**
		 * else, if no driver was found within the same city
		 */
		else {
			//no driver found for the given location
			driverfound = "no driver found in that location.";
		}
		
		//return the driverfound's name
		return driverfound;
		
		
	}
	
	
	/**
	 * Declaring the addLoad() method it is responsible for incrementing each driver's load in the driver table
	 * @param statement
	 * @throws SQLException
	 */
	public void addLoad(Statement statement)throws SQLException{
		
		//declare variable to execute update
		int rowsAffected;
		//query command to update the driver's load +1 when order is allocated
		String orderload_query = String.format("UPDATE driver SET driver_orders = driver_orders +1 WHERE driver_name = '%s'", getDriver(getRestaurantLocation(), statement));
		//execute the query statement
		rowsAffected = statement.executeUpdate(orderload_query);
		
		
	}
	

	/**
	 * Declaring method to take meal order details from customer, calls the getInvoice method in the end to create the invoice.
	 * @param customer_name
	 * @param statement
	 * @param con
	 * @param meal_input
	 * @throws SQLException
	 */
	public void takeOrder(String customer_name, Statement statement, Connection con, Scanner meal_input) throws SQLException {
		
		
		//arraylist to store lists of meals from database
		ArrayList<String> databaseMeals = new ArrayList<>();
		//arraylist to store lists of meals from database
		ArrayList<Double> databaseCosts = new ArrayList<>();
		//arraylist to store lists of meals ordered by customer
		ArrayList<String> orderedMeal = new ArrayList<>();
		//arraylist to store each meal's quantity ordered by customer
		ArrayList<Integer> orderedQuantity = new ArrayList<>();
		//arraylist to store each meal's price
		ArrayList<Double> ordered_prices = new ArrayList<>();
		
		
		//variable to store total meal cost
		Double total_cost = 0.00;
	
		//variable to store each meal ordered
		String usr_meal = "";
		//variable to store quantity of each meal ordered
		int quantity;
		//declare int variable for query update statements
		int rowsAffected;
		
		//creating a greeting string to greet customer
		String greeting = "";
		
		//concatenating greeting text to aid customer
		greeting += "\nWelcome to "+ getRestaurantName() + " !\n\n"
				+ "Create your order by entering meals from our menu below:\n\nMeals\t\tCosts:\n";
		//print greeting message
		System.out.println(greeting);
		

		//find the chosen restaurant ID from restaurant table
		String restaurantid_query = String.format("SELECT restaurant_id FROM restaurant WHERE restaurant_name ='%s'", getRestaurantName());
		//execute the query to obtain the restaurant ID
		ResultSet restaurantid_results = statement.executeQuery(restaurantid_query);
		
		//initialize int variable to store restaurant ID
		int rest_id = 0;
		
		/**
		 * while loop to extract restaurant ID from table
		 */
		while (restaurantid_results.next()) {
			//store found restaurant ID in variable
			rest_id = restaurantid_results.getInt("restaurant_id");
		}
		//close resultset
		restaurantid_results.close();
		
		//find all meals and costs for the specific restaurant 
		//(this is will display the menu of the chosen restaurant, by accessing the menu table in the database)
		String menus_query = String.format("SELECT meal, cost FROM menus WHERE menus.restaurant_id = %d", rest_id);
		//find customer ID of current customer (this query can only occur after a customer's details has been entered)
		String customerID_query = String.format("SELECT customer_id FROM customer WHERE customer.customer_name = '%s'", customer_name);
		
		
		//resultset to execute query to get customer ID
		ResultSet customerresults = statement.executeQuery(customerID_query);
		//initialize variable to store customer ID
		int customerID = 0;
		
		/**
		 * while loop to extract the customer ID in customer table
		 */
		while (customerresults.next()) {
			//store customer ID in variable
			customerID = customerresults.getInt("customer_id");
		}
		//close resultset
		customerresults.close();
		
		//resultset for all meals and costs of chosen restaurant
		ResultSet menuresults = statement.executeQuery(menus_query);
		
		/**
		 * while results has lines to show from query
		 */
		while (menuresults.next()) {
			
			//adding menus (meals and costs) from database to arraylist
			databaseMeals.add(menuresults.getString("meal"));
			databaseCosts.add(menuresults.getDouble("cost"));
			
			//print out the result from the query to show each meal with its respective cost
			System.out.println(menuresults.getString("meal") + ", "
					+ "R"+menuresults.getDouble("cost")+"\n");
		}
		//close resultset
		menuresults.close();
		
			
		/**
		 * while user has not entered '0' (Note: '0' is used to end the order)
		 */
		while (usr_meal.equals("0")== false) {
			
			//request the meal order from customer
			System.out.println("\nEnter a meal (enter '0' when done): ");
			//reads the order entered by customer
			usr_meal = meal_input.nextLine();
			
			
			//if the meal entered is within the menu list...
			if (databaseMeals.contains(usr_meal)) {
				
				//ask the meal order quantity from customer (we only ask for meal quantity if meal is in menu)
				System.out.println("\nEnter quantity of "+usr_meal+":");
				
				//stores meal order quantity entered by customer
				quantity = Integer.parseInt(meal_input.nextLine());
				
				//loop through the restaurant's food_menu variable
				for (int i =0; i < databaseMeals.size(); i++) {
					
					//if the current meal entered by the customer matches the meal at the current index in the menu...
					if (usr_meal.equals(databaseMeals.get(i))) {
						
						//add each meal to the orderedMeal arraylist
						orderedMeal.add(usr_meal);
						//add each meal quantity to the orderedQuantity arraylist
						orderedQuantity.add(quantity);
						//add that specific meal's food price to the ordered_prices arraylist
						ordered_prices.add(databaseCosts.get(i));
						
						
						//take the sum of all the meals' costs multiplied by their quantity to get the total cost
						total_cost += databaseCosts.get(i) * quantity;
						
						//store current total cost for current meal and current quantity
						double current_cost = databaseCosts.get(i) * quantity;
						
						//query string to insert into order_detail (we insert all values except order_number, which we do not have yet since order is not finalised)
						//the values not entered will be automatically set to NULL			
						String orderdetail_query = "INSERT INTO order_detail(meal, qty, cost) VALUES(?, ?, ?)";
						//precompiled statement for the insert query
						PreparedStatement pstmt = con.prepareStatement(orderdetail_query);
						//setting the missing values of the insert query (user's meal, quantity of meal and cost)
						pstmt.setString(1, usr_meal);
						pstmt.setInt(2, quantity);
						pstmt.setDouble(3, current_cost);
						
						//execute the prepared statement
						rowsAffected = pstmt.executeUpdate();
					}

				}
				
			}
			
			
			//else if meal entered is not in menu, and if user does not enter '0'
			else if (databaseMeals.contains(usr_meal)!= true && usr_meal.equals("0")!= true){
				
				//inform customer that meal is not in menu or there is a spelling mistake 
				System.out.println("\nThis meal is not in our menu or it is typed incorrectly.\nPlease try again :)\n\n");
				
			}

		}

		//create SQL_Functions object to add order to order table in database
		SQL_Functions addOrder = new SQL_Functions();
		
		//call the insertOrder method from SQL_Functions class to add order
		addOrder.insertOrder(statement, con, rest_id, getInstruction(), total_cost, customerID , getDriver(getRestaurantLocation(), statement));
		
		//change the null values of order_number in order_detail table to a integer values
		String ordernumber_query = "UPDATE order_detail SET order_detail.order_number = ( "
				+ "SELECT MAX(order_number) "
				+ "FROM orders) "
				+ "WHERE order_detail.order_number IS NULL";
		//execute query to update the NULL values with the required order numbers
		rowsAffected = statement.executeUpdate(ordernumber_query);
		
		//resultset to execute query to get the order number of the last added order (which will be the order number with highest value)
		ResultSet latest_ordernumber = statement.executeQuery("SELECT MAX(order_number) FROM orders");
		
		//store the order number with highest value (which means its the latest order)
		int max_ordernumber = 0;
		
		//if latest order number exists
		if (latest_ordernumber.next()) {
			//store the value of latest order number
			max_ordernumber = latest_ordernumber.getInt(1);
		}
		//close resultset
		latest_ordernumber.close();
		
		//call upon the getInvoice method to create the invoice
		getInvoice(statement,orderedMeal, orderedQuantity, ordered_prices,total_cost, max_ordernumber);

	}
	

	/**
	 * Declare method to create the invoice from the order's details entered
	 * @param statement
	 * @param orderedMeal
	 * @param orderedQuantity
	 * @param ordered_prices
	 * @param total_cost
	 * @param ordernumber
	 * @throws SQLException
	 */
	public void getInvoice(Statement statement, ArrayList<String> orderedMeal, ArrayList<Integer> orderedQuantity,
			ArrayList<Double> ordered_prices, Double total_cost, int ordernumber)throws SQLException {
		
		
		//if getDriver method does not return "none" (meaning we have found a driver)...
		if (getDriver(getRestaurantLocation(), statement).equals("none")!= true) {
			
			invoice = "---------------------------------------------------------------------\n";
			//concatenate the invoice variable with the order number
			invoice += "\nOrder number " + ordernumber +"\n";
			
			//concatenate the invoice variable with the customer'sname
			invoice += "Customer: " + name +"\n";
			
			//concatenate the invoice variable with the customer's email
			invoice += "Email: " + email +"\n";
			
			//concatenate the invoice variable with the customer's number
			invoice += "Phone number: " + contact_number +"\n";
			
			//concatenate the invoice variable with the customer's location
			invoice += "Location: " + location +"\n\n";
			
			//concatenate the invoice variable with the restaurant's name and location
			invoice += "You have ordered the following from " + rest_name +" in " + rest_location +":\n\n";
			
			//loop through each ordered meal in orderedMeal arraylist
			for (int i =0; i< orderedMeal.size();i++) {
				//concatenate the invoice variable with the ordered meal quantity, ordered meal and each prices
				invoice += orderedQuantity.get(i) + " x " + orderedMeal.get(i) + " (R" + ordered_prices.get(i) + ")\n";
			}
			
			//concatenate the invoice variable with the customer's instruction
			invoice += "\nSpecial instructions: " + getInstruction()+ "\n\n";
			
			//concatenate the invoice variable with the total cost
			invoice += "Total: " + "R"+ total_cost + "\n\n";
			
			//concatenate the invoice variable with the name of the nearest available driver
			invoice += getDriver(getRestaurantLocation(), statement) + " is nearest to the restaurant and so he/she will be delivering your\n"
					+ "order to you at:\n\n";
			
			//concatenate the invoice variable with the customer's address
			invoice += getAddress() +"\n\n";
			
			//concatenate the invoice variable with the restaurant's number
			invoice += "If you need to contact the restaurant, their number is " + getRestaurantNumber() +".\n\n"
					+ "---------------------------------------------------------------------";
			//concatenate the invoice variable with finalised message and date
			invoice += "\nOrder Finalised on: " + LocalDate.now() + "\n\n";
		}
		
		//else if no driver was found near the restaurant's location...
		else {
			
			//the invoice simply reads the following...
			invoice = "Sorry! Our Drivers are too far away from you\n"
					+ "to be able to deliver to your location";
		}
		//print out the invoice
		System.out.println(invoice);
	}

}
