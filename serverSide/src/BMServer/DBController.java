package BMServer;
import entities.OrderDetails;
///sss
import entities.User;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    /**
     * Checks if a user exists in the database.
     *
     * @param userName - The username(String) to check.
     * @return true if the user exists, false otherwise.
     */
    public boolean checkIfUserExist(String userName) {
        String query = "SELECT COUNT(*) FROM Users WHERE UserName = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User userLogin(User user) {
        String query = "SELECT Password, Status FROM Users WHERE Username = ?";
        String updateQuery = "UPDATE Users SET Status = 'Connected' WHERE Username = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("Password");
                    String connectionStatus = rs.getString("Status");

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
                        } else {
                            user.setPermission("Error updating connection status");
                        }
                    }
                } else {
                    user.setPermission("User not found");
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
    public void userLogout(User user) {
        
    	String query = "UPDATE Users SET Status = 'Disconnected' WHERE Username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateUserStatus(String username, String status) {
        
    	String sql = "UPDATE Users SET Status = ? WHERE Username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveOrderDetails(OrderDetails orderDetails) throws SQLException {
        String sql = "INSERT INTO CustomerDetails (customerId, restaurant, orderType, phoneNumber, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderDetails.getCustomerId());
            pstmt.setString(2, orderDetails.getRestaurant());
            pstmt.setString(3, orderDetails.getOrderType());
            pstmt.setString(4, orderDetails.getPhoneNumber());
            pstmt.setString(5, orderDetails.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
