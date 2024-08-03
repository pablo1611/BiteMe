package controllers;

import client.ChatClient;
import client.ClientUI;
import entities.ItemsOrdered;
import entities.Request;
import common.RequestType;
import common.SharedData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IncomeReportController extends AbstractController {

    @FXML
    private Label lblBranchDetails;

    @FXML
    private TableView<ItemsOrdered> tblIncomeReport;

    @FXML
    private TableColumn<ItemsOrdered, Integer> colOrderId;

    @FXML
    private TableColumn<ItemsOrdered, String> colItem;

    @FXML
    private TableColumn<ItemsOrdered, String> colType;

    @FXML
    private TableColumn<ItemsOrdered, Integer> colAmount;

    @FXML
    private TableColumn<ItemsOrdered, Integer> colPrice;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblTotalIncome;

    @FXML
    private Button btnLoadReport;

    @FXML
    private Button btnBack;

    @FXML
    void loadReport(ActionEvent event) throws IOException {
        Request request = new Request();
        request.setType(RequestType.VIEW_INCOME_REPORT);
        request.setRequest(this.currentUser);
        System.out.println("In IncomeReport, the user is: " + this.currentUser.getUserName());

        // Send the request to the server
        ChatClient client = ClientUI.getClient();
        try {
            byte[] arr = request.getBytes();
            client.handleMessageFromClientUI(arr);

            // Wait for the response
            while (ChatClient.awaitResponse) {
                Thread.sleep(100); // Adjust sleep time if necessary
            }

            // Check for the response
            Object response = SharedData.getInstance().getLastResponse();
            if (response != null && response instanceof ArrayList) {
                @SuppressWarnings("unchecked")
                List<ItemsOrdered> incomeReport = (List<ItemsOrdered>) response;
                tblIncomeReport.setItems(FXCollections.observableArrayList(incomeReport));

                int totalOrders = incomeReport.size();
                int totalIncome = incomeReport.stream().mapToInt(item -> item.getAmount() * item.getPrice()).sum();

                lblTotalOrders.setText("Total Orders: " + totalOrders);
                lblTotalIncome.setText("Total Income: " + totalIncome + "$");
            } else {
                lblTotalOrders.setText("Total Orders: 0");
                lblTotalIncome.setText("Total Income: 0");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void goBack(ActionEvent event) throws IOException {
        navigateTo("/gui/SelectReport.fxml", "/gui/SelectReport.css", (Node) event.getSource());
    }

    @FXML
    public void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
