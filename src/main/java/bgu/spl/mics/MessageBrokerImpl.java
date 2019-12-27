package bgu.spl.mics;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {

	/**
	 * Retrieves the single instance of this class.
	 */
	private static MessageBrokerImpl instance =new MessageBrokerImpl(); //makes the class singelton
	private ConcurrentHashMap<Class<? extends Event>,LinkedList<Subscriber>> subscribeEventMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Class<? extends Broadcast>,LinkedList<Subscriber>> subscribeBroadcastMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Subscriber,LinkedBlockingQueue<Message>> subscribersQueueMap = new ConcurrentHashMap<Subscriber,LinkedBlockingQueue<Message>>();
	private ConcurrentHashMap<Event,Future> futureHashMap = new ConcurrentHashMap<Event,Future>();


	//private List<Pair<Class,List<Subscriber>>> subscribeEventList;
	//private List<LinkedBlockingQueue<Integer> subscribersQueue = new List<LinkedBlockingQueue<Integer>>;
	public static MessageBroker getInstance() {
		return instance;
	}
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
		subscribeEventMap.putIfAbsent(type,new LinkedList<>());
		synchronized (type)
		{
			subscribeEventMap.get(type).add(m);
		}
	}
	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		subscribeBroadcastMap.putIfAbsent(type,new LinkedList<>());
		synchronized (type) {
				subscribeBroadcastMap.get(type).add(m);

			}
	}

	@Override
	public <T> void complete(Event<T> e, T result) { // needs synchronized ?!!?!?!?!??!?!?!!?!?!?!?
		futureHashMap.get(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) { // need to think again on the sync $$$$$$$$$$$$$$$$$$$$$$$$
try {
	for (Subscriber m : subscribeBroadcastMap.get(b.getClass())) {
		subscribersQueueMap.putIfAbsent(m, new LinkedBlockingQueue<>());
		synchronized (m) {
			try {

				subscribersQueueMap.get(m).put(b);
				m.notifyAll();
			} catch (Exception exp) {
			}
		}
	}
}
catch (NullPointerException nullExp){
	//System.out.println("STOP, ata Nashnash");
}


	}
	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Subscriber m;
		try { 				//check if not empty, or event is null
			synchronized (e.getClass()) { // to check
				m = subscribeEventMap.get(e.getClass()).getFirst(); // brings the first subscriber in the event list
				subscribeEventMap.get(e.getClass()).add(subscribeEventMap.get(e.getClass()).remove(0)); // moves the subscriber to the end of the list ?
			}
		}
			catch(Exception exp){return null;}

			Future<T> future = new Future<>();
			futureHashMap.putIfAbsent(e,future);
			synchronized (m) {
				subscribersQueueMap.get(m).add(e); //adds the Event to the subscriber Queue
				m.notifyAll(); // if m was in wait() from awaitMessage (empty Queue)
			}
		return future;
	}

	@Override
	public void register(Subscriber m) {
		// ConcurrentHashMap<Subscriber,LinkedBlockingQueue<Message>> subscribersQueueMap;
			LinkedBlockingQueue<Message> que = new LinkedBlockingQueue<>();
			subscribersQueueMap.putIfAbsent(m,que); // works atomically
	}

	@Override
	public void unregister(Subscriber m) {
		for (Map.Entry<Class<? extends Event>,LinkedList<Subscriber>>  i:subscribeEventMap.entrySet()) {
			synchronized (i.getKey()) {
				try {i.getValue().remove(m);} catch (Exception exp){};
			}
		}
		for (Map.Entry<Class<? extends Broadcast>,LinkedList<Subscriber>>  i:subscribeBroadcastMap.entrySet()) {
			synchronized (i.getKey()) {
				try {i.getValue().remove(m);} catch (Exception exp){};
			}
		}
		synchronized (m)
		{
			subscribersQueueMap.remove(m);
		}

	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		synchronized (m) {
			while (subscribersQueueMap.get(m).isEmpty()) {
				try{  m.wait(); }
				catch (InterruptedException ignored){}//m.interuppt;} ??????????????
			}
			return subscribersQueueMap.get(m).remove();
		}
	}

}
