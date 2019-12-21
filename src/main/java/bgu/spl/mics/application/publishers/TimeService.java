package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Publisher;
import bgu.spl.mics.application.TickBroadcast;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {

	private long now;
	private TickBroadcast tickBroadcast = new TickBroadcast();;

	public TimeService() {
		super("TimeService");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		now = System.currentTimeMillis();

	}

	@Override
	public void run() {
		// TODO Implement this
		while(true) { //CHANGE THIS

			////DO EVERY 100ms:
			if (System.currentTimeMillis() - now > 100) {
				tickBroadcast.Tick();
				now = System.currentTimeMillis();
				this.getSimplePublisher().sendBroadcast(tickBroadcast);
			}
		}
	}

}
