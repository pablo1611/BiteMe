import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class mysqlConnection {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeeded");
        } catch (Exception ex) {
            System.out.println("Driver definition failed");
            ex.printStackTrace();
            return; // Exit if driver definition fails
        }

        try (Connection conn = (DriverManager.getConnection("jdbc:mysql://localhost/lab3db?serverTimezone=IST", "root", "eran2005"))) {
            System.out.println("SQL connection succeeded");

            // Example: Update arrival time for the flight KU101
            updateArrivalTimeForFlight(conn, "KU101", "13:45:00");

            // Example: Update arrival time for each flight from Paris scheduled to arrive earlier than 15:00
            updateArrivalTimeForParisFlights(conn, "13:45:00", "15:00:00");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            ex.printStackTrace();
        }
    }

    // Method to update arrival time for a specific flight based on flight_number
    public static void updateArrivalTimeForFlight(Connection conn, String flightNumber, String newRecordTime) {
        String updateSQL = "UPDATE arrival_flight SET record_time = ? WHERE flight_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newRecordTime);
            pstmt.setString(2, flightNumber);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Arrival time updated successfully for flight " + flightNumber);
            } else {
                System.out.println("Flight number " + flightNumber + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update arrival time for each flight from Paris scheduled to arrive earlier than a specified time
    public static void updateArrivalTimeForParisFlights(Connection conn, String newRecordTime, String cutoffTime) {
        String updateSQL = "UPDATE arrival_flight SET record_time = ? WHERE origin = 'Paris' AND expected_time < ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newRecordTime);
            pstmt.setString(2, cutoffTime);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("9 flights updated for Paris arrivals before " + cutoffTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public static void printCourses(Connection con)
	{
		/*Statement stmt;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM courses;");
	 		while(rs.next())
	 		{
				 // Print out the values
				 System.out.println(rs.getString(1)+"  " +rs.getString(2));
			} 
			rs.close();
			//stmt.executeUpdate("UPDATE course SET semestr=\"W08\" WHERE num=61309");
		} catch (SQLException e) {e.printStackTrace();}*/
	}

	
	public static void createTableCourses(Connection con1){
		Statement stmt;
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate("create table courses(num int, name VARCHAR(40), semestr VARCHAR(10));");
			stmt.executeUpdate("load data local infile \"courses.txt\" into table courses");
	 		
		} catch (SQLException e) {	e.printStackTrace();}
		 		
	}
	
	
	
}


