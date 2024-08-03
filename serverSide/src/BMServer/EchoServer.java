package BMServer;

import common.RequestType;
import entities.Request;
import entities.User;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import serverGui.ServerGUIController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import entities.ItemsOrdered;
import entities.Item;
import entities.Order;
public class EchoServer extends AbstractServer {


  private static DBController database;

  public EchoServer(int port) {
    super(port);
    database = new DBController(ServerGUIController.hostName, ServerGUIController.schemaName, ServerGUIController.userName, ServerGUIController.password);
  }

  @Override
  protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
    System.out.println("Message received: " + msg + " from " + client);
    Request message;
    Request request = new Request();
    if (msg == null) {
      try {
        client.sendToClient(null);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    try {
      message = Request.fromBytesToObject((byte[]) msg);
      System.out.println(message.toString());
      System.out.println(message.getType().toString());

      switch (message.getType()) {
        case USER_LOGIN:
          try {
            User user = (User) message.getRequest();
            System.out.println(user.toString());
            request.setRequest(database.userLogin(user));
            System.out.println(user.toString());
            request.setType(RequestType.USER_LOGIN);
            byte[] arr;
            try {
              arr = request.getBytes();
              System.out.println("Serialized bytes: " + arr.length);
              client.sendToClient(arr);
            } catch (IOException e) {
              e.printStackTrace();
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;

        case USER_LOGOUT:
          try {
            User user = (User) message.getRequest();
            boolean logoutSuccess = database.userLogout(user);
            request.setRequest(logoutSuccess);
            request.setType(RequestType.USER_LOGOUT);
            byte[] arr;
            try {
              arr = request.getBytes();
              System.out.println("User " + user.getUserName() + " logged out. Logout success: " + logoutSuccess);
              client.sendToClient(arr);
            } catch (IOException e) {
              e.printStackTrace();
            }
          } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during logout process: " + e.getMessage());
          }
          break;

        case CREATE_USER:
          try {
            User newUser = (User) message.getRequest();
            boolean userCreated = database.createUser(newUser);
            request.setRequest(userCreated);
            request.setType(RequestType.CREATE_USER);
            byte[] arr = request.getBytes();
            System.out.println("User created: " + userCreated);
            client.sendToClient(arr);
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;

        case GET_RESTAURANTS:
          try {
            User user = (User) message.getRequest();
            ArrayList<String> restaurantNames = database.getRestaurants();
            request.setRequest(restaurantNames);
            request.setType(RequestType.GET_RESTAURANTS);
            byte[] arr = request.getBytes();
            client.sendToClient(arr);
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
        case GET_MENU:
          try {
            User user = (User) message.getRequest();
            String restaurantName = message.getOptionalDetail();
            System.out.println("Restaurant Name: " + restaurantName); // Debug print

            // Fetch items for the specified restaurant
            ArrayList<Item> menuItems = database.getItemsByRestaurant(restaurantName);

            // Set the response in the request
            request.setType(RequestType.GET_MENU);
            request.setRequest(menuItems);

            // Prepare and send the response
            byte[] arr = request.getBytes();
            client.sendToClient(arr);
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
        case INSERT_ORDER_ITEMS:
          try {
            List<Map<String, Object>> orderDetailsList = (List<Map<String, Object>>) message.getRequest();
            boolean success = database.insertOrderItems(orderDetailsList);

            // Set the response in the request
            request.setType(RequestType.INSERT_ORDER_ITEMS);
            request.setRequest(success);

            // Prepare and send the response
            byte[] arr = request.getBytes();
            client.sendToClient(arr);
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
        case INSERT_ORDER:
          try {
            Map<String, Object> orderData = (Map<String, Object>) message.getRequest();
            boolean success = database.insertOrder(orderData);

            // Set the response in the request
            request.setType(RequestType.INSERT_ORDER);
            request.setRequest(success);

            // Prepare and send the response
            byte[] arr = request.getBytes();
            client.sendToClient(arr);
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
        case VIEW_INCOME_REPORT:
          try {
            User incomeUser = (User) message.getRequest();
            String restaurantAndBranch[]=(incomeUser.getUserName()).split("_");
            String restaurant;
            String branch;
            if(restaurantAndBranch.length==3) {
              restaurant=restaurantAndBranch[0]+" "+restaurantAndBranch[1];
              branch=restaurantAndBranch[2];
            }
            else {
              restaurant=restaurantAndBranch[0];
              branch=restaurantAndBranch[1];
            }
            System.out.println("Fetching user which is: " +incomeUser.getUserName() );
            System.out.println("Fetching income report for: " + restaurant + " - " + branch);
            List<ItemsOrdered> incomeReport = database.getIncomeReport(restaurant, branch);

            System.out.println("Income report size: " + incomeReport.size());
            request.setRequest(incomeReport);
            request.setType(RequestType.VIEW_INCOME_REPORT);
            byte[] arr = request.getBytes();
            client.sendToClient(arr);
            System.out.println("Income report sent to client.");
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;



      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void serverStarted() {
    System.out.println("Server started and listening on port " + getPort());
  }

  @Override
  protected void serverStopped() {
    System.out.println("Server stopped listening for connections.");
  }

  @Override
  protected void clientConnected(ConnectionToClient client) {
    String clientHost = client.getInetAddress().getHostName();
    String clientIP = client.getInetAddress().getHostAddress();
    System.out.println("Client connected: " + clientHost + " (" + clientIP + ")");
  }

}