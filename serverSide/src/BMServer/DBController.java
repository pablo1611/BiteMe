package BMServer;

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
        String query = "INSERT INTO Users (Username, Password, Name, Phone, Address, Role, Status, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getStatus());
            ps.setString(8, user.getEmail());
            int updatedRows = ps.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

