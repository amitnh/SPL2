package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.AgentsAvailableEvent;
import bgu.spl.mics.application.GadgetAvailableEvent;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Squad;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	private Squad squad;
	private int timeTick;
	private static int totalofMoneypennys=0;
	private int id;
	public Moneypenny() {
		super("Moneypenny");
		// TODO Implement this
		totalofMoneypennys+=1;
		this.id=totalofMoneypennys;
		squad = Squad.getInstance();
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		this.subscribeBroadcast(TickBroadcast.class,(TickBroadcast b)-> {
			timeTick= b.getTime();
		} ); // TODO: callback
		this.subscribeEvent(AgentsAvailableEvent.class,(AgentsAvailableEvent e)->{
			//SYNC?
			Boolean isready = squad.getAgents(e.getSerials());

			List<Object> futureresult = new LinkedList<>();
			futureresult.add(true);
			futureresult.add(squad.getAgents(e.getSerials()));
			futureresult.add(this.id);
			this.complete(e,futureresult);

		} ); // use lambdas
		this.run();
	}

}
