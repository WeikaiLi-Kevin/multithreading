import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * FishStickCleaner is a class to clean the all records in database
 * 
 * @author Weikai Li
 * 
 */
public class FishStickCleaner {

	private Connection con = null;

	private final String connectionString = "jdbc:mysql://localhost/assignment1?autoReconnect=true&useSSL=false"; //database address
	private final String username = "assignment1"; //database username
	private final String password = "password"; // database password

	/** 
	 * deleteAllFishSticks method is used to reset the database by using TRUNCATE
	 * */
	public void deleteAllFishSticks() {
		PreparedStatement pstmt = null;
		try {

			openConnection();
			if (con == null || con.isClosed()) {
				System.out.println("Cannot delete records, no connection or connection closed");
			}

			pstmt = con.prepareStatement("TRUNCATE TABLE fishsticks");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	/**
	 * openConnection method is used to setup the connection with database
	 * */
	private void openConnection() {
		try {
			if (con != null) {
				System.out.println("Cannot create new connection, one exists already");
			} else {
				con = DriverManager.getConnection(connectionString, username, password);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * closeConnection method is used to breakdown the connection with database
	 * */
	private void closeConnection() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
