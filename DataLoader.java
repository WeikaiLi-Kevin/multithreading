import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The code come from Professor Stanley Pieda to reuse
 *  File: DataLoader.java
 * Author: Stanley Pieda
 * Date: Jan, 2017
 * Description: Orchestrates multithreaded, producer, consumer for reading and inserting records.
 */

public class DataLoader {
	/**
	 * The code to detect when all threads are completed to get the timer to
	 * completion was taken after: App Shah. (July 15, 2017). How to Run Multiple
	 * Threads Concurrently in Java? ExecutorServiceApproach. Retrieved from
	 * http://crunchify.com/how-to-run-multiple-threads-concurrently-in-java-executorservice-approach/
	 */

	public void processRecords() {
		long elapsedTime;
		try {
			// comment or un-comment and re-run to test each buffer type
//			Buffer buffer = new CircularBuffer();
			Buffer buffer = new BlockingQueueBuffer();

			ExecutorService executor = Executors.newCachedThreadPool();
			Producer producer = new Producer(buffer);
			System.out.println("A new thread is created for producer!");
			Consumer consumer = new Consumer(buffer);
			System.out.println("A new thread is created for consumer!");
			new FishStickCleaner().deleteAllFishSticks();

			long startTime = System.currentTimeMillis();

			executor.execute(producer);
			executor.execute(consumer);
			executor.shutdown();

			while (!executor.isTerminated()) {
				// infinite loop, i.e. wait until the all tasks are completed.
				// see App Shah. (July 15, 2017)
				// This works for now, but empty infinite loops are not a good practice
				// as we are wasting cpu cycles to delay the main thread shutting down
				// ,to get timestamps, before the other threads complete their tasks.
				// ** there might be a better way using the executor itself, or
				// use join() with threads without the executor.
			}

			long endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			int minutes = (int) elapsedTime / 1000 / 60;
			int seconds = (int) elapsedTime / 1000 % 60;

			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a");

			System.out.printf("%n%d records read in total%n", producer.getRecordsRead());
			System.out.printf("%d records inserted in total%n", consumer.getRecordsInserted());
			System.out.printf("%d mileseconds elapsed%n", elapsedTime);
			System.out.printf("%02d minutes, %02d seconds, %03d millisecs%n", minutes, seconds, elapsedTime % 1000);
			System.out.printf("Program by: Weikai Li run on %s%n", dateTime.format(format));
			System.out.printf("Buffer type is %s%n", buffer.getClass().getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
