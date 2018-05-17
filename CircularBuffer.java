
/**
 * CircularBuffer is a class which implements buffer
 * It is used to create circular buffer to avoid concurrent 
 * 
 * @author Weikai Li
 * 
 * */
public class CircularBuffer implements Buffer {
	private final FishStick[] buffer = new FishStick[100];// storage for the incoming data
	private int occupiedCells = 0; // count number of buffers used
	private int writeIndex = 0; // index of next element to write to
	private int readIndex = 0; // index of next element to read

	/**
	 * Override the blockingPut method from Buffer interface
	 * notify other threads to use the method until the buffer is full and current thread wait
	 * @param FishStick value
	 * @throws InterruptedException
	 * */
	@Override
	public synchronized void blockingPut(FishStick value) throws InterruptedException {

		while (occupiedCells == buffer.length) {
		
			wait(); //wait for other thread to use the object
		}

		buffer[writeIndex] = value; //add data in buffer

		writeIndex = (writeIndex + 1) % buffer.length;
		++occupiedCells;

		notifyAll();
	}
	
	/**
	 * Override the blockingGet method from Buffer interface
	 * notify other threads to use the method until the buffer is empty and current thread wait
	 * @return FishStick result
	 * @throws InterruptedException
	 * */
	@Override
	public synchronized FishStick blockingGet() throws InterruptedException {

		while (occupiedCells == 0) {

			wait(); // wait until a buffer cell is filled
		}

		FishStick readValue = buffer[readIndex]; // read value from buffer

		// update circular read index
		readIndex = (readIndex + 1) % buffer.length;
		--occupiedCells; // one fewer buffer cells are occupied

		notifyAll(); // notify threads waiting to write to buffer

		return readValue;
	}

}
