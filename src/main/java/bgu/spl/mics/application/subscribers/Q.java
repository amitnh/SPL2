package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.GadgetAvailableEvent;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.TerminateBroadcast;
import bgu.spl.mics.application.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private Inventory inv;
	private Boolean overtime = false;
	public Q() {
		super("Q");
		// TODO Implement this
		inv = Inventory.getInstance();

	}

	@Override
	protected void initialize() {
		this.subscribeBroadcast(TickBroadcast.class, (TickBroadcast b) -> {
		});// TODO Implement this

		this.subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast b) -> {
			overtime = true;
		});// TODO Implement this

		this.subscribeEvent(GadgetAvailableEvent.class, (GadgetAvailableEvent e) -> {
			Pair<Boolean,Boolean> result = new Pair(inv.getItem(e.getGadget()), overtime);
			this.complete(e, result); // use lambdas

		});
	}
}
