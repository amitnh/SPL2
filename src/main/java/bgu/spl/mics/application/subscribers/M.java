package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.AgentsAvailableEvent;
import bgu.spl.mics.application.GadgetAvailableEvent;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Report;
import bgu.spl.mics.application.passiveObjects.Squad;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private int timeTick; // TODO: needs to be update when Broadcast
	private Diary diary;
	private int id;
	private static int totalMs=0;
	public M() {
		super("M");
		totalMs+=1;
		this.id = totalMs;

	}

	@Override
	protected void initialize() {
		diary = Diary.getInstance();
		this.subscribeBroadcast(TickBroadcast.class,(TickBroadcast b)-> {
			timeTick= b.getTime();
		} ); // TODO: callback
		this.subscribeEvent(MissionReceivedEvent.class,(MissionReceivedEvent e)->
		{
			Report report = new Report();

			report.setTimeCreated(timeTick);
			boolean isCompleted = false;
			if (getSimplePublisher().sendEvent(new AgentsAvailableEvent(e.getInfo().getSerialAgentsNumbers())).get()) {
				if (getSimplePublisher().sendEvent(new GadgetAvailableEvent(e.getInfo().getGadget())).get()) {
					report.setQTime(timeTick);
					if (e.getInfo().getTimeExpired()>=timeTick)
					{
						isCompleted=true;
					}
				}
			}
				complete(e,isCompleted);
			report.setAgentsNames(); ///TODO
			report.setMoneypenny();
			report.setAgentsSerialNumbersNumber(e.getInfo().getSerialAgentsNumbers());
			report.setGadgetName(e.getInfo().getGadget());
			report.setM(id);
			report.setMissionName(e.getInfo().getMissionName());

			report.setTimeIssued(e.getInfo().getTimeIssued());
			diary.addReport(report);
		}); // use lambda
		this.run();
	}

}
