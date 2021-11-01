
/**
 * Class to represent a customer ordering from restaurant
 * It contains variables used as attributes for each customer
 * @author dan-sampai
 *
 */
public class Customer{
	
	//variable to store customer address
	String address;
	//variable to store customer's name
	String name;
	//variable to store customer's email
	String email;
	//variable to store customer's number
	String contact_number;
	//variable to store customer's location
	String location;
	//variable to store customer's instruction
	String instruction;
	
	/**
	 * Customer object constructor to pass attributes to Restaurant object
	 * @param name
	 * @param email
	 * @param contact_number
	 * @param location
	 * @param instruction
	 * @param address
	 */
	public Customer (String name, String email, String contact_number, String location, String instruction,
			String address) {
		
		//associate the attributes of the class with parameters to the Customer object
		this.setName(name);
		this.setEmail(email);
		this.setContactNumber(contact_number);
		this.setCustomerLocation(location);
		this.setInstruction(instruction);
		this.setAddress(address);
		
	}

	
	/**
	 * Getter method to return the customer's name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter method to assign the customer's name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Getter method to return the customer's email
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter method to assign the customer's email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * Getter method to return the customer's contact number
	 * @return
	 */
	public String getContactNumber() {
		return contact_number;
	}
	
	/**
	 * Setter method to assign the customer's contact number
	 * @param contact_number
	 */
	public void setContactNumber(String contact_number) {
		this.contact_number = contact_number;
	}
	
	
	/**
	 * Getter method to return the customer's instruction
	 * @return
	 */
	public String getInstruction() {
		return instruction;
	}
	
	/**
	 * Setter method to assign the customer's instruction
	 * @param instruction
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	/**
	 * Getter method to return the customer's address 
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Setter method to assign the customer's address
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Getter method to return the customer's location 
	 * @return
	 */
	public String getCustomerLocation() {
		return location;
	}
	
	/**
	 * Setter method to assign the customer's location
	 * @param location
	 */
	public void setCustomerLocation(String location) {
		this.location = location;
	}
	

}
