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
		inv = Inventory.getInstance();

	}

	@Override
	protected void initialize() {
		this.subscribeBroadcast(TickBroadcast.class,(TickBroadcast b)->{});// TODO Implement this
		this.subscribeEvent(GadgetAvailableEvent.class,(GadgetAvailableEvent e)-> this.complete(e,inv.getItem(e.getGadget()))); // use lambdas
		this.run();
	}
}
