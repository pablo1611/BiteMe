package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import common.SharedData;
import entities.Item;
import entities.OrderDetails;
import entities.Request;
import common.RequestType;
import client.ChatClient;
import client.ClientUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutPageController extends AbstractController {

    @FXML
    private TableView<OrderItem> orderSummaryTable;

    @FXML
    private TableColumn<OrderItem, String> itemColumn;

    @FXML
    private TableColumn<OrderItem, Integer> amountColumn;

    @FXML
    private TableColumn<OrderItem, Integer> priceColumn;

    @FXML
    private Label deliveryPriceLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button confirmOrderButton;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBack;

    private ArrayList<Item> menuItems;
    private Map<String, Integer> orderMap;
    private OrderDetails orderDetails;

    @FXML
    public void initialize() {
        // Initialize table columns
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Retrieve stored data from SharedData
        orderMap = SharedData.getInstance().getOrderMap();
        menuItems = SharedData.getInstance().getMenuItems();
        orderDetails = SharedData.getInstance().getOrderDetails();

        // Debug printing
        System.out.println("Menu Items:");
        for (Item item : menuItems) {
            System.out.println(item);
        }

        // Populate table with order summary
        displayOrderSummary(orderMap, menuItems);

        // Display delivery price if applicable
        int deliveryPrice = 0; // Set this based on your logic
        if ("DELIVERY".equals(orderDetails.getOrderType()) || "EARLY DELIVERY".equals(orderDetails.getOrderType()) ) {
            deliveryPrice = 25; // regular delivery price
            deliveryPriceLabel.setText("Delivery Price: $" + deliveryPrice);
        } else if ("BUSINESS DELIVERY".equals(orderDetails.getOrderType())) {
            int numberOfClients = orderDetails.getNumberOfClients();
            int totaldelivey;
            if (numberOfClients <= 2)
                deliveryPrice = 20; // regular delivery price
            else {
                deliveryPrice = 15;
            }
            totaldelivey = deliveryPrice * numberOfClients;
            deliveryPriceLabel.setText("Delivery Price (for shared delivery): $" + totaldelivey + ", " + deliveryPrice + "$ each");
        } else {
            deliveryPriceLabel.setText(" ");
        }

        // Calculate and display total price
        int totalPrice = calculateTotalPrice(orderMap, menuItems) + deliveryPrice;
        if("EARLY DELIVERY".equals(orderDetails.getOrderType())) {
        	totalPrice = (int) (calculateTotalPrice(orderMap, menuItems)*0.9 + deliveryPrice);
        }
        
        totalPriceLabel.setText("Total Price: $" + totalPrice);

        // Save total price to SharedData
        SharedData.getInstance().setTotalPrice(totalPrice);
    }

    private void displayOrderSummary(Map<String, Integer> orderMap, ArrayList<Item> menuItems) {
        orderSummaryTable.getItems().clear();
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int price = getPriceForItem(itemName, menuItems) * quantity;
            OrderItem orderItem = new OrderItem(itemName, quantity, price);
            orderSummaryTable.getItems().add(orderItem);
        }
    }

    private int getPriceForItem(String itemName, ArrayList<Item> menuItems) {
        for (Item item : menuItems) {
            if (item.getName().equals(itemName)) {
                return item.getPrice();
            }
        }
        return 0;
    }

    private int calculateTotalPrice(Map<String, Integer> orderMap, ArrayList<Item> menuItems) {
        int totalPrice = 0;
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int price = getPriceForItem(itemName, menuItems) * quantity;
            totalPrice += price;
        }
        return totalPrice;
    }

    @FXML
    private void handleConfirmOrder(ActionEvent event) {
        // Prepare order details to send to the server
        List<Map<String, Object>> orderDetailsList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String itemName = entry.getKey();
            int amount = entry.getValue();
            int price = getPriceForItem(itemName, menuItems) * amount;
            String type = getItemType(itemName, menuItems);

            Map<String, Object> orderDetail = new HashMap<>();
            orderDetail.put("restaurant", orderDetails.getRestaurant());
            orderDetail.put("branch", orderDetails.getBranch());
            orderDetail.put("item", itemName);
            orderDetail.put("type", type);
            orderDetail.put("amount", amount);
            orderDetail.put("price", price);

            orderDetailsList.add(orderDetail);
        }

        // Create and configure the request object
        Request request = new Request();
        request.setType(RequestType.INSERT_ORDER_ITEMS);
        request.setRequest(orderDetailsList);  // set the List<Map<String, Object>> as the request

        // Send the request to the server
        ChatClient client = ClientUI.getClient();
        try {
            byte[] arr = request.getBytes();
            client.handleMessageFromClientUI(arr);

            // Check for the response
            Object response = SharedData.getInstance().getLastResponse();
            if (response != null) {
                System.out.println("Received response from server: " + response); // Debug print
            } else {
                System.out.println("Request does not contain a valid response");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        navigateTo("/gui/ConfirmOrder.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    private String getItemType(String itemName, ArrayList<Item> menuItems) {
        for (Item item : menuItems) {
            if (item.getName().equals(itemName)) {
                return item.getType();
            }
        }
        return "";
    }

    @FXML
    private void handleBack(ActionEvent event) {
        navigateTo("/gui/CustomerDetailsPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        handleExit(event);
    }

    public static class OrderItem {
        private String itemName;
        private int amount;
        private int price;

        public OrderItem(String itemName, int amount, int price) {
            this.itemName = itemName;
            this.amount = amount;
            this.price = price;
        }

        public String getItemName() {
            return itemName;
        }

        public int getAmount() {
            return amount;
        }

        public int getPrice() {
            return price;
        }
    }
}
