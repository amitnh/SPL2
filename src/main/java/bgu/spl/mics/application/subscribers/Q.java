package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.GadgetAvailableEvent;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private Inventory inv;
	public Q() {
		super("Q");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		inv = Inventory.getInstance();
		this.subscribeBroadcast(TickBroadcast.class,callback);
		this.subscribeEvent(GadgetAvailableEvent.class,(String gadget)-> this.complete(,inv.getItem(gadget)); ); // use lambdas
		this.run();
	}

}
