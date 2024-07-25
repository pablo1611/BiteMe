package BMServer;

import java.math.BigDecimal;
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
    
    /**
     * 
     * @param : host is the host (ip) of the sever's database
     * @param schema : the name of the schema in the database
     * @param userName : the user name of the database
     * @param password : user's password
     */
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
    

    /**
     * Checks if a user exists in the database.
     *
     * @param userName - The username(String) to check.
     * @return true if the user exists, false otherwise.
     */
    public boolean checkIfUserExists(String userName) {
        String query = "SELECT 1 FROM users WHERE userName = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true; // User exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User does not exist
    }


    public static void closeDBconnection() {
        try {
            if (pstmt != null)
                pstmt.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
    public void userLogout(User user) {
    	
    }
    */

}

