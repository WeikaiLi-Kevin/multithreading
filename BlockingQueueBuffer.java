import java.util.concurrent.ArrayBlockingQueue;

/**
 * BlockingQueueBuffer is a class which implements buffer and using ArrayBlockingQueue
 * 
 * @author Weikai Li
 * 
 * */
public class BlockingQueueBuffer implements Buffer {

	private final ArrayBlockingQueue<FishStick> buffer; //buffer is the shared buffer between producer and consumer

	/**
	 * default constructor
	 */
	public BlockingQueueBuffer() {
		
		buffer = new ArrayBlockingQueue<FishStick>(100);
	}

	/**
	 * Override the blokingPut method of interface Buffer
	 */
	@Override
	public void blockingPut(FishStick value) throws InterruptedException {
		
		buffer.put(value);
	}

	/**
	 * Override the blokingGet method of interface Buffer
	 */
	@Override
	public FishStick blockingGet() throws InterruptedException {
		
		FishStick readValue = buffer.take(); // remove value from buffer
		
		return readValue;
	}

}
