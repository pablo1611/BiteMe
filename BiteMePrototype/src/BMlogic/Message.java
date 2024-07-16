package BMlogic;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private ArrayList<Order> orders;

	/**
	 * @param message - the string sent to server
	 * @param orders - the array list returned from server
	 */
	public Message(String message, ArrayList<Order> orders) {
		this.message = message;
		this.orders = orders;
	}

	/**
	 * @return the message string
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the orders array list
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}

	/**
	 * @param message
	 * sets the message in the message field
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @param orders
	 * setes the orders in the orders field
	 */
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
}