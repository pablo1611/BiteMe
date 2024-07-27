package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

public class OrderHistoryController {

    @FXML
    private TableView<OrderHistory> orderHistoryTable;

    @FXML
    private TableColumn<OrderHistory, String> orderIdColumn;

    @FXML
    private TableColumn<OrderHistory, String> restaurantColumn;

    @FXML
    private TableColumn<OrderHistory, String> dateColumn;

    @FXML
    private TableColumn<OrderHistory, String> statusColumn;

    @FXML
    void initialize() {
        // Initialization logic
    }

    @FXML
    void handleBack(ActionEvent event) {
        // Handle back action
    }

    public static class OrderHistory {
        private final SimpleStringProperty orderId;
        private final SimpleStringProperty restaurant;
        private final SimpleStringProperty date;
        private final SimpleStringProperty status;

        public OrderHistory(String orderId, String restaurant, String date, String status) {
            this.orderId = new SimpleStringProperty(orderId);
            this.restaurant = new SimpleStringProperty(restaurant);
            this.date = new SimpleStringProperty(date);
            this.status = new SimpleStringProperty(status);
        }

        public String getOrderId() {
            return orderId.get();
        }

        public String getRestaurant() {
            return restaurant.get();
        }

        public String getDate() {
            return date.get();
        }

        public String getStatus() {
            return status.get();
        }
    }
}