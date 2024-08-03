package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import entities.Item;
import entities.OrderDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ChatClient;
import client.ClientUI;
import common.RequestType;
import common.SharedData;
import entities.Request;

public class OrderItemsController extends AbstractController {

    @FXML
    private Label restaurantTitle;

    @FXML
    private ListView<VBox> menuListView;

    @FXML
    private Button backButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private TextArea orderDetailsTextArea;

    @FXML
    private VBox leftVBox;

    @FXML
    private VBox rightVBox;

    @FXML
    private ImageView logoImageView;

    private List<Item> currentMenu;
    private Map<String, Integer> orderMap = new HashMap<>();
    private Map<String, List<String>> notesMap = new HashMap<>();
    private Map<String, List<CheckBox>> checkBoxMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Set preferred widths
        leftVBox.setPrefWidth(500);
        rightVBox.setPrefWidth(400);

        Image logoImage = new Image(getClass().getResourceAsStream("/images/BiteMe.png"));
        logoImageView.setImage(logoImage);

        // Retrieve stored order details
        OrderDetails orderDetails = SharedData.getInstance().getOrderDetails();
        if (orderDetails != null) {
            restaurantTitle.setText(orderDetails.getRestaurant());
            requestMenuItems(orderDetails.getRestaurant());
        }
    }

    private void requestMenuItems(String restaurantName) {
        // Create a request
        Request request = new Request();
        request.setType(RequestType.GET_MENU);
        request.setRequest(null); // Set request to null if not needed
        request.setOptionalDetail(restaurantName); // Set the restaurant name

        // Send the request to the server
        ChatClient client = ClientUI.getClient();
        try {
            byte[] arr = request.getBytes();
            client.handleMessageFromClientUI(arr);

            // Check for the response
            Object response = SharedData.getInstance().getLastResponse();
            if (response != null && response instanceof ArrayList) {
                @SuppressWarnings("unchecked")
                ArrayList<Item> menuItems = (ArrayList<Item>) response;
                System.out.println("Received menu items in controller: " + menuItems); // Debug print
                setMenuItems(menuItems);
            } else {
                System.out.println("Request does not contain a valid list of menu items");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMenuItems(ArrayList<Item> menuItems) {
        currentMenu = menuItems;
        populateMenu();
    }

    private void populateMenu() {
        menuListView.getItems().clear();

        Map<String, String> sectionTitles = new HashMap<>();
        sectionTitles.put("First", "STARTERS");
        sectionTitles.put("Salad", "SALADS");
        sectionTitles.put("Main", "MAIN DISHES");
        sectionTitles.put("Drink", "DRINKS");
        sectionTitles.put("Dessert", "DESSERTS");

        // Define the order of sections
        List<String> order = List.of("First", "Salad", "Main", "Drink", "Dessert");

        for (String type : order) {
            List<Item> itemsOfType = new ArrayList<>();
            for (Item item : currentMenu) {
                if (item.getType().equals(type)) {
                    itemsOfType.add(item);
                }
            }
            if (!itemsOfType.isEmpty()) {
                Label sectionLabel = new Label(sectionTitles.get(type));
                sectionLabel.getStyleClass().add("section-title");
                menuListView.getItems().add(new VBox(sectionLabel));

                for (Item item : itemsOfType) {
                    VBox itemBox = createMenuItemBox(item);
                    menuListView.getItems().add(itemBox);
                }
            }
        }
    }

    private VBox createMenuItemBox(Item item) {
        VBox vbox = new VBox(5);

        Label nameLabel = new Label(item.getName());
        Label priceLabel = new Label(String.valueOf(item.getPrice()) + "$");
        TextField quantityField = new TextField("0");
        quantityField.getStyleClass().add("quantity-field");
        Button addButton = new Button("+");
        Button removeButton = new Button("-");

        addButton.setOnAction(e -> {
            increaseQuantity(quantityField, item);
            updateOrderDetails();
        });
        removeButton.setOnAction(e -> {
            decreaseQuantity(quantityField, item);
            updateOrderDetails();
        });

        CheckBox option1 = new CheckBox(item.getNote1());
        CheckBox option2 = new CheckBox(item.getNote2());
        CheckBox option3 = new CheckBox(item.getNote3());

        option1.setOnAction(e -> handleNoteSelection(item.getName(), option1.getText(), option1.isSelected()));
        option2.setOnAction(e -> handleNoteSelection(item.getName(), option2.getText(), option2.isSelected()));
        option3.setOnAction(e -> handleNoteSelection(item.getName(), option3.getText(), option3.isSelected()));

        if (item.getNote1() == null) option1.setVisible(false);
        if (item.getNote2() == null) option2.setVisible(false);
        if (item.getNote3() == null) option3.setVisible(false);

        List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(option1);
        checkBoxes.add(option2);
        checkBoxes.add(option3);
        checkBoxMap.put(item.getName(), checkBoxes);

        HBox hbox = new HBox(10, nameLabel, priceLabel, removeButton, quantityField, addButton);
        vbox.getChildren().addAll(hbox, option1, option2, option3);

        return vbox;
    }

    private void handleNoteSelection(String itemName, String note, boolean isSelected) {
        notesMap.putIfAbsent(itemName, new ArrayList<>());
        if (isSelected) {
            notesMap.get(itemName).add(note);
        } else {
            notesMap.get(itemName).remove(note);
        }
        updateOrderDetails();
    }

    private void increaseQuantity(TextField quantityField, Item item) {
        int quantity = Integer.parseInt(quantityField.getText());
        quantityField.setText(String.valueOf(++quantity));
        orderMap.put(item.getName(), quantity);
    }

    private void decreaseQuantity(TextField quantityField, Item item) {
        int quantity = Integer.parseInt(quantityField.getText());
        if (quantity > 0) {
            quantityField.setText(String.valueOf(--quantity));
            if (quantity == 0) {
                orderMap.remove(item.getName());
                notesMap.remove(item.getName());
                clearCheckBoxes(item.getName());
            } else {
                orderMap.put(item.getName(), quantity);
            }
            updateOrderDetails();
        }
    }

    private void clearCheckBoxes(String itemName) {
        List<CheckBox> checkBoxes = checkBoxMap.get(itemName);
        if (checkBoxes != null) {
            for (CheckBox checkBox : checkBoxes) {
                checkBox.setSelected(false);
            }
        }
    }

    private void updateOrderDetails() {
        StringBuilder orderDetails = new StringBuilder();
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int price = getPriceForItem(itemName) * quantity;
            List<String> notes = notesMap.getOrDefault(itemName, new ArrayList<>());

            orderDetails.append(String.format("%s %d - %d$\n", itemName, quantity, price));
            for (String note : notes) {
                orderDetails.append(note).append("\n");
            }
        }
        orderDetailsTextArea.setText(orderDetails.toString());
    }

    private int getPriceForItem(String itemName) {
        for (Item item : currentMenu) {
            if (item.getName().equals(itemName)) {
                return item.getPrice();
            }
        }
        return 0;
    }

    @FXML
    private void handleBack(ActionEvent event) {
        navigateTo("/gui/CustomerDetailsPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    @FXML
    private void handleCheckout(ActionEvent event) {
        // Store the current state in SharedData
        SharedData.getInstance().setOrderMap(orderMap);
        SharedData.getInstance().setMenuItems(new ArrayList<>(currentMenu));
        navigateTo("/gui/CheckoutPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        handleExit(event);
    }
}
