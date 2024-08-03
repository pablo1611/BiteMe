package BMServer;

import entities.Item;
import entities.ItemsOrdered;
import entities.Order;

import java.sql.Date;
import java.time.LocalDate;
import entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBController {

    static Connection conn;
    static Statement stmt;
    static PreparedStatement pstmt;

    public DBController(String host, String schema, String userName, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("Driver definition failed");
        }

        try {
            String url = "jdbc:mysql://" + host + "/" + schema + "?serverTimezone=IST";
            conn = DriverManager.getConnection(url, userName, password);
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static void closeDBconnection() {
        try {
            if (pstmt != null) pstmt.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * Checks if a user exists in the database.
//     *
//     * @param userName - The username(String) to check.
//     * @return true if the user exists, false otherwise.
//     */

//    public boolean checkIfUserExist(String userName) {
//        String query = "SELECT COUNT(*) FROM users WHERE Username = ?";
//        try (PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setString(1, userName);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    int count = rs.getInt(1);
//                    return count > 0;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }


    public User userLogin(User user) {
        String query = "SELECT Password, Status, Role FROM Users WHERE Username = ?";
        String updateQuery = "UPDATE Users SET Status = 'Connected' WHERE Username = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("Password");
                    String connectionStatus = rs.getString("Status");
                    String role = rs.getString("Role");
                    System.out.println();
                    System.out.println();
                    System.out.println(role);

                    if (!password.equals(user.getPassword())) {
                        user.setPermission("Password invalid");
                        return user;
                    }

                    if (connectionStatus.equals("Connected")) {
                        user.setPermission("Already connected");
                        return user;
                    }

                    // If we reach here, the login is successful
                    try (PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {
                        updatePs.setString(1, user.getUserName());
                        int updatedRows = updatePs.executeUpdate();

                        if (updatedRows > 0) {
                            user.setPermission("Connected");
                            user.setRole(role);  // Set the user's role
                        } else {
                            user.setPermission("Error updating connection status");
                        }
                    }
                } else {
                    user.setPermission("User not Exist");
                    return user;  // Return immediately if user doesn't exist
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            user.setPermission("Error during login");
        }
        return user;
    }

    /**
     * Logs out a user, updating their connection status in the database.
     *
     * @param user - The User object to log out.
     */

    public boolean userLogout(User user) {
        String query = "UPDATE Users SET Status = 'Disconnected' WHERE Username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            int updatedRows = ps.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean createUser(User user) {
        String query = "INSERT INTO Users (Username, Password, Name, Phone, Address, Role, Status, Email, Restaurant, Branch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getStatus());
            ps.setString(8, user.getEmail());
            ps.setString(9, user.getRestaurant());  // Add restaurant
            ps.setString(10, user.getBranch());  // Add branch
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<String> getRestaurants() {
        String query = "SELECT DISTINCT name FROM restaurants";
        ArrayList<String> restaurantNames = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                restaurantNames.add(rs.getString("name"));
                //************checking*********************
                //System.out.println("Fetched restaurant name: " + rs.getString("name"));  // Print each fetched restaurant name
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurantNames;
    }


    public ArrayList<Item> getItemsByRestaurant(String restaurantName) {
        String query = "SELECT * FROM menuitems WHERE restaurant = ? OR restaurant = 'all'";
        ArrayList<Item> items = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, restaurantName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String restaurant = rs.getString("restaurant");
                    String type = rs.getString("type");
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    String note1 = rs.getString("note1");
                    String note2 = rs.getString("note2");
                    String note3 = rs.getString("note3");

                    Item item = new Item(restaurant, type, name, price, note1, note2, note3);
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public boolean insertOrderItems(List<Map<String, Object>> orderDetailsList) {
        String query = "INSERT INTO itemsordered (restaurant, branch, item, type, amount, price) VALUES (?, ?, ?, ?, ?, ?)";
        boolean success = true;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (Map<String, Object> orderDetail : orderDetailsList) {
                ps.setString(1, (String) orderDetail.get("restaurant"));
                ps.setString(2, (String) orderDetail.get("branch"));
                ps.setString(3, (String) orderDetail.get("item"));
                ps.setString(4, (String) orderDetail.get("type"));
                ps.setInt(5, (Integer) orderDetail.get("amount"));
                ps.setInt(6, (Integer) orderDetail.get("price"));
                int updatedRows = ps.executeUpdate();
                if (updatedRows <= 0) {
                    success = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public boolean insertOrder(Map<String, Object> orderData) {
        String query = "INSERT INTO orders (username, order_date, restaurant, branch, type, price, supply_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean success = true;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, (String) orderData.get("username"));
            ps.setDate(2, java.sql.Date.valueOf((LocalDate) orderData.get("order_date")));
            ps.setString(3, (String) orderData.get("restaurant"));
            ps.setString(4, (String) orderData.get("branch"));
            ps.setString(5, (String) orderData.get("type"));
            ps.setInt(6, (Integer) orderData.get("price"));
            ps.setString(7, (String) orderData.get("supply_status"));

            int updatedRows = ps.executeUpdate();
            if (updatedRows <= 0) {
                success = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public List<ItemsOrdered> getIncomeReport(String restaurant, String branch) {
        String query = "SELECT * FROM itemsordered WHERE restaurant = ? AND branch = ?";
        List<ItemsOrdered> items = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, restaurant);
            ps.setString(2, branch);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    String item = rs.getString("item");
                    String type = rs.getString("type");
                    int amount = rs.getInt("amount");
                    int price = rs.getInt("price");
                    items.add(new ItemsOrdered(orderId, restaurant, branch, item, type, amount, price));
                }
                System.out.println("Query executed successfully: " + items.size() + " items retrieved.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }














}


