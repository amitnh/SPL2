package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Agent {
	private String serialNumber=null;
	private String name=null;
	private boolean isAvailable= true;
	/**
	 * Sets the serial number of an agent.
	 */
	public synchronized void setSerialNumber(String serialNumber) {
		// TODO Implement this
		this.serialNumber=serialNumber;
	}

	/**
     * Retrieves the serial number of an agent.
     * <p>
     * @return The serial number of an agent.
     */
	public synchronized String getSerialNumber() {
		// TODO Implement this
		return serialNumber;
	}

	/**
	 * Sets the name of the agent.
	 */
	public synchronized void setName(String name) {
		// TODO Implement this
		this.name=name;
	}

	/**
     * Retrieves the name of the agent.
     * <p>
     * @return the name of the agent.
     */
	public synchronized String getName() {
		// TODO Implement this
		return name;
	}

	/**
     * Retrieves if the agent is available.
     * <p>
     * @return if the agent is available.
     */
	public synchronized boolean isAvailable() {
		// TODO Implement this
		return isAvailable;
	}

	/**
	 * Acquires an agent.
	 */
	public synchronized void acquire(){
		// TODO Implement this
		isAvailable=false;
	}

	/**
	 * Releases an agent.
	 */
	public synchronized void release(){
		// TODO Implement this
		isAvailable=true;
		this.notifyAll();
	}
}
