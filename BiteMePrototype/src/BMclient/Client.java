package BMclient;

import ocsf.client.AbstractClient;
import BMlogic.Message;
import BMlogic.Order;
import BMlogic.UpdateOrderMessage;
import BMserver.DBConnector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client extends AbstractClient {
    private DBConnector dbConnector;

    public Client(String host, int port) {
        super(host, port);
        dbConnector = new DBConnector();
        dbConnector.connectToDB();
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        System.out.println("Message from server: " + msg);
    }

    public void handleMessageFromClientUI(Message msg) {
        try {
            sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fetch all orders from the database
    public List<Order> fetchAllOrders() {
        return dbConnector.getOrders();
    }

    // Fetch a specific order by primary key from the database
    public Order fetchOrder(int orderPK) {
        return dbConnector.getOrder(orderPK);
    }
    public void updateOrder(int orderNumber, int totalPrice, String orderAddress) {
        UpdateOrderMessage msg = new UpdateOrderMessage(orderAddress, null, orderNumber, totalPrice, orderAddress);
        handleMessageFromClientUI(msg);
    }
}