package BMserver;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import BMlogic.Order;

public class DBConnector {
    private Connection conn;
    private static final String URL = "jdbc:mysql://localhost:3306/biteme?serverTimezone=IST";
    private static final String USER = "root";
    //private static final String PASSWORD = "Pablo1998"; // replace with your MySQL password
    private static final String PASSWORD = "Aa123456";

    public void connectToDB() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver definition succeeded");

            // Establish a connection to the database
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully");
        } catch (ClassNotFoundException ex) {
            // Handle the error
            System.out.println("Driver definition failed: " + ex.getMessage());
        } catch (SQLException ex) {
            // Handle SQL exceptions
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    // Fetch all orders from the database
    public List<Order> getOrders() {
        if (conn == null) {
            System.out.println("Database connection is not established");
            return new ArrayList<>(); // Return an empty list if no connection
        }

        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setRestaurantName(rs.getString("Restaurant"));
                order.setOrderNumber(rs.getInt("Order_number"));
                order.setTotalPrice(rs.getInt("Total_price"));
                order.setOrderList(rs.getInt("Order_list_number"));
                order.setOrderAddress(rs.getString("Order_address"));
                orders.add(order);
                System.out.println("Fetched order: " + order); // Debug statement
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Fetch a specific order by primary key from the database
    public Order getOrder(int orderNumber) {
        if (conn == null) {
            System.out.println("Database connection is not established");
            return null;
        }

        String query = "SELECT * FROM orders WHERE Order_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setRestaurantName(rs.getString("Restaurant"));
                order.setOrderNumber(rs.getInt("Order_number"));
                order.setTotalPrice(rs.getInt("Total_price"));
                order.setOrderList(rs.getInt("Order_list_number"));
                order.setOrderAddress(rs.getString("Order_address"));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an order in the database
    public boolean updateOrder(int orderNumber, int totalPrice, String orderAddress) {
        if (conn == null) {
            System.out.println("Database connection is not established");
            return false;
        }

        String query = "UPDATE orders SET Total_price = ?, Order_address = ? WHERE Order_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, totalPrice);
            pstmt.setString(2, orderAddress);
            pstmt.setInt(3, orderNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed successfully");
            }
        } catch (SQLException ex) {
            System.out.println("Failed to close connection: " + ex.getMessage());
        }
    }
}

