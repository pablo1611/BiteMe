package BMgui;

import java.util.List;

import BMclient.BMClientController;
import BMlogic.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderDetailsController extends AbstractController {

    @FXML
    private TextField txtOrderPK;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private TextField txtOrderAddress;

    @FXML
    private TextArea txtOrderDetails;

    @FXML
    private Button btnFetchOrder, btnFetchAllOrders, btnUpdateOrder;

    @FXML
    private TableView<Order> tableOrders;

    @FXML
    private TableColumn<Order, String> colRestaurantName;

    @FXML
    private TableColumn<Order, Integer> colOrderNumber;

    @FXML
    private TableColumn<Order, Integer> colTotalPrice;

    @FXML
    private TableColumn<Order, Integer> colOrderList;

    @FXML
    private TableColumn<Order, String> colOrderAddress;

    private BMClientController clientController;

    public OrderDetailsController() {
        super("OrderDetailsController");
    }

    public void setClientController(BMClientController clientController) {
        this.clientController = clientController;
    }

    @FXML
    private void initialize() {
        colRestaurantName.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        colOrderNumber.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colOrderList.setCellValueFactory(new PropertyValueFactory<>("orderList"));
        colOrderAddress.setCellValueFactory(new PropertyValueFactory<>("orderAddress"));
    }

    @FXML
    private void fetchOrderButtonClicked() {
        String orderPK = txtOrderPK.getText();
        if (orderPK.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You must enter a valid order primary key");
            return;
        }
        // Fetch order by primary key and display details in TextArea
        Order order = clientController.getOrder(Integer.parseInt(orderPK));
        if (order != null) {
            txtOrderDetails.setText(order.toString());
        } else {
            showAlert(Alert.AlertType.ERROR, "Order not found");
        }
    }

    @FXML
    private void fetchAllOrdersButtonClicked() {
        // Fetch all orders and populate the table
        List<Order> orders = clientController.getAllOrders();
        if (orders != null && !orders.isEmpty()) {
            tableOrders.getItems().setAll(orders);
            System.out.println("Fetched " + orders.size() + " orders"); // Debug statement
        } else {
            System.out.println("No orders found"); // Debug statement
            showAlert(Alert.AlertType.INFORMATION, "No orders found");
        }
    }

    @FXML
    private void updateOrderButtonClicked() {
        String orderPK = txtOrderPK.getText();
        String totalPriceStr = txtTotalPrice.getText();
        String orderAddress = txtOrderAddress.getText();

        if (orderPK.trim().isEmpty() || totalPriceStr.trim().isEmpty() || orderAddress.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You must enter a valid order primary key, total price, and order address");
            return;
        }

        int orderPKInt = Integer.parseInt(orderPK);
        int totalPrice = Integer.parseInt(totalPriceStr);
        clientController.updateOrder(orderPKInt, totalPrice, orderAddress);
        showAlert(Alert.AlertType.INFORMATION, "Order updated successfully");
    }

    protected void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

