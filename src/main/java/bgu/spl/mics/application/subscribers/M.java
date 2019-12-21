package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.AgentsAvailableEvent;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.TickBroadcast;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private MessageBroker msg = MessageBrokerImpl.getInstance();
	public M() {
		super("M");
		// TODO Implement this
	}

	@Override
	protected void initialize() {

		// TODO Implement this
		msg.register(this);
		msg.subscribeBroadcast(TickBroadcast.class, this);
		msg.subscribeEvent(MissionReceivedEvent.class, this);
		this.run();
	}

}
