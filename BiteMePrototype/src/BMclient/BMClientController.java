package BMclient;

import BMlogic.Message;
import BMlogic.Order;
import java.io.IOException;
import java.util.List;
import BMgui.AbstractController;

public class BMClientController {
    private Client client;
    private AbstractController currentScreen;

    public BMClientController(String host, int port) {
        client = new Client(host, port);
    }

    public boolean connectClientToServer(String host, int port) {
        try {
            client.openConnection();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public AbstractController getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(AbstractController currentScreen) {
        this.currentScreen = currentScreen;
    }

    public void accept(Message msg) {
        client.handleMessageFromClientUI(msg);
    }

    // Implementing the getAllOrders method
    public List<Order> getAllOrders() {
        return client.fetchAllOrders();
    }

    public Order getOrder(int orderPK) {
        return client.fetchOrder(orderPK);
    }

    public void updateOrder(int orderNumber, int totalPrice, String orderAddress) {
        client.updateOrder(orderNumber, totalPrice, orderAddress);
    }
}

