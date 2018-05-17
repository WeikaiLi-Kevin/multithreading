
/**
 * Interface Buffer is an interface to provide the basic method: blockingPut and blockingGet 
 * for the buffer delivered between consumer and producer
 * 
 * @author Weikai Li
 * 
 * */

public interface Buffer {
	/**
	 * blockingPut method used to get buffer from producer
	 * @param fishstick value
	 * @throws InterruptedException
	 */
	public void blockingPut(FishStick value) throws InterruptedException;

	/**
	 * blockingGet method used to send buffer to consumer
	 * @return Cabbage result
	 * */
	public FishStick blockingGet() throws InterruptedException;

}
//end of interface