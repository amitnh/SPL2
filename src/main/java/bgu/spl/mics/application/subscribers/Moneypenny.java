package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.*;
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
	private long timeTick;
	private static int totalofMoneypennys=0;
	private int id;
	public Moneypenny() {
		super("Moneypenny" + totalofMoneypennys);
		// TODO Implement this
		this.id=totalofMoneypennys;

		++totalofMoneypennys;
		squad = Squad.getInstance();
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		this.subscribeBroadcast(TickBroadcast.class,(TickBroadcast b)-> {
			timeTick= b.getTime();

		} ); // TODO: callback
		this.subscribeEvent(AgentsAvailableEvent.class,(AgentsAvailableEvent e)->{
			Pair<List<String>, Integer> futureresult;
			System.out.println(this.getName() + " getting agents to event");

			if(squad.getAgents(e.getSerials())) {
				futureresult = new Pair<>(squad.getAgentsNames(e.getSerials()), this.id);
				this.complete(e, futureresult);
			}
			else {
				futureresult = new Pair<>(null,this.id);
				this.complete(e, futureresult);
			}

		} ); // use lambdas
		this.subscribeEvent(SendAgentsEvent.class,(SendAgentsEvent e)->{
			System.out.println(this.getName() + "sending agents to event");
			squad.sendAgents(e.getSerials(),e.getTime());
			this.complete(e,true);
		} ); // use lambdas
		this.subscribeEvent(ReleaseAgentsEvent.class,(ReleaseAgentsEvent e)->{
			System.out.println(this.getName() + "Releasing agents from event");
			squad.releaseAgents(e.getSeriealstoRelease());
			this.complete(e,true);
		} ); // use lambdas


	}

}
