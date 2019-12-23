package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.AgentsAvailableEvent;
import bgu.spl.mics.application.GadgetAvailableEvent;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.TickBroadcast;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private long timeTick; // needs to be update when Broadcast
	public M() {
		super("M");
		// TODO Implement this
	}

	@Override
	protected void initialize() {

		// TODO Implement this
		this.subscribeBroadcast(TickBroadcast.class,callback);
		this.subscribeEvent(MissionReceivedEvent.class,(MissionReceivedEvent e)->
		{
			boolean isCompleted = false;
			if (getSimplePublisher().sendEvent(new AgentsAvailableEvent(e.getInfo().getSerialAgentsNumbers())).get()) {
				if (getSimplePublisher().sendEvent(new GadgetAvailableEvent(e.getInfo().getGadget())).get()) {
					if (e.getInfo().getTimeExpired()>=timeTick)
					{
						isCompleted=true;
					}
				}
			}
				complete(e,isCompleted);
		}); // use lambda
		this.run();
	}

}
