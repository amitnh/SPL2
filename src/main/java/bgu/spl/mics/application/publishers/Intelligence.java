package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Publisher;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.LinkedList;

/**
 * A Publisher only.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber{

	private LinkedList<MissionInfo> missions;
	private long timeTick;
	private int id;
	private static int totalMs=0;
	public Intelligence(LinkedList<MissionInfo> missions) {
		super("intelligence" +totalMs);
		// TODO Implement this
		this.missions= missions;
		++totalMs;
		this.id = totalMs;
	}

	@Override
	protected void initialize() {
		// TODO Implement this.
		this.subscribeBroadcast(TickBroadcast.class,(TickBroadcast b)-> {
			timeTick= b.getTime();
			Sendmissions();
		} );

	}

	private void Sendmissions() {
		for(MissionInfo e : missions) {
			if (e.getTimeIssued() <= timeTick) {
				System.out.println(getName()+" Sending missions now: "+e.getMissionName()+ " time issued"+ timeTick);
				this.getSimplePublisher().sendEvent(new MissionReceivedEvent(e));
				missions.remove(e);
			}
		}
	}
}
