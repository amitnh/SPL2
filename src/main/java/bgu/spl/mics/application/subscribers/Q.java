package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.GadgetAvailableEvent;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.TickBroadcast;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private MessageBroker msg = MessageBrokerImpl.getInstance();

	public Q() {
		super("Q");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		this.subscribeBroadcast(TickBroadcast.class,callback);
		this.subscribeEvent(GadgetAvailableEvent.class,callback); // use lambdas
		this.run();
	}

}
