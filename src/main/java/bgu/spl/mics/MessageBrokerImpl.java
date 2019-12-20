package bgu.spl.mics;
import javafx.util.Pair;
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
	private ConcurrentHashMap<Class,LinkedList<Subscriber>> subscribeEventMap = new ConcurrentHashMap<Class,LinkedList<Subscriber>>();
	private ConcurrentHashMap<Class,Subscriber> subscribeBroadcastMap;
	private ConcurrentHashMap<Subscriber,LinkedBlockingQueue<Message>> subscribersQueueMap;


	//private List<Pair<Class,List<Subscriber>>> subscribeEventList;
	//private List<LinkedBlockingQueue<Integer> subscribersQueue = new List<LinkedBlockingQueue<Integer>>;
	public static MessageBroker getInstance() {
		return instance;
	}
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {

		List<Subscriber> lst= subscribeEventMap.putIfAbsent(type, new LinkedList<>());// works atomically
		lst.add(m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(Subscriber m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(Subscriber m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
