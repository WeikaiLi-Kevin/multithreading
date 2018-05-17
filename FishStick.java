/**
 * File: FishStick.java come from professor Stanley Pieda to reuse
 * Author: Stanley Pieda
 * Date: Jan, 2018
 * Description: Simple data transfer object.
 * 
 * I add two methods:setMarker and getMarker to check whether the fishstick is the last one or not
 */
public class FishStick {
	private int id;
	private int recordNumber;
	private String omega;
	private String lambda;
	private String uuid;
	private boolean isLastRecord = true;

	public FishStick() {
		this(0, 0, "", "", "");
	}

	public FishStick(int id, int recordNumber, String omega, String lambda, String uuid) {
		this.id = id;
		this.recordNumber = recordNumber;
		this.omega = omega;
		this.lambda = lambda;
		this.uuid = uuid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getOmega() {
		return omega;
	}

	public void setOmega(String omega) {
		this.omega = omega;
	}

	public String getLambda() {
		return lambda;
	}

	public void setLambda(String lambda) {
		this.lambda = lambda;
	}

	public String getUUID() {
		return uuid;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	public void setMarker(boolean isLastRecord) {
		this.isLastRecord = isLastRecord;
	}

	public boolean getMarker() {
		return isLastRecord;
	}
}
