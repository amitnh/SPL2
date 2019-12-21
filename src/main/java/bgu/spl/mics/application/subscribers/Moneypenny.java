package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.AgentsAvailableEvent;
import bgu.spl.mics.application.GadgetAvailableEvent;
import bgu.spl.mics.application.TickBroadcast;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	private MessageBroker msg = MessageBrokerImpl.getInstance();

	public Moneypenny() {
		super("Moneypenny");
		// TODO Implement this

	}

	@Override
	protected void initialize() {
		// TODO Implement this
		msg.register(this);
		msg.subscribeBroadcast(TickBroadcast.class, this);
		msg.subscribeEvent(AgentsAvailableEvent.class, this);
		this.run();
	}

}
