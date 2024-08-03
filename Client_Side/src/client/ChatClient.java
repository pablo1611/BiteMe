package client;
import entities.Item;
import entities.Request;
import entities.User;
import ocsf.client.AbstractClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import entities.ItemsOrdered;

import common.*;

public class ChatClient extends AbstractClient {

    // Instance variables **********************************************
    ChatIF clientUI;
    public static boolean awaitResponse = false;
    public static User user;
    public static boolean logout;
    public static boolean createUserSuccess;
    public static List<ItemsOrdered> incomeReport;

    // Constructors ****************************************************
    public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
        super(host, port); // Call the superclass constructor
        try {
            openConnection();
            System.out.println("Connection succeeded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Instance methods ************************************************
    public void handleMessageFromServer(Object msg) {

        System.out.println("--> handleMessageFromServer");
        Request message;
        try {
            message = Request.fromBytesToObject((byte[]) msg);
            System.out.println(message.toString());
            SharedData.getInstance().setLastResponse(message.getRequest());

            switch (message.getType()) {
                case USER_LOGIN:
                    user = (User) message.getRequest();
                    break;

                case USER_LOGOUT:
                    System.out.println("Logout successful");
                    logout = true;
                    user = null; // Clear the current user
                    break;
                case CREATE_USER:
                    System.out.println("User created successfully");
                    break;

                case GET_RESTAURANTS:
                    ArrayList<String> restaurantNames = (ArrayList<String>) message.getRequest();
                    System.out.println("Received restaurants in ChatClient: " + restaurantNames); // Debug print
                    break;
                case GET_MENU:
                    ArrayList<Item> menuItems = (ArrayList<Item>) message.getRequest();
                    System.out.println("Received menu items in ChatClient: " + menuItems); // Debug print
                    break;
                case INSERT_ORDER_ITEMS:
                    Boolean success = (Boolean) message.getRequest();
                    System.out.println("Order items insert success: " + success); // Debug print
                    break;
                case INSERT_ORDER:
                    Boolean success1 = (Boolean) message.getRequest();
                    System.out.println("Order items insert success: " + success1); // Debug print
                    break;
                case VIEW_INCOME_REPORT:
                    incomeReport = (List<ItemsOrdered>) message.getRequest();
                    System.out.println("Income report received: " + incomeReport.size() + " items");
                    break;



            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        awaitResponse = false;
    }

    public void handleMessageFromClientUI(Object message) {
        try {
            awaitResponse = true;
            System.out.println(message);
            sendToServer(message);
            // wait for response
            while (awaitResponse) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Print the last response for debugging
            Object lastResponse = SharedData.getInstance().getLastResponse();
            if (lastResponse != null) {
                System.out.println("Last response in handleMessageFromClientUI: " + lastResponse);
                if (lastResponse instanceof ArrayList) {
                    @SuppressWarnings("unchecked")
                    ArrayList<String> restaurantNames = (ArrayList<String>) lastResponse;
                    System.out.println("Restaurants list in last response: " + restaurantNames);
                }
            }
        } catch (IOException e) {
            System.out.println("handleMessageFromClientUI FAILED");
            e.printStackTrace();
            clientUI.display("Could not send message to server: Terminating client." + e);
            quit();
        }
    }

    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {}
        System.exit(0);
    }
}
// End of ChatClient class
