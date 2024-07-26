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
        String query = "SELECT UserPassword, ConnectionStatus FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("UserPassword");
                    String connectionStatus = rs.getString("ConnectionStatus");

                    if (!password.equals(user.getPassword())) {
                        user.setPermission("Password invalid");
                        return user;
                    }

                    if (connectionStatus.equals("Connected")) {
                        user.setPermission("Connected");
                        return user;
                    }
                } else {
                    user.setPermission("User not found");
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            user.setPermission("Error during login");
            return user;
        }
        return user;
    }

    /**
     * Logs out a user, updating their connection status in the database.
     *
     * @param user - The User object to log out.
     */
    public void userLogout(User user) {
        String query = "UPDATE Users SET ConnectionStatus = 'Disconnected' WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
