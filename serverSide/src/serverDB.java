
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class serverDB {

    static Connection conn;
    static Statement stmt;
    static PreparedStatement pstmt;

    public serverDB() {
        connectToDB();
    }

    public static void connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("Driver definition failed");
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root", "aA123456");
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
    public boolean checkIfUserExsit(String userName) {
        String result;
        String query = "SELECT UserName FROM users";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("UserName");
                if (id.equals(userName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

}

