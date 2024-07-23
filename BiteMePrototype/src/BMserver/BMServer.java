package BMserver;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import BMlogic.Order;
import BMlogic.UpdateOrderMessage;
import java.io.IOException;

public class BMServer extends AbstractServer {
    final public static int DEFAULT_PORT = 5555;
    private DBConnector dbConnector;

    public BMServer(int port) {
        super(port);
        dbConnector = new DBConnector();
        dbConnector.connectToDB();
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg instanceof UpdateOrderMessage) {
            UpdateOrderMessage updateMsg = (UpdateOrderMessage) msg;
            boolean success = dbConnector.updateOrder(updateMsg.getOrderNumber(), updateMsg.getTotalPrice(), updateMsg.getOrderAddress());
            try {
                client.sendToClient(success ? "Update successful" : "Update failed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Message received: " + msg + " from " + client);
            try {
                client.sendToClient("Echo: " + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT; // You can change the port as needed
        BMServer server = new BMServer(port);
        try {
            server.listen();
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
