/**
 *  File: Launcher.java from professor Stanley Pieda to reuse 
 * Author: Stanley Pieda
 * Date: Jan, 2018
 * Description: Startup for the application.
 */
public class Launcher {

	public static void main(String[] args) {
		try {
			DataLoader loader = new DataLoader();
			loader.processRecords();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
}
