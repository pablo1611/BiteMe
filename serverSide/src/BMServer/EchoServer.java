// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com
package BMServer;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import serverGui.ServerGUIController;

import java.io.IOException;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *

 * @version July 2000
 */
/*
 * This class uses and overrides some of the methods of AbstractServer of ocsf.
 * Echo server takes care of the communication of the server with clients.
 * */
 
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  private static DBController database;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   * 
   */

  public EchoServer(int port) 
  {
	  super(port);
	  database = new DBController(ServerGUIController.hostName, ServerGUIController.schemaName, ServerGUIController.userName, ServerGUIController.password);

  }

  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   * Method receives GET and POST requests
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   * @param 
   */
  public void handleMessageFromClient  (Object msg, ConnectionToClient client)
  {

  }
   
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println ("Server listening for connections on port " + getPort());


  }
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()  {
    System.out.println ("Server has stopped listening for connections.");
  }  
  
  
  @Override
  protected void clientConnected(ConnectionToClient client) {
		String clientHost = client.getInetAddress().getHostName();
		String clientIP = client.getInetAddress().getHostAddress();

		//ConnectedClient connectedClient = new ConnectedClient(client, clientHost, clientIP);
		//clientConnections.add(connectedClient);

		System.out.println("Client connected: " + clientHost + " (" + clientIP + ")");
  }
  
  
}
//End of EchoServer class
