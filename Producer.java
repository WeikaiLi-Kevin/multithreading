
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Producer Class is used to read the values from the dataset and pass it to the shared buffer
 * 
 * @author Weikai Li
 * 
 * 
 * */
public class Producer implements Runnable {
	
	private final Buffer sharedLocation; // reference to shared object
	private Scanner fishstickScanner = null;
	private int recordsRead = 0;

	/**
	 * Constructor
	 * @param sharedLocation
	 */
	public Producer(Buffer sharedLocation) {
		this.sharedLocation = sharedLocation;
	}

	// store values from 1 to 10 in sharedLocation
	public void run() {

		try {
			Thread.currentThread().setName("Producer");
			openFile();
			while (fishstickScanner.hasNext()) {
				String line = fishstickScanner.nextLine(); // read raw data
				String[] fields = line.split(","); // split on delimiter
				FishStick fishstick = new FishStick();
				fishstick.setRecordNumber(Integer.parseInt(fields[0]));
				fishstick.setOmega(fields[1]);
				fishstick.setLambda(fields[2]);
				fishstick.setUUID(fields[3]);
				
				if (fishstickScanner.hasNext()) {//set the marker of the last fish stick
					fishstick.setMarker(false);
				}

				recordsRead++;
				
				sharedLocation.blockingPut(fishstick); //put the value into buffer
				if (recordsRead % 100 == 0) {
					System.out.printf("%d records read %n", recordsRead);
				}
			}
			System.out.printf("%d records read, task completed %n", recordsRead);

		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
		} finally {
			closeFile();
		}
	}

	/**
	 * openFile method is used to start reading the file and load the data into the scanner
	 * 
	 * */
	private void openFile() {
		try {
			fishstickScanner = new Scanner(new FileReader(new File("DataSet18W_100000.csv")));
		} catch (IOException ex) {
			System.out.println("Problem opening file: " + ex.getMessage());
		}
	}

	/**
	 * closeFile method is used to close the scanner
	 * */
	private void closeFile() {
		try {
			if (fishstickScanner != null) {
				fishstickScanner.close();
			}
		} catch (Exception ex) {
			System.out.println("Problem closing file: " + ex.getMessage());
		}
	}
	
	/**
	 *  getRecordsRead method is used to return the total number of the records read from producer
	 * 
	 * @return integer counter
	 */
	public int getRecordsRead() {
		return recordsRead;
	}
} // end class Producer
