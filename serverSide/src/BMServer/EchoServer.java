package BMServer;

import common.RequestType;
import entities.Request;
import entities.User;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import serverGui.ServerGUIController;

import java.io.IOException;

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
      };
    }
    try {
      message = Request.fromBytesToObject((byte[]) msg);
      System.out.println(message.toString());
        // if (msg instanceof Request) {
        System.out.println(message.toString());
      System.out.println("a1");
        System.out.println(message.getType().toString());
        switch (message.getType()) {
          case USER_LOGIN:
            try {
              System.out.println("a2");
              User user = (User) message.getRequest();
              System.out.println(user.toString());
              request.setRequest(database.userLogin(user));
              System.out.println(user.toString());
              request.setType(RequestType.USER_LOGIN);
              byte[] arr;
              try {
                System.out.println("a3");
                arr = request.getBytes();
                System.out.println("Serialized bytes: " + arr.length);
                client.sendToClient(arr);
                System.out.println("a4");
              } catch (IOException e) {
                e.printStackTrace();
              }
            } catch (Exception e) {
            };
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
            };
            break;
        }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
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