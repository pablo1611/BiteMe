package client;
import entities.Request;
import entities.User;
import ocsf.client.AbstractClient;
import java.io.IOException;
import common.*;
//ssss
/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @version July 2000
 */
public class ChatClient extends AbstractClient {

    // Instance variables **********************************************

    /**
     * The interface type variable. It allows the implementation of
     * the display method in the client.
     */
    ChatIF clientUI;
    public static boolean awaitResponse = false;
    public static User user;
    public static boolean logout;

    // Constructors ****************************************************

    /**
     * Constructs an instance of the chat client.
     *
     * @param host     The server to connect to.
     * @param port     The port number to connect on.
     * @param clientUI The interface type variable.
     */
    public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
        super(host, port); // Call the superclass constructor
        try {
            openConnection();
            System.out.println("connection succeded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Instance methods ************************************************

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

                case USER_LOGOUT:
                    System.out.println("Logout successful: " + ((User) message.getRequest()).getUserName());
                    logout = true;
                    break;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        awaitResponse = false;
    }


    /**
     * This method handles all data coming from the UI
     *
     * @param message The message from the UI.
     */
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
        } catch (IOException e) {
            System.out.println("handleMessageFromClientUI FAILED");
            e.printStackTrace();
            clientUI.display("Could not send message to server: Terminating client." + e);
            quit();
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }
}
// End of ChatClient class




