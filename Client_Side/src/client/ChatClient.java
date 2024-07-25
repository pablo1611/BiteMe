// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;
import entities.Request;
import entities.User;
import ocsf.client.AbstractClient;
import java.io.IOException;
import common.*;
/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */

public class ChatClient extends AbstractClient
{

  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI;
  public static boolean awaitResponse = false;
  public static User user;

//  //private static OrderDetailViewController orderPage; useless?
//  private static OrderSearchPageController startPage = new OrderSearchPageController();

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
	 
  public ChatClient(String host, int port, ChatIF clientUI) 
   
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
      try {
          openConnection();
          System.out.println("connection succeded");
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) {

      System.out.println("--> handleMessageFromServer");
      Request message;
      try {
          message = Request.fromBytesToObject((byte[]) msg);
          System.out.println(message.toString());
          switch (message.getType()) {
              case USER_LOGIN:
                  user = (User) message.getRequest();
                  break;
          }
      } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      awaitResponse = false;
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  
  public void handleMessageFromClientUI(Object message)
  {
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
    }
    catch(IOException e)
    {
    	System.out.println("handleMessageFromClientUI FAILED");
    	e.printStackTrace();
      clientUI.display("Could not send message to server: Terminating client."+ e);
      quit();
    }
  }

  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class