import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Consumer Class is used to read value from the buffer and insert the value into database
 * 
 * @author Weikai Li
 * 
 */
public class Consumer implements Runnable {

	private final Buffer sharedLocation; // reference to shared object
	private Connection con = null;
	private final String connectionString = "jdbc:mysql://localhost/assignment1?autoReconnect=true&useSSL=false"; //database address
	private final String username = "assignment1"; // database username
	private final String password = "password";	//database password
	private int recordsInserted = 0;

	/**
	 * Constructor
	 * @param sharedLocation
	 */
	public Consumer(Buffer sharedLocation) {
		this.sharedLocation = sharedLocation;
	}

	/**
	 * run method is overrided from runnable interface 
	 * insert data to database
	 */
	@Override
	public void run() {
		try {
			Thread.currentThread().setName("Consumer");
			openConnection();
			FishStick data = new FishStick();
			data.setMarker(false);
			while (!data.getMarker()) { //use getMarker method to check whether get the last record or not
										// if finish reading the records from original file, then finish and break the loop
				data = sharedLocation.blockingGet();
				//System.out.print(data.getMarker());
				insertFishStick(data);  //call insertFishStick method to insert data to database
				recordsInserted++;
				if (recordsInserted % 100 == 0) {
					System.out.printf("%d records inserted by consumer%n", recordsInserted);
				}
			}
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
		} finally {
			closeConnection();
		}
		System.out.printf("%d records inserted, task completed%n", recordsInserted);

	}
	
	/**
	 * openConnection method is used to setup the connection with database
	 */
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
	 * closeConnection is used to breakdown the connection with database
	 */
	private void closeConnection() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * insertFishStick method is used to insert fishstick value into database
	 * 
	 * @param FishStick fishstick
	 */
	public void insertFishStick(FishStick fishstick) {
		PreparedStatement pstmt = null;

		try {
			if (con == null || con.isClosed()) {

				System.out.println("Cannot insert records, no connection or connection closed");
			}

			pstmt = con.prepareStatement(
					"INSERT INTO FishSticks (recordnumber, omega, lambda, uuid) " + "VALUES(?, ?, ?, ?)");
			pstmt.setInt(1, fishstick.getRecordNumber());
			pstmt.setString(2, fishstick.getOmega());
			pstmt.setString(3, fishstick.getLambda());
			pstmt.setString(4, fishstick.getUUID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	/**
	 * getRecordsInserted method is used to return the total number of the record inserted into database
	 * 
	 * @return integer recordsInserted
	 */
	public int getRecordsInserted() {
		return recordsInserted;
	}
} // end class Consumer
